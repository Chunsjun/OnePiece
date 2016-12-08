package Client;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Client.SoundEffect;


public class CatchmindDriver 
{

	private static String serverip;
	private static int port;
	private static Socket socket;
	private static DataInputStream dis;
	private static DataOutputStream dos;
	private static CatchmindFrame frame;
	

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		serverip= "localhost";			//서버의 주소
		port = 5555;					//통신할 포트 번호		

		try {
			socket = new Socket(CatchmindDriver.getServerIp(), CatchmindDriver.getPort());

			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			frame= new CatchmindFrame(socket);

			frame.setVisible(true);
			

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버 연결 실패 (IP와 포트를 확인해주세요.)");
		}

	}

	public static void exit() throws IOException
	{
		dis.close();
		dos.close();
		socket.close();
	}
	public static String getServerIp()
	{
		return serverip;
	}

	public static int getPort()
	{
		return port;
	}

	public static Socket getSocket()
	{
		return socket;
	}

	public static DataInputStream getDis()
	{
		return dis;
	}

	public static DataOutputStream getDos()
	{
		return dos;
	}

	public static CatchmindFrame getFrame()
	{
		return frame;
	}


}
