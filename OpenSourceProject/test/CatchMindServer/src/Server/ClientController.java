package Server;

import java.io.IOException;
import java.util.Vector;

public class ClientController 
{
	protected Vector<Client> clientlist;
	
	public ClientController() throws IOException
	{
		clientlist = new Vector<Client>();
	}
	
	public Vector<Client> getClientlist()
	{
		return clientlist;
	}

	public void sendTo(int i, String msg)		// 해당 Client 에게 메시지를 전송
	{
		try {
			clientlist.get(i).getDos().writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToAll(String msg)		// 접속한 모든 Client 에게 메시지를 전송
	{
		for(int i=0;i<clientlist.size();i++)
		{
			sendTo(i,msg);
		}
	}
	
	public void sendToRoom(int roomnum, String msg)		// 같은 방에 있는 사람과 채팅 (채널은 방번호가 -1)
	{
		for(int i=0;i<clientlist.size();i++)
		{
			if(clientlist.get(i).getRoomnum() == roomnum)
			{
				sendTo(i,msg);
			}
		}
	}
	
	public void sendToEnemy(int roomnum, String msg)		// 같은 방에 있는 사람과 채팅 (채널은 방번호가 -1)
	{
		for(int i=0;i<clientlist.size();i++)
		{
			if(clientlist.get(i).getRoomnum() == roomnum)
			{
				sendTo(i,msg);
			}
		}
	}

	public void sendToOne(String id, String msg)		// 해당 아이디를 가진 사람에게 메시지를 보냄
	{
		for(int i=0;i<clientlist.size();i++)
		{
			if(clientlist.get(i).getGameId().equals(id))
			{
				sendTo(i,msg);
				System.out.println("i"+i+"msg"+msg);
			}
		}
	}
	
	public void updateIDlist()				//접속자 명단을 모든 client에게 보냄
	{
		String msg = "[Clientlist] 접속자 수 : " + clientlist.size()+"\n\n";

		for(int i=0;i<clientlist.size();i++)
		{
			msg+=clientlist.get(i).id+"\n";		//모든 접속자 명단
		}
		sendToAll(msg);
	}
	
	
}
