package Server;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;

import javax.swing.JButton;



public class Server extends Frame implements ActionListener
{
	
	public static ClientController clientcontroller;
	public static RoomController roomcontroller;
	protected ItemController itemcontroller;
	
	
	public static int inPort=5555;
	public static ServerSocket server = null;
	private int count;
	
	
	//*****************************
	ChatPanel svrchat;
	JButton exit;
	//***************************
	
	public static void main(String[] args) { 
		
		Server svr = new Server();  
		
	}

	public Server () {  
		super("server");
		this.setLayout(null);
		setBounds(200, 200, 510, 470);
		setVisible(true);
		

		try {
			server = new ServerSocket (inPort);
			
			clientcontroller = new ClientController();
			roomcontroller = new RoomController();
			itemcontroller = new ItemController();
		//********************************************//
			exit=new JButton("종료");
			svrchat = new ChatPanel();
			
			svrchat.setBounds(10, 30, 500, 400);
			exit.setBounds(400, 430, 100, 30);
			
			exit.addActionListener(this);
			this.add(svrchat);
			this.add(exit);
			repaint();
			
			
			//************************************//
			System.out.println("Server start..");

			// ServerSocket listening
			while(true) {	
				Socket socket = server.accept();

				count++;
				System.out.println(count + " 번째  접속자 : " + socket.getInetAddress());

				Client client = new Client(this,socket);		//새로운 client가 접속하면 모든 clientlist에 추가
				clientcontroller.getClientlist().addElement(client);
				
				System.out.println("현재 접속자 수: "+clientcontroller.getClientlist().size());

				clientcontroller.updateIDlist();	//새로운 client가 접속하면 모든 client들의 접속자 ID list를 업데이트
			}
		} catch (Exception e) { e.printStackTrace(); }
	}

	
	public RoomController getRoomController()
	{
		return roomcontroller;
	}
	public ClientController getClientController()
	{
		return clientcontroller;
	}
	public int getCount()
	{
		return count;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==exit)
		{
			System.out.println("Server exit..");
			System.exit(0);
		}
	}

} // class

