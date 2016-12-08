package Server;




public class RandomWord {

	String word[]={"자동차","자전거","아파트","강의실","부처님",
				   "컴퓨터","마우스","회전시계","화이트보드","교수님",
				   "오토바이","짜장면","음료수","영화관","망원경"
				   };
//	String randomWord;
	int b;
	/**
	 * @param args
	 */
	
	public RandomWord() {
		// TODO Auto-generated constructor stub
		int count=0;
		while(count<1){
		b = (int)(Math.random()*15);
		
//		System.out.println(word[b]);
//		randomWord= word[b];
		count++;
		
		}
		
	}
	
	
	public String getrandomword(){
		String msg=word[b];
		System.out.println(msg);
		return word[b];
		
		
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//		
//		RandomWord randomWord = new RandomWord();
//		String line=randomWord.getrandomword();
//		System.out.println(line);
//
//	
//		
//		
//		
//	}

}
