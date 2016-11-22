package Login;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.*;

import Client.CatchmindDriver;
import Client.SoundEffect;

public class SignUpFrame extends JFrame implements ActionListener{
	
	
	public static final String NAMEText = null;
	private int WIDTH = 380;
	private int HEIGHT = 400;
	
	
	JLabel NameLabel;
	JLabel IDLabel;
	JLabel PassLabel;
	JLabel RPassLabel;
	JLabel RegiNumLabel;
	TextField NameText;
	public static TextField IDText;
	public static Component signUpFrame;
	public TextField PassText;
	public TextField RPassText;
	public TextField RegiNumText;
	JButton IDCheck;
	JButton SignUp;
	JButton Close;
	
	int ID_CHECK=0;
	
	public SignUpFrame(){
		new TitledBorder(new EtchedBorder(),"Sign-Up");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		// 우측 상단 X버튼을 눌러도 프레임이 닫히지 않음

		this.setSize(new Dimension(WIDTH,HEIGHT));

		this.setLayout(null);
		
		NameLabel = new JLabel("이름",10);
		IDLabel = new JLabel("아이디",10);
		PassLabel = new JLabel("비밀번호",10);
		RPassLabel = new JLabel("비밀번호 확인",10);
		RegiNumLabel = new JLabel("주민등록번호",10);
		NameText = new TextField(20);
		IDText = new TextField(20);
		PassText = new TextField(20);
		PassText.setEchoChar('*');
		RPassText = new TextField(20);
		RPassText.setEchoChar('*');
		RegiNumText = new TextField(20);
		IDCheck = new JButton("중복확인");
		SignUp = new JButton("회원가입");
		Close = new JButton("닫기");
		
		NameLabel.setBounds(50, 45, 90, 30);
		IDLabel.setBounds(50, 85, 90, 30);
		PassLabel.setBounds(50, 125, 90, 30);
		RPassLabel.setBounds(50, 165, 90, 30);
		RegiNumLabel.setBounds(50, 205, 90, 30);
		NameText.setBounds(150, 50, 90, 20);
		IDText.setBounds(150, 90, 90, 20);
		PassText.setBounds(150, 130, 90, 20);
		RPassText.setBounds(150, 170, 90, 20);
		RegiNumText.setBounds(150, 210, 90, 20);
		IDCheck.setBounds(250, 90, 90, 20);
		SignUp.setBounds(80, 300, 90, 30);
		Close.setBounds(190, 300, 90, 30);
		
		this.add(NameLabel);
		this.add(IDLabel);
		this.add(PassLabel);
		this.add(RPassLabel);
		this.add(RegiNumLabel);
		this.add(NameText);
		this.add(IDText);
		this.add(PassText);
		this.add(RPassText);
		this.add(RegiNumText);
		this.add(IDCheck);
		this.add(SignUp);
		this.add(Close);
		
		IDCheck.addActionListener(this);
		SignUp.addActionListener(this);
		Close.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e)
	{	
		Component event = (Component)e.getSource();
		String name = NameText.getText().trim();
		String id = IDText.getText().trim();
		String pass = PassText.getText().trim();
		String rpass = RPassText.getText().trim();
		String reginum = RegiNumText.getText().trim();
		
		if(event == Close)
		{
			SoundEffect.BUTTON.Effect_play();
			this.setVisible(false);
			NameText.setText("");
			IDText.setText("");
			PassText.setText("");
			RPassText.setText("");
			RegiNumText.setText("");
		}
		else if(event == IDCheck)
		{
			SoundEffect.BUTTON.Effect_play();
			if(id.length() == 0 || id == null){
				JOptionPane.showMessageDialog(IDText, "아이디를 입력하세요",
						"아이디 입력", JOptionPane.INFORMATION_MESSAGE);
			}else{
				ID_CHECK = 1;
				// Server로 id를 보내 중복되는 id가 있는지 확인
				try {
					CatchmindDriver.getDos().writeUTF("[LoginIDCheck]" + id);
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			
				}
			}
		}
		// 회원가입 버튼을 눌렀을 때
		else if(event == SignUp)
		{
			SoundEffect.BUTTON.Effect_play();
			//이름이 빈칸인지 확인
			if(name.length() == 0 || name == null){
			JOptionPane.showMessageDialog(NameText, "이름을 입력하세요.",
					"이름 입력", JOptionPane.INFORMATION_MESSAGE);
			}
			//아이디 빈칸인지 확인
			else if(id.length() == 0 || id == null){
			JOptionPane.showMessageDialog(NameText, "아이디를 입력하세요.",
					"아이디 입력", JOptionPane.INFORMATION_MESSAGE);
			}
			// 아이디 중복 체크했는지 확인
			else if(ID_CHECK != 1){
				JOptionPane.showMessageDialog(IDCheck, "아이디가 중복되는지 확인하세요",
						"아이디 중복 체크", JOptionPane.INFORMATION_MESSAGE);
			}
			// 비밀번호란이 빈 칸인지 확인
			else if(pass.length() == 0 || pass == null){
				JOptionPane.showMessageDialog(PassText, "비밀번호를 입력하세요",
						"비밀번호 확인", JOptionPane.INFORMATION_MESSAGE);
			}
			// 비밀번호 확인란이 빈 칸인지 확인
			else if(rpass.length() == 0 || rpass == null){
				JOptionPane.showMessageDialog(PassText, "비밀번호 확인을 입력하세요",
						"비밀번호 확인", JOptionPane.INFORMATION_MESSAGE);
			}
			// 비밀번호 두 개가 동일한지 확인
			else if(!pass.equals(rpass)){
				JOptionPane.showMessageDialog(PassText, "비밀번호가 일치하지 않습니다.",
						"비밀번호 확인", JOptionPane.INFORMATION_MESSAGE);
			}
			// 주민등록번호가 6자리인지 확인
			else if(reginum.length() != 6 || reginum == null){
				JOptionPane.showMessageDialog(PassText, "주민번호 처음 6자리를 입력하세요",
						"주민등록번호 확인", JOptionPane.INFORMATION_MESSAGE);
			}
			//아이디 입력 텍스트에 빈칸인지 확인하고 빈칸이면 메시지 출력
			else {
				try {
					CatchmindDriver.getDos().writeUTF
					("[LoginSignUp]" + name + "\t" + id + "\t" + pass + "\t" + reginum);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
