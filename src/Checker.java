
//Based on Car class
public class Checker {
	
	private Q1 waitline = new Q1();
	private boolean busy;
	boolean express; 
	
	public Checker(boolean exp){
		express = exp;
		busy = false;
	}
	
	//Shopper enters this checker's lane
	public void enter(Shopper s) {
		waitline.add(s);
	}
	
	//Returns length of the line currently
	public int lineLength(){
		return waitline.length();
	}
	
	//Determines if someone is being served or not
	public boolean isBusy(){
		return busy;
	}

	//Changes the busy status
	public void setStatus(boolean business){
		busy = business;
	}

	//Removes the first customer in line
	public Object remove(){
		return waitline.remove();
	}
}
