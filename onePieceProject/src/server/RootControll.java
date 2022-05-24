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
		stage.setTitle("Ÿ�̸�");
		stage.setScene(scene2);
		stage.show();
	}
	
	public class Client {
		Socket s;

		Client(Socket s) {
			this.s = s;
			receive();

			// ���۹�ư
			sendbtn.setOnAction(envet -> {

				for (Client client : connections) {
					client.send("������: " + textdata.getText());
				}
				ctext("������: " + textdata.getText());
				textdata.setText(null);

			});
			// ���ͷ� ����
			textdata.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						for (Client client : connections) {
							client.send("������: " + textdata.getText());
						}
						ctext("������: " + textdata.getText());
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
							// ���� �޼��� �ѷ��ֱ�
							if (data.startsWith("!") && data.endsWith("!")) {

								Platform.runLater(() -> {
									displaytext("��û ó��: " + s.getRemoteSocketAddress() + ": "
											+ Thread.currentThread().getName());
									ctext(data.substring(1, data.length() - 1));
								});

								for (Client client : connections) {
									client.send(data.substring(1, data.length() - 1));
									Thread.sleep(10);
								}

							}
							// ���� �г��� �߰� ���
							if (data.startsWith("*") && data.endsWith("*")) {

								Platform.runLater(() -> {
									nick.add(data.substring(1, data.length() - 1));
									listperson.setItems(nick);
								});
								// System.out.println(data.substring(1,data.length()-1));
								// ���� �г��� Ȯ��
							}

							// ���� �г��� ������ ���
							if (data.startsWith("#") && data.endsWith("#")) {
								Thread.sleep(40);
								Platform.runLater(() -> {
									nick.remove(data.substring(1, data.length() - 1));

								});
							}

							// Ŭ���̾�Ʈ�� ���� ����ٴµ�??
							if (data.startsWith("S")) {
								Platform.runLater(() -> {
									cta.setText("�ֵ��� ���� ����ܴ�!!\n");
								});
							}

						}
					} catch (Exception e) {
						try {
							connections.remove(Client.this);
							// ������ Ŭ���̾�Ʈ �߻��� �����ο�
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

							// ���������� Ŭ���̾�Ʈ ����� �����ο�
							n = n - 1;
							Platform.runLater(() -> {
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�: " + s.getRemoteSocketAddress() + ": "
										+ Thread.currentThread().getName());
								person.setText(n + "��");
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		// Ŭ���̾�Ʈ ����Ʈ�信 ��� �г���
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
									displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		// Ŭ���̾�Ʈ�� ��� �����ߴ��� �ѷ��ֱ����� ����
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		// �׸� ������1
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?3: " + s.getRemoteSocketAddress() + ": "
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

		// �׷������� 2
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?11: " + s.getRemoteSocketAddress() + ": "
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

		/// ��ü ����� ��ɾ� ������
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		/// �� ����������
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		/// �� ����������
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		/// �� �Ķ�������
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		/// �� �ʷϻ�����
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		/// �� ���������
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		/// �� �Ͼ������
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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

		/// ���� �� ������
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
								displaytext("Ŭ���̾�Ʈ ��� �ȵ�?: " + s.getRemoteSocketAddress() + ": "
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
					btn.setText("�����ݱ�");
					state.setText("������ ���Ƚ��ϴ�.");
				});
				String num;

				while (true) {
					try {
						Socket s = ss.accept();

						Platform.runLater(() -> {
							displaytext(
									"���� ����:" + s.getRemoteSocketAddress() + ": " + Thread.currentThread().getName());
						});

						Client client = new Client(s);
						connections.add(client);

						// Ŭ���̾�Ʈ�� ��� �������� �ѷ��ֱ� ������������ �ѷ��ֱ�
						for (Client client1 : connections) {
							client1.send2(); // �����ο�
							Thread.sleep(50);
							client1.send1(); // ���������ߴ���

						}

						// �������� �����ο� �������
						Platform.runLater(() -> {
							displaytext("���� ����: " + connections.size());
							person.setText(connections.size() + "��");
						});

						n = connections.size();

						/// Ŭ���̾�Ʈ�� ���� �ϸ� �׸��� �ϴ� �κ�

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

						/// ���� �ѱ��

						String[][] hhh = new String[4][5];
						hhh[0][0] = "������";
						hhh[0][1] = "����Ӵ�";
						hhh[0][2] = "ȭ���";
						hhh[0][3] = "������";
						hhh[0][4] = "�ּ���";
						hhh[1][0] = "���б�";
						hhh[1][1] = "�м�����";
						hhh[1][2] = "������";
						hhh[1][3] = "���";
						hhh[1][4] = "��Ÿ����";
						hhh[2][0] = "����å��";
						hhh[2][1] = "�����ܼ�ū";
						hhh[2][2] = "�Ͽ���";
						hhh[2][3] = "�������б�";
						hhh[2][4] = "������Ű��а�";
						hhh[3][0] = "���������Ű��";
						hhh[3][1] = "����ó��";
						hhh[3][2] = "������";
						hhh[3][3] = "��¤����";
						hhh[3][4] = "����";

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
				btn.setText("��������");
				state.setText("������ �������ϴ�.");
			});
		} catch (Exception e) {
		}
	}

	public void stratstop(ActionEvent e) {
		if (btn.getText().equals("��������")) {
			startServer();
		} else if (btn.getText().equals("�����ݱ�")) {

			Platform.runLater(() -> {

				nick.removeAll(nick);

			});
			stopServer();
		}
	}

	/// �׸� Ʋ �׷��ִ°�
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
