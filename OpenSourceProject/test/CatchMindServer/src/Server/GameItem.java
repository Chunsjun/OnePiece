package Server;

import java.util.Vector;

public class GameItem {
	private int numMoreExp;  //경험치 획득량을 2배로 늘려주는 아이템
	private int numMoreCoin2;  //코인획득량을 2배로 해주는 아이템
	private int numShowOneword; //한글자 보여주는 아이템
	private int numhowmanyword; //몇글자 인지 알려주는 아이템
	private int numMorepoints;  //포인트 획득량을 늘려주는 아이템
	
	
	
	
	public GameItem() {
		// TODO Auto-generated constructor stub
		this.setNumMoreExp(0);
		this.setNumMoreCoin2(0);
		this.setNumShowOneword(0);
		this.setNumhowmanyword(0);
		this.setNumMorepoints(0);
	}
	
	public GameItem(int numMoreExp , int numMoreCoin, int numMoreCoin2, int NumShowOneword,int Numhowmanyword,int NumMorepoints)
	{

		this.setNumMoreExp(numMoreExp);
		this.setNumMoreCoin2(numMoreCoin2);
		this.setNumShowOneword(NumShowOneword);
		this.setNumhowmanyword(Numhowmanyword);
		this.setNumMorepoints(NumMorepoints);
	}

	





	public int getNumMoreExp() {
		return numMoreExp;
	}

	public void setNumMoreExp(int numMoreExp) {
		this.numMoreExp = numMoreExp;
	}


	public int getNumMoreCoin2() {
		return numMoreCoin2;
	}

	public void setNumMoreCoin2(int numMoreCoin2) {
		this.numMoreCoin2 = numMoreCoin2;
	}
	
	public void buyMoreExp() {
		this.numMoreExp++;
	}
	
	public void useMoreExp() {
		this.numMoreExp--;
	}
	
	
	public void buyMoreCoin2() {
		this.numMoreCoin2++;
	}
	
	public void useMoreCoin2() {
		this.numMoreCoin2--;
	}

	public int getNumhowmanyword() {
		return numhowmanyword;
	}

	public void setNumhowmanyword(int numhowmanyword) {
		this.numhowmanyword = numhowmanyword;
	}

	public int getNumMorepoints() {
		return numMorepoints;
	}

	public void setNumMorepoints(int numMorepoints) {
		this.numMorepoints = numMorepoints;
	}

	public int getNumShowOneword() {
		return numShowOneword;
	}

	public void setNumShowOneword(int numShowOneword) {
		this.numShowOneword = numShowOneword;
	}
	

	
}