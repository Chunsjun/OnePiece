package Server;

import java.util.StringTokenizer;
import java.util.Vector;

public class RoomController 
{
	protected Vector<Room> roomlist;
	private StringTokenizer tokenizer;
	private String[] tokens = new String[5];
	private int roomnum;

	public RoomController()
	{
		roomlist = new Vector<Room>();
		roomnum = 0;
	}
	
	public int indexRoom(int roomnum)			// 방번호를 통해 list의 인텍스를 반환
	{
		for(int i=0;i<roomlist.size();i++)
		{
			if(roomlist.get(i).getNumber().equals(roomnum+"")) return i;
		}
		return -1;		// 방번호가 없으면 -1 오류값을 반환
	}
	
	public int whereUser(String id)  //해당아이디가 어느방에있는지찾는다.
	{
		for(int i=0;i<roomlist.size();i++)
		{
			for(int j=0;j<roomlist.get(i).playerlist.size();j++)
			{
			if(roomlist.get(i).playerlist.get(j).equals(id)) return i;
			}
		}
		return -1;
	}

	public String enterRoom(Client player)
	{
		int i = indexRoom(player.getRoomnum());			// 해당 방번호로 roomlist의 index를 받아옴 
		roomlist.get(i).playerlist.add(player);			// 방에 입장한 client를 playerlist에 추가
		roomlist.get(i).updateCondition();				// 인원 형황을 변경

		return roomlist.get(i).getCondition();			// 방의 입장 현황을 반환
	}
	
	public String exitRoom(Client player)
	{
		int i = indexRoom(player.getRoomnum());			// 해당 방번호로 roomlist의 index를 받아옴 
		roomlist.get(i).playerlist.remove(player);			// client를 playerlist에서 제거
		roomlist.get(i).updateCondition();				// 인원 형황을 변경

		for(int j=0;j<roomlist.size();j++)
		{
			System.out.println("*************************************************");
			System.out.println("방"+j+"방상태:"+roomlist.get(j).getCondition());
		}
		if(roomlist.get(i).getCondition().equals("null"))		//만약 입장현황이 null이면 리스트에서 방을 지움.
		{
			roomlist.remove(i);
			
			return "null";
		}
		
	
		return roomlist.get(i).getCondition();		// 방의 입장 현황을 반환
	}
	
	public Vector<Room> getRoomlist()
	{
		return roomlist;
	}


	public String makeRoom(Client player, String msg)
	{
		String result;
		
		tokenizer = new StringTokenizer(msg.substring(10),"\t"); 		// [Make Room] 를 제외한 메시지를 \t를 기준으로 나눔
		int i=0;

		while(tokenizer.hasMoreTokens())
		{
			tokens[i++] = tokenizer.nextToken();
		}
		roomnum++;
		roomlist.add(new Room(player, roomnum+"",tokens[0],tokens[1]));
		player.setRoomnum(roomnum);		//Client의 방번호를 변경한다.
		

		result =""+ roomlist.get(roomlist.size()-1).getString();
		System.out.println("makeroom result"+result);
		return result;
	}
	

	public String nextTurn(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).nextTurn();	// 해당 방의 다음턴의 아이디를 받아옴
	}
	
	public String thisTurn(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).thisTurn();
	}
	
	public String getPlayerIDlist(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).getPlayerIDlist();	// 해당 방의 정보와 모든 playerlist의 상태를 반환
	}
	public String getRoomNum(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).getRoomNum();

	}
	
	public String getRoomTitle(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).getRoomTitle();
	}
	
	public String getRoomPeopleCount(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).getRoomPeopleCount();
	}
	
	public String getRoomIdList(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).getRoomIdList();
	}
	
	public String getFirstPlayerId(int roomnum)
	{
		int i = indexRoom(roomnum);
//		int j = roomlist.get(i).playerlist.size()-1;
		return roomlist.get(i).playerlist.get(0).getGameId();
	}
	
	public int RoomSize()
	{
		return roomlist.size()-1;
	}
	public String totalRoom()
	{
		// 만들어진 모든 방의 정보를 반환한다.
		String result = "";
		for(int i=0;i<roomlist.size();i++)
		{
			result += roomlist.get(i).getString()+"\n";
		}
		return result;
	}
	
	public String checkStart(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).checkStart();	// 해당방의 시작 가능여부를 판단
	}
	
	public boolean checkTurnOver(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).CheckAllTurnOver();
	}

	public void TurnReset(int roomnum) {
		// TODO Auto-generated method stub
		int i = indexRoom(roomnum);
		roomlist.get(i).SetOneTurnReset();
		roomlist.get(i).SetAllTurnReset();
	}

	public void GameOn(int roomnum2) {
		// TODO Auto-generated method stub
		int i = indexRoom(roomnum);
		roomlist.get(i).gamecontroller.GameOn();
	}

	public boolean ReturnState(int roomnum2) {
		// TODO Auto-generated method stub
		int i = indexRoom(roomnum);
		return roomlist.get(i).gamecontroller.ReturnState();
	}

	public void FinishTurn(int roomnum2) {
		// TODO Auto-generated method stub
		int i = indexRoom(roomnum);
		roomlist.get(i).gamecontroller.FinishTurn();
	}

	public void SetOneTurnReset(int roomnum2) {
		// TODO Auto-generated method stub
		
		int i = indexRoom(roomnum);
		roomlist.get(i).SetOneTurnReset();
	}

	public boolean CheckAllTurnOver(int roomnum2) {
		// TODO Auto-generated method stub
		int i = indexRoom(roomnum);
		return roomlist.get(i).CheckAllTurnOver();
	}

	public boolean CheckOneTurnOver(int roomnum2) {
		// TODO Auto-generated method stub
		int i = indexRoom(roomnum);
		return roomlist.get(i).CheckOneTurnOver();
	}

	public void Turnover(int roomnum2) {
		// TODO Auto-generated method stub
		int i = indexRoom(roomnum);
		roomlist.get(i).gamecontroller.Turnover();
	}

	public int getTurnnum(int roomnum2) {
		// TODO Auto-generated method stub
		int i = indexRoom(roomnum);
		return roomlist.get(i).gamecontroller.getTurnnum();
	}

	public void SetAllTurnReset(int roomnum2) {
		// TODO Auto-generated method stub
		int i = indexRoom(roomnum);
		roomlist.get(i).SetAllTurnReset();
	}
	
	
	public String RankManageMent(int roomnum)
	{
		int i = indexRoom(roomnum);
		return roomlist.get(i).RankManageMent();
	}
	public void setUserPosition(int roomnum, int Number){
		int i = indexRoom(roomnum);
		roomlist.get(i).setUserPosition(Number);
	}
	//게임 상 유저의 자리위치를 알아내는 함수
	public int getUserPosition(int roomnum){
		int i = indexRoom(roomnum);
		return roomlist.get(i).getUserPosition();
	}
	public String getUserInfo(int roomnum){
		int i = indexRoom(roomnum);
		return roomlist.get(i).getUserInfo();
	}
	public void setUserInfo(int roomnum, String msg, int Num){
		int i = indexRoom(roomnum);
		roomlist.get(i).setUserInfo(msg, Num);
	}
	public void outUserPosition(int roomnum, int Number){
		int i = indexRoom(roomnum);
		roomlist.get(i).outUserPosition(Number);
	}
	public void outUserInfo(int roomnum, int Number){
		int i = indexRoom(roomnum);
		roomlist.get(i).outUserInfo(Number);
	}
	public int getPeopleSize(int roomnum){
		int i = indexRoom(roomnum);
		return roomlist.get(i).getPeopleSize();
	}
	
}
