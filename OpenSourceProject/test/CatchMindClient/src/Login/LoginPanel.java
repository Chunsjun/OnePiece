package Login;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.*;
import Client.CatchmindDriver;
import Client.CatchmindFrame;
import Client.SoundEffect;

public class LoginPanel extends JPanel implements ActionListener {
	public static Component loginPanel;

	SignUpFrame signUp;

	private int WIDTH = 380;
	private int HEIGHT = 180;
	private boolean LoginCheck = false; // 로그인되어있는지 확인하는 변수

	FindIDFrame findIDFrame;
	FindPassFrame findPassFrame;

	JLabel IDLabel;
	JLabel PasswordLabel;
	JButton ForgotID;
	JButton ForgotPS;
	TextField IDText;
	TextField PasswordText;
	JButton Login;
	JButton SignUp;
	JButton exit;

	public LoginPanel() {
		this.setBorder(new TitledBorder(new EtchedBorder(), "Login"));
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(null);

		signUp = new SignUpFrame();

		IDLabel = new JLabel("ID", 10);
		PasswordLabel = new JLabel("Password", 10);
		IDText = new TextField(20);
		PasswordText = new TextField(20);
		Login = new JButton("Login");
		ForgotID = new JButton("ID 찾기");
		ForgotPS = new JButton("비밀번호 찾기");
		SignUp = new JButton("SignUp");
		exit = new JButton("Exit");
		findIDFrame = new FindIDFrame();
		findPassFrame = new FindPassFrame();

		IDLabel.setBounds(50, 45, 70, 30);
		IDText.setBounds(140, 50, 100, 20);
		PasswordLabel.setBounds(50, 75, 70, 30);
		PasswordText.setBounds(140, 80, 100, 20);
		PasswordText.setEchoChar('*');
		Login.setBounds(270, 45, 80, 60);
		ForgotID.setBounds( 40, 120, 80, 20);
		ForgotPS.setBounds(130, 120, 120, 20);
		SignUp.setBounds(270, 120, 80, 20);
		exit.setBounds(270, 150, 80, 20);

		this.add(IDLabel);
		this.add(PasswordLabel);
		this.add(ForgotID);
		this.add(ForgotPS);
		this.add(IDText);
		this.add(PasswordText);
		this.add(Login);
		this.add(SignUp);
		this.add(exit);

		// ActionListener 생성
		SignUp.addActionListener(this);
		Login.addActionListener(this);
		exit.addActionListener(this);
		ForgotID.addActionListener(this);
		ForgotPS.addActionListener(this);
		
		SoundEffect.LOGINMUSIC.BGM_play();
	}
	
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		Component event = (Component) e.getSource();

		if (event == SignUp) {
			SoundEffect.BUTTON.Effect_play();
			signUp.setVisible(true);
		} else if (event == ForgotID) {	// 아이디 찾기를 클릭 했을 때
			SoundEffect.BUTTON.Effect_play(); 
			//아이디 찾기 패널 보여줌
			findIDFrame.setVisible(true);
		} else if (event == ForgotPS) {	// 비밀번호 찾기를 클릭 했을 때
			SoundEffect.BUTTON.Effect_play(); 
			//비밀번호 찾기 패널 보여줌
			findPassFrame.setVisible(true);
		} else if (event == Login) {
			SoundEffect.BUTTON.Effect_play(); 
			String ID = IDText.getText().trim();
			String PW = PasswordText.getText().trim();
			if (ID.length() == 0 || ID == null) {
				JOptionPane.showMessageDialog(Login, "아이디를 입력하세요", "ID Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (PW.length() == 0 || PW == null) {
				JOptionPane.showMessageDialog(Login, "비밀번호를 입력하세요",
						"Password Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					CatchmindDriver.getDos().writeUTF("[Login]" + ID + "\t" + PW);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}

		if (event == exit) {
			SoundEffect.BUTTON.Effect_play();
			SoundEffect.LOGINMUSIC.stop();
			try {
				CatchmindDriver.exit(); // 서버와 접속을 해지함 : 메인함수의 socket, dis, dos를
										// close
			} catch (IOException ea) {
				ea.printStackTrace();
			}
			CatchmindDriver.getFrame().setVisible(false); // 종료 버튼을 누르면 프레임을 닫음
			// SoundEffect.BGM.stop(); // 배경음악을 끔

		}
	}

}
