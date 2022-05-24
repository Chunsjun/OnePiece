package Client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GamePanel extends JPanel implements ActionListener {
	public static enum ColorButtons {
		Black, Blue, Green, Yellow, Red, Clear
	};

	public static final String IMG_URL = "images/";
	public static final String SUFFIX_TOOLBAR_BTN = ".gif";
	public static final String SUFFIX_TOOLBAR_BTN_SLT = "SLT.gif";

	static int minute = 0;
	static int second = 0;
	static String state = "gameOff";
	static boolean turn = false;
	static String roomnumber;
	Timer timer;
	static int endturntime = 6; // 턴수제한
	static int limittime = 1; // 게임 시간제한
	static int getscorenum;
	static int points;
	static int coin;
	static int exp;
	WorkTask run;
	private ColorBarHandler colorBarHandler;

	public static JLabel statusBar = new JLabel();
	public static JLabel word;
	public static JLabel turnnum;
	public static JLabel showpoints;

	static CanvasPanel canvas; // 그림 그려주는 패널

	JButton ready; // 게임 준비
	JButton cancel; // 준비 취소
	JButton start;

	TextArea idlistarea; // 방에 있는 IDlist

	public GamePanel() {
		this.setBorder(new TitledBorder(new EtchedBorder(), "게임"));
		this.setLayout(null);
		Timer timer = new Timer();

		timer.schedule(run = new WorkTask(), 0, 1000);

		ready = new JButton("Ready");
		cancel = new JButton("Cancel");
		start = new JButton("Start");
		word = new JLabel();
		showpoints = new JLabel();

		turnnum = new JLabel();
		canvas = new CanvasPanel();

		getscorenum = 0;
		points = 0;
		exp = 0;
		coin = 0;

		ready.setBounds(395, 410, 80, 30);
		cancel.setBounds(395, 410, 80, 30);
		start.setBounds(300, 410, 90, 30);
		statusBar.setBounds(20, 410, 80, 30);
		word.setBounds(100, 10, 170, 30);
		showpoints.setBounds(275, 10, 220, 30);
		turnnum.setBounds(10, 10, 95, 30);
		canvas.setBounds(20, 50, 460, 350);

		statusBar.setText("[0" + minute + "분:" + " " + second + "초]");
		turnnum.setText("턴 : ");
		showpoints.setText("[맞춘문제수] : " + getscorenum + " [얻은점수] : " + points);
		ready.addActionListener(this);
		cancel.addActionListener(this);
		start.addActionListener(this);

		this.add(ready);
		this.add(statusBar);
		this.add(word);
		this.add(showpoints);
		this.add(canvas);
		this.add(turnnum);

		JRadioButton rButton = null;
		colorBarHandler = new ColorBarHandler();
		int x = 100; // 버튼 첫 위치
		for (ColorButtons btn : ColorButtons.values()) {
			rButton = new JRadioButton();
			rButton.setIcon(new ImageIcon(IMG_URL + btn.toString()
					+ SUFFIX_TOOLBAR_BTN));
			rButton.setSelectedIcon(new ImageIcon(IMG_URL + btn.toString()
					+ SUFFIX_TOOLBAR_BTN_SLT));
			rButton.addActionListener(colorBarHandler);
			rButton.setActionCommand(btn.toString()); // btn : 버튼을 가진 열거타입
			rButton.setBounds(x, 410, 30, 30);
			this.add(rButton);
			x += 30;
		}

	}

	public void showPoints() {
		showpoints.setText("[맞춘문제수] : " + getscorenum + " [얻은점수] : " + points);
		repaint();
	}

	public int getCoin() {
		return coin;
	}

	public int getExp() {
		return exp;
	}

	public void UpPoints() {
		points += 10;
		coin += 10;
		exp += 10;
		getscorenum++;
		ChangePoints();
		SendPoint();
	}

	public void ChangePoints() {
		showpoints.setText("[맞춘문제수] : " + getscorenum + " [얻은점수] : " + points);
		try {
			      CatchmindDriver.getDos().writeUTF("[CorrectAnswer]" + getscorenum);
			    } catch (IOException e1) {
			      // TODO Auto-generated catch block
			      e1.printStackTrace();
			    }
	}

	public void SendPoint() {
		try {
			CatchmindDriver.getDos().writeUTF("[GameSendPoint]" + points);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void SetStartButton() {
		this.add(start);
		repaint();
	}

	public void RemoveStartButton() {
		this.remove(start);
		repaint();
	}

	public void EndGame() {
		this.add(ready);
		minute = 0;
		second = 0;
		canvas.ClearAllUser();
		state = "gameOff";
		turnnum.setText("턴 : ");
		turn = false;
		try {
			CatchmindDriver.getDos().writeUTF("[GameEndAllTurn]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startGame() {
		SoundEffect.BEFOREGAME.stop();
		SoundEffect.INGAME.BGM_play();
		this.remove(start);
		this.remove(ready);
		this.remove(cancel);
		state = "gameOn";
		points = 0;
		coin = 0;
		exp = 0;
		getscorenum = 0;
		points = 0;
		showpoints.setText("[맞춘문제수] : " + getscorenum + " [얻은점수] : " + points);
		try {
			CatchmindDriver.getDos().writeUTF("[GameGetWord]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}

	public void SetWord(String msg) {
		word.setText(msg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Component event = (Component) e.getSource();

		if (event == ready) {
			SoundEffect.BUTTON.Effect_play();
			this.remove(ready);
			this.add(cancel);
			System.out.println("눌림");
			try {
				CatchmindDriver.getDos().writeUTF("[GameSetReady]");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			repaint();
		}
		if (event == cancel) {
			SoundEffect.BUTTON.Effect_play();
			this.remove(cancel);
			this.add(ready);
			try {
				CatchmindDriver.getDos().writeUTF("[GameSetCancel]");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			repaint();
		}
		if (event == start) {
			SoundEffect.BUTTON.Effect_play();
			try {
				CatchmindDriver.getDos().writeUTF("[GameStart]");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void Init() {

	}

	public static class WorkTask extends TimerTask {

		int turnNum = 0;
		int i = 0;

		@Override
		public void run() {
			String msg;
			if (state == "gameOn") {

				GamePanel.statusBar.setText("[0" + minute + "분:" + " " + second
						+ "초]");
				// timeprint();
				i = i + 1;
				if (second != 60) {
					second = i;
				}
				// System.out.println(i);
				if (second == 60) {
					i = 0;
					second = i;
					minute++;

					if (minute == limittime) {

						try {
							CatchmindDriver.getDos().writeUTF(
									"[GameOneTurnOver]");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						state = "gameOff";

					}
				}

			}
		}

		public static String nextTurn() {
			return "next";

		}

		public void changeState() {
			state = "gameOn";
		}

		public void Turnover() // 아무도 정답을못마췃을때 다음 턴으로
		{
			second = 0;
			minute = 0;
			i = 0;
			System.out.println("쿨라이언트턴" + turnNum + "종료" + "turnover");
			state = "gameOn";
			word.setText("");

			turn = false;
			canvas.ClearAllUser();
			turnNum++;
			SoundEffect.FAIL_TURN.Effect_play();
			turnnum.setText("턴 : " + turnNum);
			if (turnNum > endturntime) {
				state = "gameOff";
				turnNum = 0;
				GamePanel.statusBar.setText("[0" + minute + "분:" + " " + second
						+ "초]");
			}
			System.out.println("클라이언트턴" + turnNum + "시작" + "turnover");
		}

		public void FinishTurn() // 누군가 정답을 맞췃을때 다음턴으로
		{
			second = 0;
			i = 0;
			minute = 0;
			System.out.println("쿨라이언트턴" + turnNum + "종료" + "finishturn");
			canvas.ClearAllUser();
			word.setText("");
			turn = false;
			turnNum++;
			SoundEffect.COR_TURN.Effect_play();
			turnnum.setText("턴 : " + turnNum);
			if (turnNum > endturntime) {
				SoundEffect.INGAME.stop();
				SoundEffect.BEFOREGAME.BGM_play();
				state = "gameOff";
				turnNum = 0;
				GamePanel.statusBar.setText("[0" + minute + "분:" + " " + second
						+ "초]");
			}
			System.out.println("클라이언트턴" + turnNum + "시작" + "finishturn");

		}
	}

	private class ColorBarHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JRadioButton button = (JRadioButton) e.getSource();
			SoundEffect.BRUSH.Effect_play();
			if (button.getActionCommand().equals("Black")) {
				canvas.actionPerformed(e);
			} else if (button.getActionCommand().equals("Blue")) {
				canvas.actionPerformed(e);
			} else if (button.getActionCommand().equals("Green")) {
				canvas.actionPerformed(e);
			} else if (button.getActionCommand().equals("Yellow")) {
				canvas.actionPerformed(e);
			} else if (button.getActionCommand().equals("Red")) {
				canvas.actionPerformed(e);
			} else if (button.getActionCommand().equals("Clear")) {
				canvas.actionPerformed(e);
			}

		}
	}
}
