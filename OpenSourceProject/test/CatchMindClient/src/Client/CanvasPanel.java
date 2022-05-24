package Client;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Graphics2D;
/**
 * 
 * @author Administrator client
 */
public class CanvasPanel extends Canvas implements MouseMotionListener, ActionListener {
	private Socket socket;


	private int x = 0;
	private int y = 0;

	Color pp;				//지우개나 초기화시 색깔 보관
	Color t = Color.black;	//색깔 저장 초기에는 검은색
	
	String msg;
	
	public CanvasPanel() {
		// TODO Auto-generated constructor stub
		super();
		addMouseMotionListener(this);
		setBackground(Color.WHITE);
		
	}
	
	public void setColor(String c){
		if(c.equals("Black")){
			this.getGraphics().setColor(Color.black);
			t = Color.black;
		}
		else if(c.equals("Blue")){
			System.out.println("Blue");
			this.getGraphics().setColor(Color.blue);
			t = Color.blue;
		}
		else if(c.equals("Green")){
			this.getGraphics().setColor(Color.green);
			t = Color.green;
		}
		else if(c.equals("Yellow")){
			this.getGraphics().setColor(Color.yellow);
			t = Color.yellow;
		}
		else if(c.equals("Red")){
			this.getGraphics().setColor(Color.red);
			t = Color.red;
		}
		else if(c.equals("Clear")){
			pp = this.getGraphics().getColor();
			this.getGraphics().setColor(Color.white);
			this.getGraphics().clearRect(0, 0, 460, 350);
			this.getGraphics().setColor(pp);
		}
	}
	
	public void ClearAllUser()
	{
		pp = this.getGraphics().getColor();
		this.getGraphics().setColor(Color.white);
		this.getGraphics().clearRect(0, 0, 460, 350);
		this.getGraphics().setColor(pp);
		
		t = Color.black;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(GamePanel.turn==true)
		{
		Graphics gs = this.getGraphics();
		if(e.getModifiers() == InputEvent.BUTTON1_MASK){
			if(t.equals(Color.black))			gs.setColor(t);
			else if(t.equals(Color.blue))		gs.setColor(t);
			else if(t.equals(Color.green))		gs.setColor(t);
			else if(t.equals(Color.yellow))		gs.setColor(t);
			else if(t.equals(Color.red))		gs.setColor(t);
			gs.drawLine(x, y, e.getX(), e.getY());
			
			x = e.getX();
			y = e.getY();
			
			msg = "[Draw]:" + x + "_" + y;
			try {
				CatchmindDriver.getDos().writeUTF(msg);
				CatchmindDriver.getDos().flush();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			
		}
		else if(e.getModifiers() == InputEvent.BUTTON3_MASK){
			pp = this.getGraphics().getColor();
		
			this.getGraphics().clearRect(e.getX(), e.getY(), 30, 30);
			
			x = e.getX();
			y = e.getY();
			
			this.getGraphics().setColor(pp);
			
			msg = "[Eraser]:" + x + "_" + y;
			try {
				CatchmindDriver.getDos().writeUTF(msg);
				CatchmindDriver.getDos().flush();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(GamePanel.turn==true)
		{
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		msg = "[Move]:" + x + "_" + y;
	
		try {
			CatchmindDriver.getDos().writeUTF(msg);
			CatchmindDriver.getDos().flush();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
	}
	
	public void Draw(String x1, String y1){
		if(GamePanel.turn==false)
		{
		Graphics gs = this.getGraphics();
		if(t.equals(Color.black))			gs.setColor(t);
		else if(t.equals(Color.blue))		gs.setColor(t);
		else if(t.equals(Color.green))		gs.setColor(t);
		else if(t.equals(Color.yellow))		gs.setColor(t);
		else if(t.equals(Color.red))		gs.setColor(t);
		
		gs.drawLine(x, y, Integer.parseInt(x1), Integer.parseInt(y1));
		this.x = Integer.parseInt(x1);
		this.y = Integer.parseInt(y1);
		}
	}
	public void Move(String x1, String y1){
		if(GamePanel.turn==false)
		{
		this.x = Integer.parseInt(x1);
		this.y = Integer.parseInt(y1);
		}
	}
	public void Eraser(String x, String y){
		if(GamePanel.turn==false)
		{
		pp = this.getGraphics().getColor();
		this.getGraphics().clearRect(Integer.parseInt(x), Integer.parseInt(y), 30, 30);
		this.x = Integer.parseInt(x);
		this.y = Integer.parseInt(y);
		this.getGraphics().setColor(pp);
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JRadioButton button = (JRadioButton)e.getSource();
		String source = button.getActionCommand();
		if(GamePanel.turn==true)
		{
		if(source.equals("Red")){
			setColor("Red");
			try {
				CatchmindDriver.getDos().writeUTF("[SetColor]:Red");
				CatchmindDriver.getDos().flush();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if(source.equals("Black")){
			setColor("Black");
			try {
				CatchmindDriver.getDos().writeUTF("[SetColor]:Black");
				CatchmindDriver.getDos().flush();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if(source.equals("Green")){
			setColor("Green");
			try {
				CatchmindDriver.getDos().writeUTF("[SetColor]:Green");
				CatchmindDriver.getDos().flush();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if(source.equals("Yellow")){
			setColor("Yellow");
			try {
				CatchmindDriver.getDos().writeUTF("[SetColor]:Yellow");
				CatchmindDriver.getDos().flush();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if(source.equals("Blue")){
			setColor("Blue");
			try {
				CatchmindDriver.getDos().writeUTF("[SetColor]:Blue");
				CatchmindDriver.getDos().flush();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if(source.equals("Clear")){
			setColor("Clear");
			try {
				CatchmindDriver.getDos().writeUTF("[SetColor]:Clear");
				CatchmindDriver.getDos().flush();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	}
}
