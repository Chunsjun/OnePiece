package server;

public class Namelist {
	private String name;
	
	public Namelist(){
		this.name = "";
	}
	
	public Namelist(String name){
		this.name = name;
	}
	
	public String getname(){
		return this.name;
	}
	
	public void setname(String name){
		this.name = name;
	}
}
