package project;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

/*
 * IP, Port 정보 SystemProperties를 이용해서 가져올것
 * 
 * */

public class Client extends Frame implements ActionListener {

	private TextArea ta = new TextArea();
	private TextField tf = new TextField();
	private Button sendB = new Button("Send");
	private Panel p = new Panel();
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	//private String ip;
	private int port;
	private String name;

	
	
	public Client() {
		createGUI();
	}

	// GUI 생성하고 Event 등록,처리
	public void createGUI() {
		p.add(sendB);
		add(ta, "West");
		add(p, "Center");

		add(tf, "South");

		setBounds(200, 200, 400, 400);
		setVisible(true);

		// Event 등록
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
				System.exit(0);
			}
		});

		// 버튼에 핸들러 등록
		sendB.addActionListener(this);
		tf.addActionListener(this);

	}
	
	private void close(){
		try {
			ois.close();
			oos.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void go(String ip, int port, String name) {
		
		this.name = name;
		
		try {
			// 1. Socket 생성
			socket = new Socket(ip, port);
			
			// 2. I/O stream 생성
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			// Thread 만들기...
			new ChatClientThread().start();

			
		} catch (Exception e) {
			ta.append("서버가 시작중인지,IP와port가 맞는지 확인 바랍니다.");
		}
	}

	public void actionPerformed(ActionEvent e) {

		Object selObj = e.getSource();
		if (selObj == tf || selObj == sendB) {
			String sendMsg = tf.getText().trim();
			try {
				oos.writeObject(" " +name+" )" + sendMsg);
				tf.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {

		}

	}

	public static void main(String[] args) {
		
		String ip = "210.119.33.102";
		
//		try {
//			ip = "210.119.33.102";
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Properties prop = System.getProperties();

		int port = 8888;
		String name = "C";
		
		System.out.println(ip+", "+port+", "+name);
		new Client().go(ip, port, name);
	}

	class ChatClientThread extends Thread {
		@Override
		public void run() {

			while (true) {
				try {
					String recMsg = ois.readObject().toString();
					ta.append(recMsg + "\n");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}// Inner
}// outer