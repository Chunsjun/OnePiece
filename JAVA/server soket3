package soket.regionServer;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyRegionServer {
	
	public static void main(String[] args)throws Exception {
		
		ServerSocket server = new ServerSocket(7777);
		System.out.println(server);
		
		Socket client = server.accept();
		System.out.println(client);
		
		InputStream in = client.getInputStream();
		
		byte[] fileName = new byte[50];
		int count = in.read(fileName);
		String fileNameStr = new String(fileName).trim();
		
		OutputStream out = new FileOutputStream("C:\\zz\\"+fileNameStr);
		
		while(true){
			int data = in.read();
			out.write(data);
			
			if(data == -1){
				break;
			}
		}
		System.out.println("finished.............................");
		
		server.close();

	}

}
