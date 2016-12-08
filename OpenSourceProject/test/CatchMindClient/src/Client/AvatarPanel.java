package Client;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.image.ImageProducer;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class AvatarPanel extends JPanel
{
//	private int WIDTH = 200;
//	private int HEIGHT = 270;

	JRadioButton b;
//	JLabel level;
//	JLabel exp;
	TextField level;
	TextField exp;
	int lv;
	int ex;
	
	public AvatarPanel(MyInformation info)
	{
//		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setBorder(new TitledBorder(new EtchedBorder(),"아바타"));

		this.setLayout(null);
		b= new JRadioButton(); 			// 메시지를 보여주는 영역
//		level = new JLabel();
//		exp = new JLabel();
		level =new TextField() ;
		exp= new TextField();
		lv = info.getLevel();
		ex = info.getExp();

		
		
		b.setBounds(40, 20, 170, 170);
		level.setBounds(50, 195, 50, 20);
		exp.setBounds(150, 195, 100, 20);
		
//		level.setText(""+lv);
//		exp.setText(""+exp);
		b.setIcon(new ImageIcon("images/Avatar1.gif"));
		
		this.add(b);
		this.add(level);
		this.add(exp);
	}
	
	public void setImfo(MyInformation info)
	{
		lv=info.getLevel();
		ex=info.getExp();
	}
	

	public void repaintinfo()
	{
		level.setText("Lv : " + lv);
		exp.setText("Exp : " + ex);
		repaint();
	}
}