
public class Shopper {
	
	//Based on Car class
	
	private double arrivalTime;
	private double itemCount;
	private int index;
	
	public Shopper(double t, double items){
		Stats.addCustomer();
		arrivalTime = t;
		itemCount = items;
		index = CheckerEvent.enter(this);
		//System.out.println("New shopper with "+items+" items at "+t+" time");
		
		//Wakes up checker if not currently running
		if (!CheckerEvent.isBusy(index)){
	          new CheckerEvent(index).run();
		}
	}
	
	//Returns time shopper entered the line
	public double getArrivalTime() {
        return arrivalTime;
    }
  
	//Returns the number of items they have
    public double getItemCount() {
        return itemCount;
    }

}
