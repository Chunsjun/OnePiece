package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootControll implements Initializable {
	@FXML
	private Button btn;
	@FXML
	private Label state;
	@FXML
	private Label person;
	@FXML
	private TextArea ta;
	@FXML
	private TextArea cta;
	@FXML
	private ListView<String> listperson;
	@FXML
	private TextField textdata;
	@FXML
	private Button sendbtn;
	@FXML
	private Canvas can;
	@FXML
	private TextField question;
	@FXML
	private Button gamestart;

	@FXML
	private Button all;
	@FXML
	private Button black;
	@FXML
	private Button red;
	@FXML
	private Button blue;
	@FXML
	private Button green;
	@FXML
	private Button yellow;
	@FXML
	private Button white;
	@FXML
	private Button timer;

	ExecutorService es;
	ServerSocket ss;
	List<Client> connections = new Vector<Client>();
	ObservableList<String> nick = FXCollections.observableArrayList();

	double x;
	double y;
	double xx;
	double yy;
	GraphicsContext graphicsContext;
	
	public void timer() throws IOException{
		Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/timer.fxml"));
		
		Scene scene2 = new Scene(root2);
		
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("타이머");
		stage.setScene(scene2);
		stage.show();
	}
	
	public class Client {
		Socket s;

		Client(Socket s) {
			this.s = s;
			receive();

			// 전송버튼
			sendbtn.setOnAction(envet -> {

				for (Client client : connections) {
					client.send("관리자: " + textdata.getText());
				}
				ctext("관리자: " + textdata.getText());
				textdata.setText(null);

			});
			// 엔터로 전송
			textdata.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						for (Client client : connections) {
							client.send("관리자: " + textdata.getText());
						}
						ctext("관리자: " + textdata.getText());
						textdata.setText(null);
					}
				}

			});
			all.setOnAction(Event -> {
				for (Client client : connections) {
					client.send5();
				}

				graphicsContext.clearRect(1.5, 1.5, graphicsContext.getCanvas().getWidth() - 3,
						graphicsContext.getCanvas().getHeight() - 3);
			});

			black.setOnAction(Event -> {
				for (Client client : connections) {
					client.sendblack();
				}

				graphicsContext.setStroke(Color.BLACK);
				graphicsContext.setLineWidth(1);
			});

			red.setOnAction(Event -> {
				for (Client client : connections) {
					client.sendred();
				}

				graphicsContext.setStroke(Color.RED);
				graphicsContext.setLineWidth(1);
			});

			blue.setOnAction(Event -> {
				for (Client client : connections) {
					client.sendblue();
				}

				graphicsContext.setStroke(Color.BLUE);
				graphicsContext.setLineWidth(1);
			});

			green.setOnAction(Event -> {
				for (Client client : connections) {
					client.sendgreen();
				}

				graphicsContext.setStroke(Color.GREEN);
				graphicsContext.setLineWidth(1);
			});

			yellow.setOnAction(Event -> {
				for (Client client : connections) {
					client.sendyellow();
				}

				graphicsContext.setStroke(Color.YELLOW);
				graphicsContext.setLineWidth(1);
			});

			white.setOnAction(Event -> {
				for (Client client : connections) {
					client.sendwhite();
				}

				graphicsContext.setStroke(Color.WHITE);
				graphicsContext.setLineWidth(15);
			});

			////

		}

		public void receive() {
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					try {
						while (true) {
							byte[] byteArr = new byte[100];
							InputStream is = s.getInputStream();

							int readByteCount = is.read(byteArr);

							if (readByteCount == -1) {
								throw new IOException();
							}

							String data = new String(byteArr, 0, readByteCount, "UTF-8");
							// 받은 메세지 뿌려주기
							if (data.startsWith("!") && data.endsWith("!")) {

								Platform.runLater(() -> {
									displaytext("요청 처리: " + s.getRemoteSocketAddress() + ": "
											+ Thread.currentThread().getName());
									ctext(data.substring(1, data.length() - 1));
								});

								for (Client client : connections) {
									client.send(data.substring(1, data.length() - 1));
									Thread.sleep(10);
								}

							}
							// 받은 닉네임 추가 목록
							if (data.startsWith("*") && data.endsWith("*")) {

								Platform.runLater(() -> {
									nick.add(data.substring(1, data.length() - 1));
									listperson.setItems(nick);
								});
								// System.out.println(data.substring(1,data.length()-1));
								// 받은 닉네임 확인
							}

							// 받은 닉네임 삭제할 목록
							if (data.startsWith("#") && data.endsWith("#")) {
								Thread.sleep(40);
								Platform.runLater(() -> {
									nick.remove(data.substring(1, data.length() - 1));

								});
							}

							// 클라이언트가 정답 맞췄다는데??
							if (data.startsWith("S")) {
								Platform.runLater(() -> {
									cta.setText("애들이 정답 맞췄단다!!\n");
								});
							}

						}
					} catch (Exception e) {
						try {
							connections.remove(Client.this);
							// 종료한 클라이언트 발생시 참여인원
							for (Client c : connections) {
								try {
									c.send2();
									Thread.sleep(10);
									c.send1();
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}

							// 서버측에서 클라이언트 종료시 참여인원
							n = n - 1;
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
								person.setText(n + "명");
							});
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		public void send(String data) {
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					try {
						String str = "#" + data + "#";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		// 클라이언트 리스트뷰에 띄울 닉네임
		public void send1() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < nick.size(); i++) {
						try {
							String str = "&" + nick.get(i) + "&";
							byte[] byteArr = str.getBytes("UTF-8");
							OutputStream os = s.getOutputStream();
							os.write(byteArr);
							os.flush();
							Thread.sleep(50);
						} catch (Exception e) {
							try {
								Platform.runLater(() -> {
									displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
											+ Thread.currentThread().getName());
								});
								connections.remove(Client.this);
								s.close();
							} catch (IOException e2) {
							}
						}
					}
				}
			};
			es.submit(runnable);
		}

		// 클라이언트에 몇명 접속했는지 뿌려주기위한 샌드
		public void send2() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "$" + connections.size() + "$";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		// 그림 보내기1
		public void send3() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "(" + x + "," + y + "(";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?3: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		// 그럼보내기 2
		public void send4() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = ")" + xx + "," + yy + ")";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?11: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		/// 전체 지우기 명령어 보내기
		public void send5() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "A";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		/// 펜 검은색으로
		public void sendblack() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "B";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		/// 펜 빨간색으로
		public void sendred() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "R";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		/// 펜 파란색으로
		public void sendblue() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "b";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		/// 펜 초록색으로
		public void sendgreen() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "G";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		/// 펜 노란색으로
		public void sendyellow() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "Y";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		/// 펜 하얀색으로
		public void sendwhite() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "W";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

		/// 문제 답 보내기
		public void sendquestion() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						String str = "/" + aw + "/";
						byte[] byteArr = str.getBytes("UTF-8");
						OutputStream os = s.getOutputStream();
						os.write(byteArr);
						os.flush();

					} catch (Exception e) {
						try {
							Platform.runLater(() -> {
								displaytext("클라이언트 통신 안됨?: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
							});
							connections.remove(Client.this);
							s.close();
						} catch (IOException e2) {
						}
					}
				}
			};
			es.submit(runnable);
		}

	}

	int n;

	public void displaytext(String text) {
		ta.appendText(text + "\n");
	}

	public void ctext(String text) {
		cta.appendText(text + "\n");
	}

	public void startServer() {
		es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		try {
			ss = new ServerSocket();
			ss.bind(new InetSocketAddress("localhost", 5002));
		} catch (Exception e) {
			if (!ss.isClosed()) {
				stopServer();
			}
			return;
		}

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Platform.runLater(() -> {
					btn.setText("서버닫기");
					state.setText("서버가 열렸습니다.");
				});
				String num;

				while (true) {
					try {
						Socket s = ss.accept();

						Platform.runLater(() -> {
							displaytext(
									"연결 수락:" + s.getRemoteSocketAddress() + ": " + Thread.currentThread().getName());
						});

						Client client = new Client(s);
						connections.add(client);

						// 클라이언트에 몇명 접속한지 뿌려주기 누구접속한지 뿌려주기
						for (Client client1 : connections) {
							client1.send2(); // 접속인원
							Thread.sleep(50);
							client1.send1(); // 누구접속했는지

						}

						// 서버측에 참여인원 몇명인지
						Platform.runLater(() -> {
							displaytext("연결 개수: " + connections.size());
							person.setText(connections.size() + "명");
						});

						n = connections.size();

						/// 클라이언트가 접속 하면 그리게 하는 부분

						graphicsContext = can.getGraphicsContext2D();
						initDraw(graphicsContext);

						can.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								graphicsContext.beginPath();
								graphicsContext.moveTo(event.getX(), event.getY());
								graphicsContext.stroke();
								x = event.getX();
								y = event.getY();

								for (Client client1 : connections) {
									client1.send3();
								}
							}
						});

						can.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								graphicsContext.lineTo(event.getX(), event.getY());
								graphicsContext.stroke();

								xx = event.getX();
								yy = event.getY();

								for (Client client1 : connections) {
									client1.send4();
								}
							}
						});

						can.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {

							}
						});

						/// 문제 넘기기

						String[][] hhh = new String[4][5];
						hhh[0][0] = "에어컨";
						hhh[0][1] = "엽기붉닭";
						hhh[0][2] = "화장실";
						hhh[0][3] = "수영장";
						hhh[0][4] = "최순혁";
						hhh[1][0] = "중학교";
						hhh[1][1] = "패션피플";
						hhh[1][2] = "샤프심";
						hhh[1][3] = "담배";
						hhh[1][4] = "스타벅스";
						hhh[2][0] = "나무책상";
						hhh[2][1] = "개그콘서큰";
						hhh[2][2] = "일요일";
						hhh[2][3] = "선문대학교";
						hhh[2][4] = "정보통신공학과";
						hhh[3][0] = "취업성공패키지";
						hhh[3][1] = "영상처리";
						hhh[3][2] = "나루토";
						hhh[3][3] = "밀짚모자";
						hhh[3][4] = "섯다";

						Random r = new Random();

						gamestart.setOnAction(envent -> {
							aw = hhh[r.nextInt(3)][r.nextInt(4)];
							question.setText(aw);
							System.out.println(aw);

							for (Client client1 : connections) {
								client1.sendquestion();
							}

						});

						///

					} catch (Exception e) {
						if (!ss.isClosed()) {
							stopServer();
						}
						break;
					}
				}
			}
		};
		es.submit(runnable);
	}

	public void stopServer() {
		try {
			Iterator<Client> iterator = connections.iterator();
			while (iterator.hasNext()) {
				Client client = iterator.next();
				client.s.close();
				iterator.remove();
			}
			if (ss != null && !ss.isClosed()) {
				ss.close();
			}
			if (es != null && !es.isShutdown()) {
				es.isShutdown();
			}
			Platform.runLater(() -> {
				btn.setText("서버열기");
				state.setText("서버가 닫혔습니다.");
			});
		} catch (Exception e) {
		}
	}

	public void stratstop(ActionEvent e) {
		if (btn.getText().equals("서버열기")) {
			startServer();
		} else if (btn.getText().equals("서버닫기")) {

			Platform.runLater(() -> {

				nick.removeAll(nick);

			});
			stopServer();
		}
	}

	/// 그림 틀 그려주는거
	private void initDraw(GraphicsContext gc) {

		double canvasWidth = gc.getCanvas().getWidth();
		double canvasHeight = gc.getCanvas().getHeight();

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);

		gc.strokeRect(0, // x of the upper left corner
				0, // y of the upper left corner
				canvasWidth, // width of the rectangle
				canvasHeight); // height of the rectangle
		/*
		 * te.setOnAction(Event -> { gc.setStroke(Color.BLUE);
		 * gc.setLineWidth(5); });
		 * 
		 * 
		 */
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

	String aw;
}
