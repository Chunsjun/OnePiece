package Login;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Client.CatchmindDriver;
import Client.SoundEffect;

public class FindPassFrame extends JFrame implements ActionListener {
	private int WIDTH = 300;
	private int HEIGHT = 150;
	public static Component findPassFrame;
	
	JLabel NameLabel;
	JLabel IDLabel;
	JLabel RegiNumLabel;
	JButton FindPass;
	JButton Close;

	TextField NameText;
	TextField IDText;
	TextField RegiNumText;
	String name;
	String id;
	String reginum;
	
	public FindPassFrame(){
		new TitledBorder(new EtchedBorder(), "비밀번호 찾기");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		// 우측 상단 X버튼을 눌러도 프레임이 닫히지 않음

		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(null);
		
		NameLabel = new JLabel("이름");
		NameText = new TextField();
		IDLabel = new JLabel("아이디");
		IDText = new TextField();
		RegiNumLabel = new JLabel("주민번호");
		RegiNumText = new TextField();
		FindPass = new JButton("비밀번호 찾기");
		Close = new JButton("닫기");

		
		NameLabel.setBounds(20, 15, 70, 30);
		NameText.setBounds(90, 20, 80, 20);
		IDLabel.setBounds(20, 45, 70, 30);
		IDText.setBounds(90, 50, 80, 20);
		RegiNumLabel.setBounds( 20, 75, 70, 30);
		RegiNumText.setBounds(90, 80, 80, 20);
		FindPass.setBounds(190, 50, 80, 20);
		Close.setBounds(190, 80, 80, 20);

		
		this.add(NameLabel);
		this.add(NameText);
		this.add(IDLabel);
		this.add(IDText);
		this.add(RegiNumLabel);
		this.add(RegiNumText);
		this.add(FindPass);
		this.add(Close);

		FindPass.addActionListener(this);
		Close.addActionListener(this);

	}
	
	public void actionPerformed(ActionEvent e) {
		Component event = (Component) e.getSource();
		name = NameText.getText().trim();
		id = IDText.getText().trim();
		reginum = RegiNumText.getText().trim();
		
		if(event == Close){
			SoundEffect.BUTTON.Effect_play();
			this.setVisible(false);
			NameText.setText("");
			IDText.setText("");
			RegiNumText.setText("");
		}else if(event == FindPass){
			SoundEffect.BUTTON.Effect_play();
			
			//서버에게 비밀번호 찾기 보내기
			if (name.length() == 0 || name == null) {
				JOptionPane.showMessageDialog(NameText, "이름을 입력하세요", "NAME Error",
						JOptionPane.ERROR_MESSAGE);
			}else if (id.length() == 0 || id == null) {
				JOptionPane.showMessageDialog(RegiNumText, "아이디를 입력하세요",
						"ID Error", JOptionPane.ERROR_MESSAGE);
			}else if (reginum.length() == 0 || reginum == null) {
				JOptionPane.showMessageDialog(RegiNumText, "주민번호6자리를 입력하세요",
						"Regi_Number Error", JOptionPane.ERROR_MESSAGE);
			}else{
				try {
					CatchmindDriver.getDos().writeUTF(
							"[LoginFindPass]" + name + "\t" + id + "\t" + reginum);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
