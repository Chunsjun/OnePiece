package Server;

import java.util.Timer;
import java.util.TimerTask;

public class GameController{

	
	
	static boolean state = false; //게임이 진행중인지 상태를 알려주는 변수
	static String roomnumber;
	static int endturntime=6; //턴수제한
	Timer timer;
	private int turnnum=1;
	

	public GameController(String roomnumber) {
		// TODO Auto-generated constructor stub
		
		state=false;
		turnnum=1;
		
		
	}
	public int getTurnnum() {
		return turnnum;
	}

	public void setTurnnum(int turnnum) {
		this.turnnum = turnnum;
	}
	
	
	public static String nextTurn()
	{
		return "next";
		
	}
	public void GameOn() //게임진행상태를 진행으로 바꾸는함수
	{
		state=true;
	}
	public void GameOff()
	{
		state=false;
	}
	public void Turnover() //아무도 정답을못마췃을때 다음 턴으로
	{
		System.out.println("서버턴"+turnnum+"시작"+"turnover");
		turnnum++;
		if(turnnum>endturntime)
		{
			state=false;
			turnnum=-1;
		}
		System.out.println("서버턴"+turnnum+"종료"+"turnover");
	}
	
	public void FinishTurn() //누군가 정답을 맞췃을때 다음턴으로
	{
		
		System.out.println("서버턴"+turnnum+"시작"+"finishturn");
		turnnum++;
		if(turnnum>endturntime)
		{
			state=false;
			turnnum=1;
		}
		System.out.println("서버턴"+turnnum+"시작"+"finishturn");
		
	}
	
	public void AllUserEnd()//게임을 시작하기 전의 방상태로 초기화
	{
		turnnum=1;
		state=false;
	}
	
	public boolean ReturnState()
	{
		return state;
	}

	
	}







