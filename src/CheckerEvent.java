
//Based on Car class
public class CheckerEvent implements Event {
	
	static Checker[] checkers;
	private static boolean storeBag;
	private static int speedLanes;
	private int servTime;
	private static int checkerNum;
	
	//Used for putting onto StoreSim agenda
	public CheckerEvent(int index){
		servTime = index;
	}
	
	public CheckerEvent(boolean baggers, int speeder, int checkerNumber){
		checkers = new Checker[checkerNumber];
		checkerNum = checkerNumber;
		speedLanes = speeder;
		storeBag = baggers;
		int x = 0;
		while (x<checkerNum){
			Checker a = new Checker(false);
			checkers[x] = a;
			x++;
		}
	}
	
	//Used when ShopperMaker creates a new shopper
	public static int enter(Shopper s){
		int y = 0;
		int index = -1;
		if(s.getItemCount() != 10){
			y = speedLanes;
		}
		int min = checkers[y].lineLength() + 1;
		boolean test = true;//Used for correct ordering of shoppers in lines
		for(int x=y; x<checkerNum; x++){
			if (!checkers[x].isBusy() && test){
				index = x;
				test = false;
			}
			else if((checkers[x].lineLength() < min) && test){
				index = x;
				min = checkers[x].lineLength();
			}
		}
		checkers[index].enter(s);
		//System.out.println("Shopper added at "+index+" queue");
		Stats.updateTimeStats(StoreSim.agenda.getCurrentTime(), index, checkers[index].lineLength());
		return index;
	}
	
	//Determines if the lane at the specified index is busy
	public static boolean isBusy(int index){
		if(checkers[index].isBusy() == true){
			return true;
		}
		return false;
	}
	
	//Returns number of checkers
	public static int checkNum(){
		return checkerNum;
	}
	
	//Checks number of express lanes
	public static int checkExpress(){
		return speedLanes;
	}
	
	//What is run from the StoreSim agenda
	public void run(){
		int x = servTime;
		
		//Update if clerk is actually serving someone
		if(checkers[x].isBusy())
			Stats.updateBusyTimeStats(StoreSim.agenda.getCurrentTime(),x);
		
		//If no customer is being served
		else
			Stats.updateIdleTimeStats(StoreSim.agenda.getCurrentTime(),x);
		
		if(checkers[x].lineLength() == 0){//Runs when checker is done
			
			checkers[x].setStatus(false);
		}
		else{
			checkers[x].setStatus(true);
			Shopper shop = (Shopper) checkers[x].remove();
			double itemTime;
			boolean testForSpeed = false;
			
			if (((speedLanes == 1)||(speedLanes == 2)) && (x == 0)){
				testForSpeed = true;
			}
			
			else if((speedLanes == 2) && (x == 1)){
				testForSpeed = true;
			}
			
			if(!storeBag || testForSpeed){
				itemTime = 9 * shop.getItemCount(); 
			}
			
			else{
				itemTime = 5 * shop.getItemCount(); 
			}
			
			Stats.customerServed();
			StoreSim.agenda.add(new CheckerEvent(x), itemTime);
			Stats.updateWaitTime(StoreSim.agenda.getCurrentTime(), x, shop.getArrivalTime());
		}
	}
}