package GameUser;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

 public class GameUser1 extends JPanel {
	 
	 private int WIDTH=200;
	 private int HIGHT=180;
	 
	 JLabel ID;
	 JLabel Level;
	 JButton Status;
	 JLabel CatchAnswer;
	 JRadioButton Avatar;

	 public GameUser1()
	 {
		 this.setSize(new Dimension (WIDTH, HIGHT));
		 this.setBorder(new TitledBorder(new EtchedBorder(),"User1"));
		 
		 this.setLayout(null);   
		 
		 ID = new JLabel();
		 Level = new JLabel("Lv : ");
		 Status = new JButton("준비");
		 CatchAnswer = new JLabel();
		 Avatar = new JRadioButton(); 			// 메시지를 보여주는 영역

		 ID.setBounds(110, 20, 80, 20);
		 Level.setBounds(110, 45, 80, 20);
		 CatchAnswer.setBounds(110, 70, 80, 20);
		 Status.setBounds(110, 100, 60, 40);
		 Avatar.setBounds(10, 10, 100, 160);
		 Avatar.setIcon(new ImageIcon("images/Avatar1.gif"));
	}

	public void SetUserInfo(String id, String level) {
		this.setLayout(null);
		this.add(ID);
		this.add(Level);
		this.add(CatchAnswer);
		// this.add(Avatar);

		this.ID.setText(id);
		this.Level.setText("Lv : " + level);
		this.CatchAnswer.setText("정답 : 0");
	}

	public void SetReady() {
		this.setLayout(null);
		this.add(Status);
		repaint();
	}

	public void SetInit() {
		this.setLayout(null);
		this.remove(Status);
		this.CatchAnswer.setText("정답 : 0");
		repaint();
	}
	public void SetReadyCancel() {
		this.setLayout(null);
		this.remove(Status);
		repaint();
	}
	public void SetCorrectAnswer(int Answer){
		this.setLayout(null);
		this.CatchAnswer.setText("정답 : " + Answer);
		repaint();
	}
	
	// 플레이어가 나갔을 떄 나간 플레이어 자리를 빈 공간으로 바꿔주는 함수
	public void OutUserInfo(){
		this.setLayout(null);
		this.ID.setText("");
		this.Level.setText("");
		this.CatchAnswer.setText("");
		this.remove(ID);
		this.remove(Level);
		this.add(CatchAnswer);
		
		repaint();
	}
}
