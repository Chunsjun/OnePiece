package Client;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import Login.FindIDFrame;
import Login.FindPassFrame;
import Login.LoginPanel;
import Login.SignUpFrame;

import GameUser.GameUser1;
import GameUser.GameUser2;
import GameUser.GameUser3;
import GameUser.GameUser4;
import GameUser.GameUser5;
import GameUser.GameUser6;
import Client.SoundEffect;

//test
//test2
//test3
//test4
public class CatchmindFrame extends JFrame implements Runnable, ActionListener {
	private int WIDTH = 1000;
	private int HEIGHT = 700;
	private StringTokenizer tokenizer1;
	private String[] tokens1 = new String[200]; // 최대 200개의 방까지

	private StringTokenizer tokenizer2;
	private String[] tokens2 = new String[20];

	private ButtonGroup buttonGroup;

	MyInformation myInformation;
	RoomPanel roompanel; // 방 목록을 보고 입장하거나 방을 만들 수 있는 패널
	ChatPanel chatpanel; // 접속한 client와 채팅을 할 수 있는 패널
	IDlistPanel idlistpanel; // 접속중인 ID를 알려주는 패널
	InformationPanel informationpanel; // 정보 및 상태를 알려 주는 패널
	AvatarPanel avatarpanel; // 아바타 정보를 알려주는 패널

	LoginPanel loginPanel; // 로그인 페이지 보여주는 프레임
	GamePanel gamepanel; // 게임을 진행하는 패널

	SignUpFrame signUp; // 회원가입 페이지 보여주는 프레임
	String state; // 창여러개 뜨는걸 방지하기위해....나중에 자세히 코딩
	FrameControl fc;
	GameUser1 gameUser1;
	GameUser2 gameUser2;
	GameUser3 gameUser3;
	GameUser4 gameUser4;
	GameUser5 gameUser5;
	GameUser6 gameUser6;

	JButton gowaitroom;
	JButton exit;
	JButton roomMake;
	JButton join;
	JButton itemShop;
	JButton avatar;
	JButton roomInfo;
	JButton myInfo;
	JLabel gameRankState;

	int saveRowNum;
	private static String title = "";
	private static String peopleCount = "";
	private static String idList = "";
	private static String roomNum = "";

	public CatchmindFrame(Socket socket) {

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 우측 상단 X버튼을
																	// 눌러도 프레임이
																	// 닫히지 않음

		this.setTitle("Catchmind Game");
		this.setSize(WIDTH, HEIGHT);
		fc = new FrameControl();
		loginPanel = new LoginPanel();
		myInformation = new MyInformation();
		roompanel = new RoomPanel();
		chatpanel = new ChatPanel();
		informationpanel = new InformationPanel();
		gamepanel = new GamePanel();
		avatarpanel = new AvatarPanel(myInformation);
		gameRankState = new JLabel();

		exit = new JButton("종료");
		roomMake = new JButton("방만들기");
		join = new JButton("참여하기");
		itemShop = new JButton("게임아이템");
		avatar = new JButton("아바타샵");
		roomInfo = new JButton("방정보");
		myInfo = new JButton("내정보");
		gowaitroom = new JButton("대기방으로");

		exit.addActionListener(this);
		roomMake.addActionListener(this);
		join.addActionListener(this);
		itemShop.addActionListener(this);
		avatar.addActionListener(this);
		roomInfo.addActionListener(this);
		myInfo.addActionListener(this);

		gowaitroom.addActionListener(this);
		LoginRoom();
		idlistpanel = new IDlistPanel();
		Thread Catchmind;
		Catchmind = new Thread(this); // 스레드 생성
		Catchmind.start();

		SoundEffect.init();

	}

	public void LoginRoom() {

		this.setLayout(null);

		loginPanel.setBounds(200, 150, 380, 180);
		this.add(loginPanel);

	}

	private void removeLoginPalnel() {
		// TODO Auto-generated method stub
		this.remove(loginPanel);
		this.repaint();
	}

	public void WaitRoom() {

		this.setLayout(null);
		chatpanel.chatarea.setText(""); // 채팅한 내용을 초기화
		exit.setBounds(800, 620, 95, 30);
		roomMake.setBounds(100, 20, 95, 30);
		join.setBounds(200, 20, 95, 30);
		roomInfo.setBounds(300, 20, 95, 30);
		itemShop.setBounds(680, 20, 115, 30);
		avatar.setBounds(800, 20, 95, 30);
		myInfo.setBounds(580, 20, 95, 30);
		roompanel.setBounds(100, 70, 500, 280);
		gamepanel.setBounds(250, 0, 500, 450);
		chatpanel.setBounds(100, 350, 500, 250);
		idlistpanel.setBounds(630, 70, 265, 280);
		gowaitroom.setBounds(800, 620, 150, 30);
		avatarpanel.setBounds(630, 350, 260, 250);
		avatarpanel.repaintinfo();
		this.add(avatarpanel);
		this.add(idlistpanel);
		this.add(exit);
		this.add(roomMake);
		this.add(join);
		this.add(roomInfo);
		this.add(itemShop);
		this.add(avatar);
		this.add(myInfo);
		this.add(chatpanel);
		this.add(roompanel);

		SoundEffect.LOGINMUSIC.stop();
		SoundEffect.WAIT.BGM_play();

	}

	private void removeWaitRoom() {
		// TODO Auto-generated method stub

		this.remove(roomMake);
		this.remove(join);
		this.remove(roomInfo);
		this.remove(itemShop);
		this.remove(avatar);
		this.remove(myInfo);
		this.remove(gamepanel);
		this.remove(roompanel);
		this.remove(chatpanel);
		this.remove(idlistpanel);
		this.remove(exit);
		this.remove(avatarpanel);
	}

	public void setGameRoom() {
		SoundEffect.WAIT.stop();
		SoundEffect.BEFOREGAME.BGM_play();
		this.setLayout(null);
		chatpanel.chatarea.setText(""); // 방에 입장하면 채널에서 채팅한 내용을 초기화
		chatpanel.setBounds(250, 450, 500, 200);
		chatpanel.GameRoomChatPanel();
		gameRankState.setBounds(10, 10, 240, 30);
		gameRankState.setText("점수형황:");

		gameUser1 = new GameUser1();
		gameUser2 = new GameUser2();
		gameUser3 = new GameUser3();
		gameUser4 = new GameUser4();
		gameUser5 = new GameUser5();
		gameUser6 = new GameUser6();

		gameUser1.setBounds(20, 10, 200, 200);
		gameUser2.setBounds(20, 210, 200, 200);
		gameUser3.setBounds(20, 410, 200, 200);
		gameUser4.setBounds(760, 10, 200, 200);
		gameUser5.setBounds(760, 210, 200, 200);
		gameUser6.setBounds(760, 410, 200, 200);

		this.add(gameUser1);
		this.add(gameUser2);
		this.add(gameUser3);
		this.add(gameUser4);
		this.add(gameUser5);
		this.add(gameUser6);

		this.add(gamepanel);
		this.add(chatpanel);
		this.add(gowaitroom);
		// this.add(gameRankState);

		repaint();

	}

	public void removeGameRoom() {
		chatpanel.setBounds(250, 450, 500, 200);
		chatpanel.WaitRoomChatPanel();

		this.remove(gameUser1);
		this.remove(gameUser2);
		this.remove(gameUser3);
		this.remove(gameUser4);
		this.remove(gameUser5);
		this.remove(gameUser6);

		this.remove(gamepanel);
		this.remove(chatpanel);
		this.remove(gowaitroom);
		repaint();
		SoundEffect.BEFOREGAME.stop();
		WaitRoom();
	}

	public String getTitle() {
		return title;
	}

	public void setRoomTitle(String title) {
		this.title = title;
	}

	public String getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(String peopleCount) {
		this.peopleCount = peopleCount;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void gameStart() {
		gamepanel.startGame();
		this.remove(gowaitroom);
		repaint();
	}

	public void GameSetInit() {
		gameUser1.SetInit();
		gameUser2.SetInit();
		gameUser3.SetInit();
		gameUser4.SetInit();
		gameUser5.SetInit();
		gameUser6.SetInit();
		gamepanel.getscorenum = 0;
		gamepanel.points = 0;
		gamepanel.showPoints();
	}

	// SoundEffect.init();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String line;

		try {
			while (true) {
				line = CatchmindDriver.getDis().readUTF();

				if (line.startsWith("[Chat]")) // [ / 로 시작하는 메시지면 채팅창에 메시지를 출력
				{
					System.out.println(line);
					chatpanel.chatarea.append(line.substring(6) + "\n");
				} else if (line.startsWith("[ServerChat]")) {
					chatpanel.chatarea.append("[서버공지]:" + line.substring(12)
							+ "\n");
					CatchmindDriver.getDos().writeUTF(line);

				} else if (line.startsWith("[ShowID]")) {
					// myInformation.setNumId(line.substring(8));
					// myInformation.setGameId(line.substring(8));
				} else if (line.startsWith("[Login]")) {
					SoundEffect.SUC_LOGIN.Effect_play();
					JOptionPane.showMessageDialog(SignUpFrame.IDText,
							line.substring(7) + "님 반갑습니다.^^");
					myInformation.setGameId(line.substring(7));
					removeLoginPalnel();

					WaitRoom();

					repaint();

				} else if (line.startsWith("[ShowLv]")) {
					System.out.println("[ShowLv]" + line.substring(8));
					String tem = line.substring(8);
					if (tem == "false")
						tem = "1";
					System.out.println("Integer.parseInt(ax[0])"
							+ Integer.parseInt(tem));
					myInformation.setLevel(Integer.parseInt(tem));
					avatarpanel.setImfo(myInformation);
					avatarpanel.repaintinfo();
				} else if (line.startsWith("[ShowExp]")) {
					String tem = line.substring(9);
					myInformation.setExp(Integer.parseInt(tem));
					avatarpanel.setImfo(myInformation);
					avatarpanel.repaintinfo();
				} else if (line.startsWith("[ShowCoin]")) {
					String tem = line.substring(10);
					myInformation.setCoin(Integer.parseInt(tem));
					// avatarpanel.setImfo(myInformation);
					// avatarpanel.repaintinfo();
				} else if (line.startsWith("[ShowAvatar]")) {
					String tem = line.substring(12);
					myInformation.setAvatar(Integer.parseInt(tem));
					// avatarpanel.setImfo(myInformation);
					// avatarpanel.repaintinfo();
				} else if (line.startsWith("[LoginFail]")) {
					SoundEffect.FAIL_LOGIN.Effect_play();
					String line2 = line.substring(11);
					if (line2.equals("Fail")) {
						JOptionPane.showMessageDialog(LoginPanel.loginPanel,
								"아이디나 비밀번호가 맞지 않습니다. ", "Login Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (line2.equals("Overlap")) {
						JOptionPane.showMessageDialog(LoginPanel.loginPanel,
								"이 아이디는 현재 접속 중입니다.", "Login Error",
								JOptionPane.ERROR_MESSAGE);
					}

				} else if (line.startsWith("[MadeRoom]")) {
					System.out.println(line);
					removeWaitRoom();
					repaint();
					setGameRoom();
					try {
						CatchmindDriver.getDos().writeUTF("[GameSetCancel]");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (line.startsWith("[MakeRoom]")) {
					tokenizer2 = new StringTokenizer(line.substring(10), "\t"); // /t를
																				// 기준으로
																				// col을
																				// 구별
					int i = 0;

					while (tokenizer2.hasMoreTokens()) {
						tokens2[i] = tokenizer2.nextToken();
						i++;
					}
					roompanel.tmodel.addRow(tokens2); // 방 목록 테이블에 만든 방정보를 추가
				}

				// if(line.startsWith("[RoomSize]"))
				// {
				// int j=roompanel.tmodel.getRowCount();
				//
				// System.out.println(j+"j");
				// roompanel.tmodel.fireTableRowsDeleted(0, j);
				//
				// }
				else if (line.startsWith("[Roomlist]")) // [Roomlist] 으로 시작하는
														// 메시지면 room 테이블 변경
				{

					System.out.println(line + "roomlistline");
					tokenizer1 = new StringTokenizer(line.substring(10), "\n"); // row를
																				// \n을
																				// 기준으로
																				// 나눔
					int i = 0;

					while (tokenizer1.hasMoreTokens()) {
						tokens1[i] = tokenizer1.nextToken(); // 나눈 row들을
																// tokens1에 각각
																// 저장

						tokenizer2 = new StringTokenizer(tokens1[i], "\t"); // 나눈
																			// row를
																			// col을\t를
																			// 기준으로
																			// 나눔
						int j = 0;
						System.out.println(i + "tokens1[i]:" + tokens2[i]);

						while (tokenizer2.hasMoreTokens()) {
							tokens2[j] = tokenizer2.nextToken();
							System.out.println(j + "tokens2[j]:" + tokens2[j]);
							j++;

						}
						System.out.println("tokens2:" + tokens2);
						roompanel.tmodel.addRow(tokens2); // 방 목록 테이블에 한개의 row씩
															// 추가
						i++;
					}
					saveRowNum = i;
				} else if (line.startsWith("[EnterRoom]")) {
					System.out.println("클라이언트가 받은 enter메세지"
							+ line.substring(11));
					tokenizer2 = new StringTokenizer(line.substring(11), "\t"); // /t를
																				// 기준으로
																				// col을
																				// 구별
					int i = 0;
					System.out.println(line + "line:값이다enterroom");
					while (tokenizer2.hasMoreTokens()) {
						tokens2[i] = tokenizer2.nextToken(); // tokens[0] 방번호
																// tokens[1] 입장
																// 수 현황
						System.out.println("tokens" + i + "" + tokens2[i]);
						i++;
					}
					for (int row = 0; row < roompanel.tmodel.getRowCount(); row++) // roomtable의
																					// row수만큼
																					// 밤복
					{
						System.out.println("token2[0]:" + tokens2[0]);
						System.out.println("roompanel.tmodel.getValueAt(" + row
								+ ", 0)" + roompanel.tmodel.getValueAt(row, 0));

						if (tokens2[0].equals(roompanel.tmodel.getValueAt(row,
								0))) // 방번호와 0번째 column값이 같으면
						{

							roompanel.tmodel.setValueAt(tokens2[1], row, 2); // 해당
																				// 방의
																				// 인원수
																				// 현황을
																				// 변경
							System.out.println("token2[1]:" + tokens2[1]);
						}
					}
					repaint();

				} else if (line.startsWith("[ExitRoom]")) {
					tokenizer2 = new StringTokenizer(line.substring(10), "\t"); // /t를
																				// 기준으로
																				// col을
																				// 구별
					int i = 0;
					System.out.println("클라이언트 exitroom메세지받을때"
							+ line.substring(10));

					while (tokenizer2.hasMoreTokens()) {
						tokens2[i] = tokenizer2.nextToken(); // tokens[0] 방번호
																// tokens[1] 입장
																// 수 현황

						i++;
					}
					for (int row = 0; row < roompanel.tmodel.getRowCount(); row++) // roomtable의
																					// row수만큼
																					// 밤복
					{

						if (tokens2[0].equals(roompanel.tmodel.getValueAt(row,
								0))) {
							if (tokens2[1].equals("null")) {
								roompanel.tmodel.removeRow(row); // 방에 아무도 없다는
																	// 정보를 받으면
																	// 테이블에서로 해당
																	// 방 정보를 지움
							} else {
								roompanel.tmodel.setValueAt(tokens2[1], row, 2); // 해당
																					// 방의
																					// 인원수
																					// 현황을
																					// 변경
							}
						}
					}
					repaint();
				}

				else if (line.startsWith("[SetGamePanel]")) {
					System.out.println(line);
					removeWaitRoom();
					setGameRoom();
					repaint();
				} else if (line.startsWith("[PositioningUser]")) {
					String msg = line.substring(17);
					String tokens[] = msg.split("\t");

					if (tokens[2].equals("0"))			gameUser1.SetUserInfo(tokens[0], tokens[1]);
					else if (tokens[2].equals("1"))		gameUser2.SetUserInfo(tokens[0], tokens[1]);
					else if (tokens[2].equals("2"))		gameUser3.SetUserInfo(tokens[0], tokens[1]);
					else if (tokens[2].equals("3"))		gameUser4.SetUserInfo(tokens[0], tokens[1]);
					else if (tokens[2].equals("4"))		gameUser5.SetUserInfo(tokens[0], tokens[1]);
					else if (tokens[2].equals("5"))		gameUser6.SetUserInfo(tokens[0], tokens[1]);

				} else if (line.startsWith("[SpreadOtherUsers]")) {
					int Number = Integer.parseInt(line.substring(18, 19));
					String msg = line.substring(19);

					String tokens[] = msg.split("\n");
					String tokens3[];
					for (int i = 0; i < Number; i++) {
						tokens3 = tokens[i].split("\t");

						if (tokens3[2].equals("0"))		gameUser1.SetUserInfo(tokens3[0], tokens3[1]);
						else if (tokens3[2].equals("1"))gameUser2.SetUserInfo(tokens3[0], tokens3[1]);
						else if (tokens3[2].equals("2"))gameUser3.SetUserInfo(tokens3[0], tokens3[1]);
						else if (tokens3[2].equals("3"))gameUser4.SetUserInfo(tokens3[0], tokens3[1]);
						else if (tokens3[2].equals("4"))gameUser5.SetUserInfo(tokens3[0], tokens3[1]);
						else if (tokens3[2].equals("5"))gameUser6.SetUserInfo(tokens3[0], tokens3[1]);
					}
				} else if (line.startsWith("[OutUser]")) {
					int User_Num = Integer.parseInt(line.substring(9, 10));

					if (User_Num == 0)		gameUser1.OutUserInfo();
					else if (User_Num == 1)	gameUser2.OutUserInfo();
					else if (User_Num == 2)	gameUser3.OutUserInfo();
					else if (User_Num == 3)	gameUser4.OutUserInfo();
					else if (User_Num == 4)	gameUser5.OutUserInfo();
					else if (User_Num == 5)	gameUser6.OutUserInfo();

				} else if (line.startsWith("[RoomTitle]")) {
					System.out.println(line);
					System.out.println(line.substring(11));
					setRoomTitle(line.substring(11));

				}

				else if (line.startsWith("[RoomPeopleCount]")) {
					setPeopleCount(line.substring(17));

				}

				else if (line.startsWith("[RoomIdList]")) {
					setIdList(line.substring(12));
					// CatchmindDriver.getDos().writeUTF("[EndSetRoomInfo]");

				} else if (line.startsWith("[RoomNum]")) {
					setRoomNum(line.substring(9));
					fc.loadInfo();
					fc.setVisible(true);
				} else if (line.startsWith("[EndSetInfo]")) {
					// getRoomInfoframe().setVisible(true);
				} else if (line.startsWith("[GameSetReady]")) {
					int Ready = Integer.parseInt(line.substring(14));

					if (Ready == 0)
						gameUser1.SetReady();
					else if (Ready == 1)
						gameUser2.SetReady();
					else if (Ready == 2)
						gameUser3.SetReady();
					else if (Ready == 3)
						gameUser4.SetReady();
					else if (Ready == 4)
						gameUser5.SetReady();
					else if (Ready == 5)
						gameUser6.SetReady();
				} else if (line.startsWith("[GameSetCancel")) {
					int Cancel = Integer.parseInt(line.substring(15));

					if (Cancel == 0)
						gameUser1.SetReadyCancel();
					else if (Cancel == 1)
						gameUser2.SetReadyCancel();
					else if (Cancel == 2)
						gameUser3.SetReadyCancel();
					else if (Cancel == 3)
						gameUser4.SetReadyCancel();
					else if (Cancel == 4)
						gameUser5.SetReadyCancel();
					else if (Cancel == 5)
						gameUser6.SetReadyCancel();

				} else if (line.startsWith("[CorrectAnswer]")) {
					int Answer = Integer.parseInt(line.substring(15, 16));
					int User = Integer.parseInt(line.substring(16, 17));

					if (User == 0)
						gameUser1.SetCorrectAnswer(Answer);
					else if (User == 1)
						gameUser2.SetCorrectAnswer(Answer);
					else if (User == 2)
						gameUser3.SetCorrectAnswer(Answer);
					else if (User == 3)
						gameUser4.SetCorrectAnswer(Answer);
					else if (User == 4)
						gameUser5.SetCorrectAnswer(Answer);
					else if (User == 5)
						gameUser6.SetCorrectAnswer(Answer);

				}

				else if (line.startsWith("[Clientlist]")) // [Clientlist] 로 시작하는
															// 메시지면 접속자 목록을 출력
				{
					idlistpanel.idlistarea.setText("");
					idlistpanel.idlistarea.append(line.substring(12) + "\n");
				} else if (line.startsWith("[GameSetStart]"))// 모든 게임자가 레디를 하면
																// 방장에게
																// start버튼생성
				{
					gamepanel.SetStartButton();
				} else if (line.startsWith("[GameRemoveStart]")) {
					gamepanel.RemoveStartButton();
				} else if (line.startsWith("[SetGameStart]")) {

					gameStart();
				}

				else if (line.startsWith("[GameRandomWord]")) {
					gamepanel.turn = true;
					gamepanel.SetWord("문 제 : " + line.substring(16));
					System.out.println(line.substring(16));
					// chatpanel.MyTurn();
				} else if (line.startsWith("[GameChat]")) // [ / 로 시작하는 메시지면
															// 채팅창에 메시지를 출력
				{
					System.out.println(line);
					chatpanel.chatarea.append(line.substring(10) + "\n");
				} else if (line.startsWith("[GameNextTurn]")) {
					gamepanel.run.FinishTurn();
					chatpanel.FnishMyTurn();
					if (gamepanel.state == "gameOff") {
						gamepanel.EndGame(); // 모든텅이 끝나고 다시 ready버튼생성

						myInformation.upCoin(gamepanel.getCoin());
						myInformation.upExp(gamepanel.getExp());

					}

				} else if (line.startsWith("[GameOneTurnOver]")) {
					gamepanel.run.Turnover();
					chatpanel.FnishMyTurn();
					if (gamepanel.state == "gameOff") {
						gamepanel.EndGame(); // 모든텅이 끝나고 다시 ready버튼생성
						this.add(gowaitroom);
						repaint();

						myInformation.upCoin(gamepanel.getCoin());
						myInformation.upExp(gamepanel.getExp());

					}
				} else if (line.startsWith("[GameEndAllTurn]")) {
					this.add(gowaitroom);
					GameSetInit();

					CatchmindDriver.getDos().writeUTF(
							"[ChangeLv]" + myInformation.getLevel() + "\t"
									+ myInformation.getExp() + "\t"
									+ myInformation.getCoin());
					System.out.println("[GameEndAllTurn]"
							+ myInformation.getLevel());

					System.out.println("[GameEndAllTurn]"
							+ myInformation.getExp());

					System.out.println("[GameEndAllTurn]"
							+ myInformation.getCoin());
					repaint();
				} else if (line.startsWith("[GameRankState]")) {
					gameRankState.setText(line.substring(15));

					System.out.println("점수현향:" + line.substring(15));
				}

				else if (line.startsWith("[LoginIDCheck]")) {
					if (line.substring(14).equals("true")) {
						System.out.println("현재 아이디로 가입이 가능합니다");
						JOptionPane.showMessageDialog(SignUpFrame.IDText,
								"현재 아이디로 가입이 가능합니다");
					} else if (line.substring(14).equals("false")) {
						JOptionPane.showMessageDialog(SignUpFrame.IDText,
								"현재 아이디로 가입이 불가능합니다. " + "다른 아이디를 입력하세요",
								"ID Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if (line.startsWith("[LoginSignUp]")) {
					JOptionPane.showMessageDialog(SignUpFrame.signUpFrame,
							"가입이 완료되었습니다.");

				} else if (line.startsWith("[LoginFindPass]")) {
					if (line.substring(15).trim() != null) {
						JOptionPane.showMessageDialog(
								FindPassFrame.findPassFrame,
								"비밀번호는 : " + line.substring(15) + "입니다.");
					} else if (line.substring(15).trim() == null) {
						JOptionPane.showMessageDialog(
								FindPassFrame.findPassFrame,
								"입력한 정보가 올바르지 않습니다. 다시 한 번 확인해주세요");
					}
				} else if (line.startsWith("[LoginFindID]")) {
					if (line.substring(13).trim() != null) {
						JOptionPane.showMessageDialog(FindIDFrame.findIDFrame,
								"아이디는 : " + line.substring(13) + "입니다.");
					} else if (line.substring(13).trim() == null) {
						JOptionPane.showMessageDialog(FindIDFrame.findIDFrame,
								"입력한 정보가 올바르지 않습니다. 다시 한 번 확인해주세요.");
					}
				} else if (line.startsWith("[Draw]")) {
					String[] tokens = line.split(":");
					String[] xy = tokens[1].split("_");
					gamepanel.canvas.Draw(xy[0], xy[1]);
					repaint();
				} else if (line.startsWith("[Move]")) {
					String[] tokens = line.split(":");
					String[] xy = tokens[1].split("_");
					gamepanel.canvas.Move(xy[0], xy[1]);
					repaint();
				} else if (line.startsWith("[SetColor]")) {
					String[] tokens = line.split(":");
					gamepanel.canvas.setColor(tokens[1]);
					repaint();
				} else if (line.startsWith("[Eraser]")) {
					String[] tokens = line.split(":");
					String[] xy = tokens[1].split("_");
					gamepanel.canvas.Eraser(xy[0], xy[1]);
					repaint();
				} else if (line.startsWith("[GameGetScore]")) {
					gamepanel.UpPoints();
				}
			}
		} catch (IOException e) {
			System.out.println("서버와 연결이 끊어졌습니다.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == exit) {
			SoundEffect.BUTTON.Effect_play();
			try {
				CatchmindDriver.exit(); // 서버와 접속을 해지함 : 메인함수의 socket, dis, dos를
										// close
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setVisible(false); // 종료 버튼을 누르면 프레임을 닫음
			// SoundEffect.BGM.stop(); // 배경음악을 끔
		}

		if (event.getSource() == roomMake) {
			SoundEffect.BUTTON.Effect_play();

			fc.setBounds(300, 150, 400, 200);
			fc.setResizable(false);
			fc.RoomMakeFrame();
			fc.setVisible(true);
		} else if (event.getSource() == roomInfo) {
			SoundEffect.BUTTON.Effect_play();
			int row = -1;
			row = roompanel.roomtable.getSelectedRow(); // 입장할 방을 선택하지 않은 경우
			if (row == -1) {
				JOptionPane.showMessageDialog(join, "방을 선택해주세요", "ID Error",
						JOptionPane.ERROR_MESSAGE);
			} else {

				try {
					fc.RoomInformationFrame();
					CatchmindDriver.getDos().writeUTF(
							"[RequestInfor]"
									+ roompanel.roomtable.getValueAt(row, 0));
					fc.setBounds(300, 150, 400, 500);
					fc.setResizable(false);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (event.getSource() == join) {
			SoundEffect.BUTTON.Effect_play();
			String msg;
			int row = -1;
			row = roompanel.roomtable.getSelectedRow(); // 입장할 방을 선택하지 않은 경우
			if (row == -1) {
				JOptionPane.showMessageDialog(join, "방을 선택해주세요", "ID Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (roompanel.roomtable.getValueAt(row, 2).equals("Full")) // 방
																				// 상태가
																				// Full이면
																				// 입장
																				// 불가
			{
				JOptionPane.showMessageDialog(join, "방이꽉찼습니다", "ID Error",
						JOptionPane.ERROR_MESSAGE);
			} else // 방 상태가 Full이 아니면 서버에 입장을 요청
			{
				msg = "[EnterRoom]" + roompanel.roomtable.getValueAt(row, 0); // 선택한
																				// 방번호를
																				// 받음

				System.out.println("입장!버튼누름msg" + msg);
				try {
					CatchmindDriver.getDos().writeUTF(msg); // [Enter Room] 방번호
															// 메시지를 서버로 보냄
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					CatchmindDriver.getDos().writeUTF("[GameSetCancel]");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		} else if (event.getSource() == myInfo) {
			SoundEffect.BUTTON.Effect_play();
			fc.myInfoFrame(myInformation);
			fc.setBounds(300, 150, 400, 500);
			fc.setResizable(false);
			fc.setVisible(true);
		} else if (event.getSource() == gowaitroom) {
			SoundEffect.BUTTON.Effect_play();
			SoundEffect.INGAME.stop();
			try {
				CatchmindDriver.getDos().writeUTF("[ExitRoom]");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			removeGameRoom();
			WaitRoom();
		}
	}
}
