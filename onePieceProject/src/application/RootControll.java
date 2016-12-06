package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootControll implements Initializable {

	@FXML
	private Button btn;
	@FXML
	private Button send;
	@FXML
	private TextField textdata;
	@FXML
	private TextArea ta;
	@FXML
	private TextField nickname;
	@FXML
	private Label state;
	@FXML
	private Label person;
	@FXML
	private ListView<String> listperson;
	@FXML
	private Canvas can;
	@FXML
	private TextField bingo;
	@FXML
	private Button bingobtn;
	@FXML
	private Label an;
	

	ObservableList<String> nick = FXCollections.observableArrayList();

	Socket s;
	
	public void startstop(ActionEvent e) throws Exception {
		
		nickname.setText(Main.nick);
		nickname.setDisable(true);
		
		if (btn.getText().equals("시작")) {
			if (!nickname.getText().equals("")) {
				nickname.setDisable(true);
			}			
			startClient();
		} else if (btn.getText().equals("중지")) {
			nickname.setDisable(true);
			System.out.println("중지버튼 눌렀다");
			send2();
			stopClient();
		}
	}

	public void sendbtn(ActionEvent e) {
		send(textdata.getText());
	}

	public void displaytext(String text) {
		ta.appendText(text + "\n");
	}

	public void startClient() throws Exception {

		if (nickname.getText().equals("")) {

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("경고");

			Parent nick = FXMLLoader.load(getClass().getResource("/fxml/nickname.fxml"));

			Button ok = (Button) nick.lookup("#ok");

			ok.setOnAction(Event -> {
				dialog.close();
			});

			Scene scene1 = new Scene(nick);

			dialog.setScene(scene1);
			dialog.setResizable(false);
			dialog.show();
		}

		else if (!nickname.getText().equals("")) {
			Thread thread = new Thread() {
				public void run() {
					try {
						s = new Socket();
						s.connect(new InetSocketAddress("localhost", 5002));
						send1(); // 서버에 닉네임 보네기

						Platform.runLater(() -> {
							state.setText("서버 연결 완료");
							btn.setText("중지");
							send.setDisable(false);
							// nickname.getText();

						});
					} catch (Exception e) {
						Platform.runLater(() -> {
							// displaytext("서버 통신 안됨");
							state.setText("서버 연결 안됨");
						});
						if (!s.isConnected()) {
							stopClient();
						}
						return;
					}
					receive();
				}

			};
			thread.start();
		}
	}

	public void stopClient() {

		try {
			Platform.runLater(() -> {
				btn.setText("시작");
				send.setDisable(true);

			});

			if (s != null && !s.isClosed()) {
				s.close();
				System.out.println("소켓닫앗다");
				nickname.setDisable(true);
			}
		} catch (IOException e) {
		}
	}

	public void receive() {
		while (true) {
			try {
				byte[] byteArr = new byte[100];
				InputStream is = s.getInputStream();

				int readByteCount = is.read(byteArr);

				if (readByteCount == -1) {
					throw new IOException();
				}

				String data = new String(byteArr, 0, readByteCount, "UTF-8");

				// 채팅 대화 내용
				if (data.startsWith("#") && data.endsWith("#")) {
					Platform.runLater(() -> {
						displaytext(data.substring(1, data.length() - 1));
					});
				}
				// 참여인원 몇명인지
				if (data.startsWith("$") && data.endsWith("$")) {

					Platform.runLater(() -> {
						int i = data.indexOf('$');
						int j = data.substring(i + 1).indexOf('$');

						person.setText(data.substring(i + 1, i + 1 + j) + "명");
					});

					Platform.runLater(() -> {
						nick.removeAll(nick);
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});

				}
				// test 클라이언트에서 보는 참여인원 목록
				if (data.startsWith("&") && data.endsWith("&")) {

					int i = data.indexOf('&');
					int j = data.substring(i + 1).indexOf('&');

					Platform.runLater(() -> {
						nick.add(data.substring(i + 1, i + j + 1));
						listperson.setItems(nick);
					});
				}

				/// 그림 받기 1
				if (data.startsWith("(") && data.endsWith("(")) {
					Platform.runLater(() -> {
						//displaytext(data.substring(1, data.length() - 1));

						int i = data.indexOf('(');
						int j = data.substring(i + 1).indexOf(',');

						int n = data.indexOf(',');
						int m = data.substring(n + 1).indexOf('(');

						x = Float.parseFloat(data.substring(i + 1, j + i + 1));
						y = Float.parseFloat(data.substring(n + 1, n + m + 1));
						// System.out.println(x+","+y);

						graphicsContext.beginPath();
						graphicsContext.moveTo(x, y);
						graphicsContext.stroke();

					});
				}
				// 그림 받기 2
				if (data.startsWith(")") && data.endsWith(")")) {
					Platform.runLater(() -> {
						//displaytext(data.substring(1, data.length() - 1));

						int i = data.indexOf(')');
						int j = data.substring(i + 1).indexOf(',');

						int n = data.indexOf(',');
						int m = data.substring(n + 1).indexOf(')');

						xx = Float.parseFloat(data.substring(i + 1, j + i + 1));
						yy = Float.parseFloat(data.substring(n + 1, n + m + 1));
						// System.out.println(xx+",,"+yy);

						graphicsContext.lineTo(xx, yy);
						graphicsContext.stroke();
					});
				}
				// 모두 지우기 받기
				if (data.startsWith("A")) {
					Platform.runLater(() -> {
						double canvasWidth = graphicsContext.getCanvas().getWidth();
						double canvasHeight = graphicsContext.getCanvas().getHeight();

						graphicsContext.clearRect(1.5, 1.5, canvasWidth - 3, canvasHeight - 3);

					});
				}
				// 검은색 펜으로 바꾸기
				if (data.startsWith("B")) {
					Platform.runLater(() -> {
						graphicsContext.setStroke(Color.BLACK);
						graphicsContext.setLineWidth(1);

					});
				}
				// 빨간 펜으로 바꾸기
				if (data.startsWith("R")) {
					Platform.runLater(() -> {
						graphicsContext.setStroke(Color.RED);
						graphicsContext.setLineWidth(1);

					});
				}
				// 파란 펜으로 바꾸기
				if (data.startsWith("b")) {
					Platform.runLater(() -> {
						graphicsContext.setStroke(Color.BLUE);
						graphicsContext.setLineWidth(1);

					});
				}
				// 초록 펜으로 바꾸기
				if (data.startsWith("G")) {
					Platform.runLater(() -> {
						graphicsContext.setStroke(Color.GREEN);
						graphicsContext.setLineWidth(1);

					});
				}
				// 노란 펜으로 바꾸기
				if (data.startsWith("Y")) {
					Platform.runLater(() -> {
						graphicsContext.setStroke(Color.YELLOW);
						graphicsContext.setLineWidth(1);

					});
				}
				// 하얀 펜으로 바꾸기
				if (data.startsWith("W")) {
					Platform.runLater(() -> {
						graphicsContext.setStroke(Color.WHITE);
						graphicsContext.setLineWidth(15);

					});
				}

				/// 정답 받기
				if (data.startsWith("/") && data.endsWith("/")) {
					
					int i = data.indexOf('/');
					int j = data.substring(i + 1).indexOf('/');
					
					Platform.runLater(() -> {
						aw = data.substring(i + 1, i + j + 1);
						ta.setText("관리자: 애들아 이제 문제 낸다!!"+"\n");
					});
				}

			} catch (Exception e) {
				Platform.runLater(() -> {
					// displaytext("서버 통신 안됨");
					state.setText("서버 연결 안됨");
				});
				stopClient();
				break;
			}
		}
	}
	String aw;
	int k;

	public void send(String data) {
		Thread thread = new Thread() {
			public void run() {
				try {
					String str = "!" + nickname.getText() + ": " + data + "!";
					byte[] byteArr = str.getBytes("UTF-8");
					OutputStream os = s.getOutputStream();
					os.write(byteArr);
					os.flush();

					Platform.runLater(() -> {
						// displaytext("전송 완료");
						state.setText("전송 완료");
						textdata.setText(null);
					});
				} catch (Exception e) {
					Platform.runLater(() -> {
						// displaytext("서버 통신 안됨");
						state.setText("서버 연결 안됨");
					});
					stopClient();
				}
			}
		};
		thread.start();
	}

	// 리스트뷰에 추가할 닉네임
	public void send1() {
		Thread thread = new Thread() {
			public void run() {
				try {
					String str = "*" + nickname.getText() + "*";
					byte[] byteArr = str.getBytes("UTF-8");
					OutputStream os = s.getOutputStream();
					os.write(byteArr);
					os.flush();

				} catch (Exception e) {
					Platform.runLater(() -> {
						// displaytext("서버 통신 안됨");
						state.setText("서버 연결 안됨");
					});
					stopClient();
				}
			}
		};
		thread.start();
	}

	// 리스트뷰에서 삭제할 닉네임
	public void send2() {
		try {
			String str = "#" + nickname.getText() + "#";
			byte[] byteArr = str.getBytes("UTF-8");
			OutputStream os = s.getOutputStream();
			os.write(byteArr);
			os.flush();

		} catch (Exception e) {
			Platform.runLater(() -> {
				// displaytext("서버 통신 안됨");
				state.setText("서버 연결 안됨11");
			});
			stopClient();
		}
	}

	// 나 정답 맞췄어요
	public void sendsolution() {
		try {
			String str = "S";
			byte[] byteArr = str.getBytes("UTF-8");
			OutputStream os = s.getOutputStream();
			os.write(byteArr);
			os.flush();

		} catch (Exception e) {
			Platform.runLater(() -> {
				// displaytext("서버 통신 안됨");
				state.setText("서버 연결 안됨11");
			});
			stopClient();
		}
	}

	private void initDraw(GraphicsContext gc) {

		double canvasWidth = gc.getCanvas().getWidth();
		double canvasHeight = gc.getCanvas().getHeight();

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);

		gc.strokeRect(0, // x of the upper left corner
				0, // y of the upper left corner
				canvasWidth, // width of the rectangle
				canvasHeight); // height of the rectangle

	}

	/////

	double x;
	double y;
	double xx;
	double yy;
	GraphicsContext graphicsContext;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		textdata.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {

					send(textdata.getText());
					textdata.setText(null);
				}
			}
		});

		graphicsContext = can.getGraphicsContext2D();
		initDraw(graphicsContext);
		
		bingobtn.setOnAction(event -> {
			if(aw.equals(bingo.getText())){
				an.setText("정답입니다.");
				//System.out.println(bingo.getText());
				sendsolution();
			}
			else{
				an.setText("틀렸다 멍청아.");
				//System.out.println(bingo.getText());
				//System.out.println(aw+"dd");
			}
			
		});

		

	}

}
