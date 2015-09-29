import java.util.Scanner;

public class StoreSim {

    static PQ agenda = new PQ();
    static CheckerEvent checkers;
    
    //Code based on CarSim

    public static void main(String args[]) {
    	Scanner s = new Scanner(System.in);
    	boolean baggers = false;
    	int speedLanes = 0;
    	int checkerNum = 8;
    	double arrivalTime = 30;
    	int simLength = 10800;
    	
    	System.out.println("Enter y for employee bagging or n for not:");
    	String bags = s.nextLine();
    	if(bags.equalsIgnoreCase("y"))
    		baggers = true;
    		
    	
    	System.out.println("Enter number of lanes:");
    	int lanes = 0;
    	try{
    		lanes = s.nextInt();
    	}
    	catch(Exception exp){
    		System.out.println("False input. Default to 12.");
    		lanes = 12;
    	}
    	if(lanes>0 && lanes<13)
    		checkerNum = lanes;
    	
    	
    	System.out.println("Enter number of express lanes:");
    	int express = 0;
    	try{
    		express = s.nextInt();
    	}
    	catch(Exception exp){
    		System.out.println("False input. Default to 0.");
    	}
    	if(express>-1 && express<lanes)
    		speedLanes = express;
    	
    	
    	System.out.println("Enter average inter-arrival rate:");
    	double arrival = 0;
    	try{
    		arrival = s.nextDouble();
    	}
    	catch(Exception exp){
    		System.out.println("False input. Default to 30.");
    		arrival = 30;
    	}
    	arrivalTime = arrival;
    	
    	Stats.updateStats(checkerNum);
    	
    	checkers = new CheckerEvent(baggers, speedLanes, checkerNum);

        int distArray[] = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100,
        				   10, 20, 30, 40, 50, 60, 70, 80, 
        				   10, 20, 30, 40, 50, 60, 70,
        				       20, 30, 40, 50,
        				           30};
        
        agenda.add(new ShopperMaker(arrivalTime, distArray), 0);
        
        //Used for begining idleing stats
        for(int x = 0; x<checkerNum; x++){
        	Stats.updateBusyTimeStats(StoreSim.agenda.getCurrentTime(),x);
        }
        
        while (agenda.getCurrentTime() <= simLength){
            agenda.remove().run();
        }
        
        //Ends idling and busy lanes for correct timing
        for(int x = 0; x<checkerNum; x++){
        	if(CheckerEvent.checkers[x].isBusy())
        		Stats.updateBusyTimeStats(StoreSim.agenda.getCurrentTime(),x);
        	else
        		Stats.updateIdleTimeStats(StoreSim.agenda.getCurrentTime(),x);
        }
        
        Stats.displayStats();
        System.out.println(" Average inter-arrival time: "+arrivalTime);

    }
}
