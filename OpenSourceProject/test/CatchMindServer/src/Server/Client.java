package Server;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.print.attribute.standard.Severity;
import javax.swing.RepaintManager;

import com.mysql.jdbc.UpdatableResultSet;

import DBController.Login;

class Client extends Thread 
{
	protected Server svr;
	protected Socket socket;
	protected DataInputStream dis;
	protected DataOutputStream dos;
	
	protected String id;				// client id는 "user1,user2,...(접속순서)" 이다.
//	private static String thisturnid=null;
	protected String pass;
	protected String name;
	protected String reginum;
	protected String temp_id;
	protected String temp_pass;
	private String ReValue;
	private int roomnum;			// 방번호를 나타내는 변수 초기값 -1(대기실)
	private static RandomWord randomWord;
	protected int score;
	protected int seq;
	
	String state;					// 준비중인지 현재 상태를 나타냄
	boolean turnovercheck;          //턴이진행중인지 상태를 나타냄
	boolean oneturnovercheck=false;
	
	protected ItemController itemController;
	protected Login loginController;
	protected StringTokenizer tokenizer1;
	protected String[] user = new String[10];
	
	
	protected String level;
	protected String exp;
	protected String coin;
	protected String avatar;
	
	
	public Client(Server svr, Socket s) throws IOException
	{
		this.svr = svr;
		this.socket = s;
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());

		itemController = new ItemController();
		
		
		this.id = this.socket.getInetAddress()+"_"+svr.getCount();	//client id는 "ip주소_접속 순서" 이다.
		this.id = "user"+svr.getCount();
		roomnum = -1;
		score=0;

		state = "";		// 게임 시작을 준비하지 않은 상태
		oneturnovercheck=false;
		
		
		
		try {
			dos.writeUTF("[ShowID]"+ id);	//접속한 client에게 ID를 전송
			
			
			dos.writeUTF("[Information] "+ id + " 님 접속을 환영합니다.");	//접속한 client에게 ID와 접송 정보를 보냄
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}

//	public static DataInputStream getDis()
//	{
//		return dis;
//	}
//
	public DataOutputStream getDos()
	{
		return dos;
	}

	public void setRoomnum(int roomnum)
	{
		this.roomnum = roomnum;
	}

	public int getRoomnum()
	{
		return roomnum;
	}

//	public String getID()
//	{
//		return id;
//	}


	public boolean CheckPlay()
	{
		return oneturnovercheck;
	}
	public void sendToMe(String msg)		// this 클래스와 통신하고 있는 client에게 보냄
	{
		try {
			this.dos.writeUTF(msg);
			System.out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String msg = null;
		String msg2 =null;
		try {   
			while(true)
			{
				msg = dis.readUTF();   
				System.out.println(id+" ("+ this.socket.getInetAddress() + ") " + msg);

				if(msg.startsWith("[Chat]")) 	//[Chat] 으로 시작하는 메시지면 같은 방에있는 사람과 채팅 (대기실은 방번호가 -1)
				{
					svr.clientcontroller.sendToRoom(roomnum, "[Chat][ " + id + " ] : " + msg.substring(6));
						//[Chat] 을 제외한 id + 메시지를 채팅창에 보냄
//					if(thisturnid!=id)
//					{
					if(roomnum!=-1)
					{
					if(svr.roomcontroller.ReturnState(roomnum))
					{
//						System.out.println(gameId);
//						System.out.println(roomnum);
//						System.out.println((randomWord.getrandomword()));
					if(msg.substring(6).toString().equals((randomWord.getrandomword())) )  //client가 정답을 맞추엇으면수행된다.
					{
						
						System.out.println("정답");
						sendToMe("[GameGetScore]");
						System.out.println("[GameGetScore]");
						svr.clientcontroller.sendToRoom(roomnum, "[GameChat]"+id+"님이"+svr.roomcontroller.getTurnnum(roomnum)+"번째턴 정답을 맞추셨습니다.");
						svr.roomcontroller.FinishTurn(roomnum);
						msg=svr.roomcontroller.nextTurn(roomnum);
						System.out.println(svr.roomcontroller.ReturnState(roomnum)+"if전");
						if(svr.roomcontroller.ReturnState(roomnum))
						{
						System.out.println("다음턴은"+msg);
						svr.clientcontroller.sendToRoom(roomnum,"[GameNextTurn]"+msg);
						svr.roomcontroller.SetOneTurnReset(roomnum);
						randomWord = new RandomWord();
						svr.clientcontroller.sendToOne(msg,"[GameRandomWord]"+randomWord.getrandomword());
						}
						if(svr.roomcontroller.ReturnState(roomnum)==false) //해당방 턴이 끝낫으면 클라이언트를 꺼주기위한 if문
						{
							System.out.println("게임종료");
							svr.clientcontroller.sendToRoom(roomnum,"[GameNextTurn]"+"클라이언트좀꺼줄래?");//클라이언트 턴 종료
						}
						
					}
					}
//					
				}
						
				}
				else if(msg.startsWith("[ServerChat]"))
				{
					svr.svrchat.chatarea.append("[서버공지]:"+msg.substring(12)+"\n");
					
					
				}
				else if(msg.startsWith("[GameChat]")) 	//[Chat] 으로 시작하는 메시지면 같은 방에있는 사람과 채팅 (대기실은 방번호가 -1)
				{
					System.out.println(roomnum);
					svr.clientcontroller.sendToRoom(roomnum, "[GameChat][ " + id + " ] : " + msg.substring(10));
					if(svr.roomcontroller.ReturnState(roomnum))
					{
//						System.out.println(gameId);
//						System.out.println(roomnum);
//						System.out.println((randomWord.getrandomword()));
					if(msg.substring(11).toString().equals((randomWord.getrandomword())))
					{
						
						System.out.println("정답");
						sendToMe("[GameGetScore]");
						System.out.println("[GameGetScore]");
						svr.clientcontroller.sendToRoom(roomnum, "[GameChat]"+id+"님이"+svr.roomcontroller.getTurnnum(roomnum)+"번째턴 정답을 맞추셨습니다.");
						svr.roomcontroller.FinishTurn(roomnum);
						msg=svr.roomcontroller.nextTurn(roomnum);
						System.out.println("다음턴은"+msg);
						svr.clientcontroller.sendToRoom(roomnum,"[GameNextTurn]"+msg);
						randomWord = new RandomWord();
						svr.clientcontroller.sendToOne(msg,"[GameRandomWord]"+randomWord.getrandomword());
						
					}
					}
						//[Chat] 을 제외한 id + 메시지를 채팅창에 보냄
				}
				
				else if(msg.startsWith("[Login]"))
				{
					tokenizer1 = new StringTokenizer(msg.substring(7),"\t");
					int i=0;
					while(tokenizer1.hasMoreTokens()){
						user[i] = tokenizer1.nextToken();
						i++;
					}
					temp_id = user[0];
					temp_pass = user[1];
					ReValue = Login.Login(temp_id,temp_pass); 
					
					if(ReValue.equals("true")){
						Boolean flag = true;
						int j;
						for(j=0;j<svr.clientcontroller.clientlist.size();j++)
						{
							System.out.println(svr.clientcontroller.clientlist.get(j).getGameId());
							if(temp_id.equals(svr.clientcontroller.clientlist.get(j).getGameId())){
								// 중복된 아이디가 있음 
								flag = false;
								break;
							}
						}
						
						if(flag == true){
							id = temp_id;
							pass = temp_pass;
							msg="[Login]" + id;
							System.out.println("id"+id);
							sendToMe(msg);
							dos.writeUTF("[Roomlist]"+ svr.roomcontroller.totalRoom());	//roomlist에 모든 Room객체 의 정보를 받아서 접속한 client에 보냄
							svr.clientcontroller.updateIDlist();
							
							String imfo = Login.LoadInfo(id);
							tokenizer1 = new StringTokenizer(imfo,"\t");
							int k=0;
							String[] saveimfo = new String[10];
							while(tokenizer1.hasMoreTokens()){
								saveimfo[k] = tokenizer1.nextToken();
								System.out.println(saveimfo);
								k++;
							}
							
							level=saveimfo[0];
							
							exp=saveimfo[1];
							
							coin=saveimfo[2];
							avatar=saveimfo[3];
							while(level=="false"||exp=="false"||exp=="false"||coin=="false")
							{
								imfo = Login.LoadInfo(id);
								tokenizer1 = new StringTokenizer(imfo,"\t");
							     k=0;
								saveimfo = new String[10];
								while(tokenizer1.hasMoreTokens()){
									saveimfo[k] = tokenizer1.nextToken();
									System.out.println(saveimfo);
									k++;
								}
								level=saveimfo[0];
								
								exp=saveimfo[1];
								
								coin=saveimfo[2];
								avatar=saveimfo[3];
							}
							sendToMe("[ShowLv]"+level);
							sendToMe("[ShowExp]"+exp);
							sendToMe("[ShowCoin]"+coin);
							
//							sendToMe("[ShowAvatar]"+avatar);
							
							System.out.println(level+exp+coin+avatar);
							System.out.println("********정보를 서버에서 보낼때*******************");
							System.out.println(id + " " +level+" "+exp+" "+coin+" "+avatar);
							
							System.out.println("정보"+imfo);
							System.out.println("************************************");
							System.out.println("정보"+imfo);
						}else{
							msg="[LoginFail]Overlap";
							sendToMe(msg);
						}
					}else if(ReValue.equals("false")){
						msg="[LoginFail]Fail";
						sendToMe(msg);
					}
				}
				else if(msg.startsWith("[ChangeCoin]"))
				{
					level=msg.substring(12);
				}
				else if(msg.startsWith("[ChangeExp]"))
				{
					exp=msg.substring(11);
					coin=msg.substring(12);
				}
				else if(msg.startsWith("[ChangeLv]"))
				{
					
					tokenizer1 = new StringTokenizer(msg.substring(10),"\t");
					int k=0;
					String[] saveimfo = new String[10];
					while(tokenizer1.hasMoreTokens()){
						saveimfo[k] = tokenizer1.nextToken();
						System.out.println(saveimfo);
						k++;
					}
					level=saveimfo[0];
					exp=saveimfo[1];
					coin=saveimfo[2];
					if(Login.UpdateInfo(id, level, exp, coin)=="true")System.out.println(id+":업데이트성공");

					sendToMe("[ShowLv]"+level);
					sendToMe("[ShowExp]"+exp);
					sendToMe("[ShowCoin]"+coin);
//					sendToMe("[ShowAvatar]"+avatar);
				}
				
				
				
				/*** 13.05.17 Modified ***/
				else if(msg.startsWith("[LoginIDCheck]")){
					temp_id = msg.substring(15); 
					ReValue = Login.IDCheck(temp_id);
					
					if(ReValue.equals("false")){
						msg = "[LoginIDCheck]false";
						sendToMe(msg);
					}else{
						msg = "[LoginIDCheck]true";
						sendToMe(msg);
					}
				}
				else if(msg.startsWith("[LoginFindID]")){
					tokenizer1 = new StringTokenizer(msg.substring(13),"\t");
					int i=0;
					while(tokenizer1.hasMoreTokens()){
						user[i] = tokenizer1.nextToken();
						i++;
					}
					
					System.out.println(user[0] + user[1]);
					name = user[0];
					reginum = user[1];
					ReValue = Login.FindID(name,reginum);
					
					if(!ReValue.equals(null)){
						msg = "[LoginFindID]" + ReValue;
						sendToMe(msg);
					}else{
						msg = "[LoginFindID]";
						sendToMe(msg);
					}
				}
				else if(msg.startsWith("[LoginFindPass]")){
					tokenizer1 = new StringTokenizer(msg.substring(15),"\t");
					int i=0;
					while(tokenizer1.hasMoreTokens()){
						user[i] = tokenizer1.nextToken();
						i++;
					}
					name = user[0];
					id = user[1];
					reginum = user[2];
					ReValue = Login.FindPass(name,id,reginum);
					if(!ReValue.equals(null)){
						msg = "[LoginFindPass]" + ReValue;
						sendToMe(msg);
					}else{
						msg = "[LoginFindPass]";
						sendToMe(msg);
					}
				}
				else if(msg.startsWith("[LoginSignUp]")){
					tokenizer1 = new StringTokenizer(msg.substring(13),"\t");
					int i=0;
					while(tokenizer1.hasMoreTokens()){
						user[i] = tokenizer1.nextToken();
						i++;
					}
					name = user[0];
					temp_id = user[1];
					temp_pass = user[2];
					reginum = user[3];
					// 테스트
					System.out.println(name + temp_id + temp_pass + reginum);
					
					ReValue = Login.SignUp(name,temp_id,temp_pass,reginum);
					
					if(ReValue.equals("true")){
						msg = "[LoginSignUp]";
						sendToMe(msg);
					}
					
					
				}
				/*** 13.05.17 Modified ***/
				
				else if(msg.startsWith("[MakeRoom]"))
				{
					msg = "[MakeRoom]" + svr.getRoomController().makeRoom(this,msg); 	//RoomCotroller를 통해 Room객체를 만듬
					System.out.println("방만들엇어요"+msg);
					sendToMe("[MadeRoom]");
					svr.clientcontroller.sendToAll(msg);	//만든 Room객체의 정보를 모든 client에게 보냄
				    svr.clientcontroller.sendToAll("[RoomSize]"+svr.getRoomController().RoomSize());
					msg = svr.roomcontroller.getPlayerIDlist(roomnum);		// 방의 IDlist를 업데이트
					
					// 유저의 정보를 저장함
					msg = id + "\t" + level + "\t" + 0 + "\n";
					svr.roomcontroller.setUserInfo(roomnum, msg, 0);
					//유저의 방 입장 순서위치를 저장
					svr.getRoomController().setUserPosition(roomnum,0);	
					// 게임 내에서 사용자의 정보를 보여주도록 메시지 보냄 
					msg = "[PositioningUser]" + id + "\t" + level + "\t" + 0;
					sendToMe(msg);
					
					System.out.println(id+"방번호"+roomnum);
				}
				else if(msg.startsWith("[EnterRoom]"))
				{
					this.roomnum = Integer.parseInt(msg.substring(11));		// 방번호를 입장한 방번호로 변경
					System.out.println("클라이언트가 들어갈 방번호는"+msg.substring(11));
					msg += "\t"+svr.getRoomController().enterRoom(this);	// client를 입장한 방의 playerlist에 추가
					System.out.println("클라이언트에게 보내는 메세지"+msg);
					svr.clientcontroller.sendToAll(msg);	// 방에 입장하여 변경된 방의 정보를 모든 client에게 보냄
					msg="[SetGamePanel]";
					sendToMe(msg);
					
					seq = svr.getRoomController().getUserPosition(roomnum);	// 방 내의 위치를 얻어옴
					svr.getRoomController().setUserPosition(roomnum,seq);	// 방 내의 나의 위치를 고정시킴	
					//현재 유저의 정보를 저장
					msg = id + "\t" + level + "\t" + seq + "\n";
					svr.roomcontroller.setUserInfo(roomnum, msg, seq);
					// 자신의 정보를 다른 유저들에게 보냄
					msg = "[PositioningUser]" + id + "\t" + level + "\t" + seq;
					svr.clientcontroller.sendToRoom(roomnum, msg);
					// 방에 있는 유저(자신을 포함)들을 보여줌
					msg = "[SpreadOtherUsers]" + svr.roomcontroller.getPeopleSize(roomnum) + svr.roomcontroller.getUserInfo(roomnum);
					sendToMe(msg);
					
					
					System.out.println(id+"방번호"+roomnum);
				}
				else if(msg.startsWith("[ExitRoom]"))
				{
					String temp;
					// 유저의 위치를 나간 것으로 표시
					svr.roomcontroller.outUserPosition(roomnum, seq);
					svr.roomcontroller.outUserInfo(roomnum, seq);
					// 방에 남은 유저들에게 나간 것을 알려줌
					msg2 = "[OutUser]" + seq;
					svr.clientcontroller.sendToRoom(roomnum, msg2);
					System.out.println("방번호 : " + roomnum + " 메시지" + msg2);

					msg +=  roomnum + "\t";		// client를 현재 방의 playerlist에서 제거
					temp = svr.getRoomController().exitRoom(this);	// client를 현재 방의 playerlist에서 제거
					msg +=  temp;
					System.out.println(id+"클라이언트가 exit버튼누르면 temp:"+msg+"roomnum"+roomnum);
					svr.clientcontroller.sendToAll(msg);	// 방에 퇴장하여 변경된 방의 정보를 모든 client에게 보냄

					//유저의 위치를 나간 것으로 표시
					svr.roomcontroller.outUserPosition(roomnum, seq);
					svr.roomcontroller.outUserInfo(roomnum, seq);
					//방에 남은 유저들에게 나간 것을 알려줌
					msg = "[OutUser]" + seq;
					svr.clientcontroller.sendToRoom(roomnum, msg);
					
					if(!(temp.equals("null")))			// 내가 마지막으로 나오는 것이 아니면 (방에 사람이 남아있다면)
					{
//						msg = svr.roomcontroller.getPlayerIDlist(roomnum);		// 내가 퇴장하여 변경된 IDlist를 업데이트
//						svr.clientcontroller.sendToRoom(roomnum,"[PlayerIDlist] "+ msg);	
					}
					
					roomnum = -1;
				}
				else if(msg.startsWith("[RequestInfor]"))
				{
					int tempnum;
					tempnum=Integer.parseInt(msg.substring(14));
					msg ="[RoomTitle]"+ svr.roomcontroller.getRoomTitle(tempnum);
					sendToMe(msg);
					msg ="[RoomPeopleCount]"+ svr.roomcontroller.getRoomPeopleCount(tempnum);
					sendToMe(msg);
					msg ="[RoomIdList]"+ svr.roomcontroller.getRoomIdList(tempnum);
					sendToMe(msg);
					msg ="[RoomNum]" +svr.roomcontroller.getRoomNum(tempnum);
					sendToMe(msg);
					System.out.println(id+"방번호"+roomnum);
				}
				else if(msg.startsWith("[EndSetRoomInfo]"))
				{
					sendToMe(msg);
					 System.out.println(id+"방번호"+roomnum);
				}
				else if(msg.startsWith("[GameSetReady]"))
				{
					state="Ready";
					String check=svr.roomcontroller.checkStart(roomnum);
					System.out.println(check+"check");
					if(check=="allReady")
					{
						System.out.println(roomnum+"방번호야");
						check=svr.roomcontroller.getFirstPlayerId(roomnum);
						System.out.println(check);
						svr.clientcontroller.sendToOne(check, "[GameSetStart]");
						System.out.println(id+"방번호"+roomnum);
					}
					msg += seq;
					svr.clientcontroller.sendToRoom(roomnum, msg);
				         
				}
				else if(msg.startsWith("[GameSetCancel]"))
				{
					state="";
					String check;
					check=svr.roomcontroller.getFirstPlayerId(roomnum);
					svr.clientcontroller.sendToOne(check, "[GameRemoveStart]");
					 System.out.println(id+"방번호"+roomnum);
					 msg += seq;
					 svr.clientcontroller.sendToRoom(roomnum, msg);
				}
				else if(msg.startsWith("[GameStart]"))
				{
					svr.roomcontroller.TurnReset(roomnum);
					
					randomWord=new RandomWord();
					msg="[SetGameStart]";
					
					svr.clientcontroller.sendToRoom(roomnum, msg);
					
//					msg="[GameRandomWord]"+randomWord.getrandomword();
//					String tempId=svr.roomcontroller.getFirstPlayerId(roomnum);
//					svr.clientcontroller.sendToOne(tempId,msg);
					msg=svr.roomcontroller.nextTurn(roomnum);
//					thisturnid=msg;
					System.out.println("다음턴은"+msg);
					svr.clientcontroller.sendToRoom(roomnum,"[GameNextTurn]"+msg);
					randomWord = new RandomWord();
					svr.clientcontroller.sendToOne(msg,"[GameRandomWord]"+randomWord.getrandomword());
					svr.clientcontroller.sendToRoom(roomnum, "[GameRankState]"+svr.roomcontroller.RankManageMent(roomnum));
					System.out.println(id+"방번호"+roomnum);
//					svr.clientcontroller.sendToOne(tempId, "[GameTurn]");
					
					svr.roomcontroller.GameOn(roomnum);
					System.out.println(roomnum);
				}
				else if(msg.startsWith("[GameEndAllTurn]"))//클라이언트에게 [GameEndAllTurn]메세지를받앗을때 모든유저가 턴이 끝났는지확인하고 게임종료를 공지함
				{
					turnovercheck=true;
					state="";
					if(svr.roomcontroller.CheckAllTurnOver(roomnum))
					{
						
						svr.clientcontroller.sendToRoom(roomnum, "[Chat]"+"게임이 끝났습니다.");
						svr.clientcontroller.sendToRoom(roomnum,"[GameRankState]"+ svr.roomcontroller.RankManageMent(roomnum));
						svr.roomcontroller.SetAllTurnReset(roomnum);
						svr.clientcontroller.sendToRoom(roomnum, "[GameEndAllTurn]");
//						thisturnid=null;
						
					}
				}
				else if (msg.startsWith("[CorrectAnswer]")) {
					msg += seq;
					svr.clientcontroller.sendToRoom(roomnum, msg);
				}
				else if(msg.startsWith("[GameSendPoint]"))
				{
					score=Integer.parseInt(msg.substring(15));
					svr.clientcontroller.sendToRoom(roomnum,"[GameRankState]"+ svr.roomcontroller.RankManageMent(roomnum));
				}
				/* 2013.05.23 */
				else if(msg.startsWith("[Draw]"))
				{
					svr.clientcontroller.sendToRoom(roomnum, msg);
					
				}
				else if(msg.startsWith("[Move]"))
				{
					svr.clientcontroller.sendToRoom(roomnum, msg);
				}
				else if(msg.startsWith("[Eraser]"))
				{
					svr.clientcontroller.sendToRoom(roomnum, msg);
				}
				else if(msg.startsWith("[SetColor]"))
				{
					svr.clientcontroller.sendToRoom(roomnum, msg);
				}
				/* 2013.05.23 */
				else if(msg.startsWith("[GameOneTurnOver]"))
				{
					oneturnovercheck=true;
					if(svr.roomcontroller.CheckOneTurnOver(roomnum))
					{
						svr.clientcontroller.sendToRoom(roomnum, msg);
						svr.clientcontroller.sendToRoom(roomnum, "[GameChat]"+"아무도"+svr.roomcontroller.getTurnnum(roomnum)+"번째턴 정답을 못 맞추셨습니다.");
						
						if(svr.roomcontroller.CheckAllTurnOver(roomnum)==false)
						{
						svr.roomcontroller.Turnover(roomnum);
						if(svr.roomcontroller.getTurnnum(roomnum)!=-1)//턴수가 남았으면 문제를 유저에게 준다.
						{
						msg=svr.roomcontroller.nextTurn(roomnum);
//						thisturnid=msg;
						System.out.println("다음턴은"+msg+"입니다");
						svr.clientcontroller.sendToRoom(roomnum,"[GameChat]"+"다음턴은"+msg+"입니다");
						randomWord = new RandomWord();
						svr.clientcontroller.sendToOne(msg,"[GameRandomWord]"+randomWord.getrandomword());
						}
						}
						else
						{
							System.out.println("게임종료");
							svr.clientcontroller.sendToRoom(roomnum,"[GameNextTurn]"+"클라이언트좀꺼줄래?");//클라이언트 턴 종료
							
						}
					}
					
				}
			}
		} catch (IOException | SQLException ex) { 
			System.out.println(id+" ("+ this.socket.getInetAddress() + ") " + "접속 해지");
		}
		finally {
			svr.clientcontroller.clientlist.removeElement(this);	// 접속 해제한 this Client를 list에서 제거
			System.out.println("현재 접속자 수: "+svr.clientcontroller.getClientlist().size());
			svr.clientcontroller.updateIDlist();	// 접속한 모든 client에게 접속 해제 client ID를 제외한 IDlist결과를 업데이트

			try {
				dis.close();
				dos.close();
				socket.close();
			} catch (IOException e) { e.printStackTrace(); }
		}  

	}

	public String getGameId() {
		return id;
	}

	public int getScore(){
		return score;
	}
	public void setGameId(String gameId) {
		this.id = id;
	}

	public void ScoreReset() {
		// TODO Auto-generated method stub
		score=0;
	}

}