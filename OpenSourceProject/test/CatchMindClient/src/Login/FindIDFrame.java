package Login;

import java.awt.*;
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

public class FindIDFrame extends JFrame implements ActionListener  {
	
	public static Component findIDFrame;
	private int WIDTH = 300;
	private int HEIGHT = 150;
	
	JLabel NameLabel;
	JLabel RegiNumLabel;
	JButton FindID;
	TextField NameText;
	TextField RegiNumText;
	JButton Close;

	public FindIDFrame(){
		new TitledBorder(new EtchedBorder(), "ID 찾기");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		// 우측 상단 X버튼을 눌러도 프레임이 닫히지 않음

		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(null);
		
		NameLabel = new JLabel("이름");
		NameText = new TextField();
		RegiNumLabel = new JLabel("주민번호앞6자리");
		RegiNumText = new TextField();
		FindID = new JButton("ID찾기");
		Close = new JButton("닫기");

		NameLabel.setBounds(20, 15, 70, 30);
		NameText.setBounds(90, 20, 80, 20);
		RegiNumLabel.setBounds( 20, 55, 70, 50);
		RegiNumText.setBounds(90, 60, 80, 20);
		FindID.setBounds(190, 60, 80, 20);
		Close.setBounds(190, 90, 80, 20);

		this.add(NameLabel);
		this.add(NameText);
		this.add(RegiNumLabel);
		this.add(RegiNumText);
		this.add(FindID);
		this.add(Close);

		FindID.addActionListener(this);
		Close.addActionListener(this);

	}
	
	public void actionPerformed(ActionEvent e) {
		Component event = (Component) e.getSource();
		
		if(event == Close){
			SoundEffect.BUTTON.Effect_play();
			this.setVisible(false);
			NameText.setText("");
			RegiNumText.setText("");
		}else if(event == FindID){
			SoundEffect.BUTTON.Effect_play();
			//서버에게 이름과 주민번호를 보내 아이디를 찾음
			
			String name = NameText.getText().trim();
			String reginum = RegiNumText.getText().trim();
			
			if (name.length() == 0 || name == null) {
				JOptionPane.showMessageDialog(NameText, "이름을 입력하세요", "NAME Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (reginum.length() == 0 || reginum == null) {
				JOptionPane.showMessageDialog(RegiNumText, "주민번호6자리를 입력하세요",
						"Regi_Number Error", JOptionPane.ERROR_MESSAGE);
			}else{
				try {
					CatchmindDriver.getDos().writeUTF(
							"[LoginFindID]" + name + "\t" + reginum);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
