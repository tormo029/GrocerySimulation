
public class Stats {
	
	private static int checkerNumber;
	
	private static double[] lastUpdateTime;
	private static double[] lastQUpdateTime;
	private static double lastTimeUpdateTime;
	
	private static double[] busyTime;
	private static double[] idleTime;
	private static double totalTime;
	
	private static int customerTotal;
	private static int customerServed;
	
	private static int maxQLength;
	private static int[] oldQLength;
	private static int[] averageQLength;
	
	private static double maxWait;
    private static double averageWait;
	
    //Keeps track of every new suctomer in system
	public static void addCustomer(){
		customerTotal++;
	}
	
	//Every customer that gets served in the system
	public static void customerServed(){
		customerServed++;
	}
	
	//Keeps track of customer waiting times
	public static void updateWaitTime(double time, int index, double enterTime){
		double wait = time - enterTime;
		if(wait > maxWait){
			maxWait = wait;
		}
		averageWait += wait;
	}
	
	//Keeps track of total time along with Queue lengths
	public static void updateTimeStats(double time, int index, int qlen) {
		totalTime += time - lastTimeUpdateTime;
		lastTimeUpdateTime = time;
		if(qlen>maxQLength)
			maxQLength = qlen;
		averageQLength[index] += (int) (oldQLength[index] * (time - lastQUpdateTime[index]));
		//System.out.println(averageQLength[index]);
		lastQUpdateTime[index] = time;
		oldQLength[index] = qlen;
	}
	
	//Tracks each lane's busy times
	 public static void updateBusyTimeStats(double time, int index) {
		 busyTime[index] += time - lastUpdateTime[index];
		 lastUpdateTime[index] = time;
	 }
	 
	 //Tracks each lane's idle times
	 public static void updateIdleTimeStats(double time, int index) {
		 idleTime[index] += time - lastUpdateTime[index];
		 lastUpdateTime[index] = time;
	 }
	 
	 //Used in printout for average lane busy time
	 public static double averageBusy(){
		 double sum = 0;
		 for(int x = 0; x<checkerNumber; x++){
			 sum += busyTime[x];
		 }
		 return (sum/checkerNumber);
	 }
	 
	//Used in printout for average lane idle time
	 public static double averageIdle(){
		 double sum = 0;
		 for(int x = 0; x<checkerNumber; x++){
			 sum += idleTime[x];
		 }
		 return (sum/checkerNumber);
	 }
	
	//Used in printout for average lane queue length
	 public static double averageQueue(){
		 double sum = 0;
		 for(int x = 0; x<checkerNumber; x++){
			 sum += averageQLength[x];
		 }
		 return (sum/checkerNumber);
	 }
	 
	 //Updates numbers based on input and initializes arrays to the correct length
	 public static void updateStats(int checkers){
		 checkerNumber = checkers;
		 idleTime = new double[checkers];
		 busyTime = new double[checkers];
		 oldQLength = new int[checkers];
		 averageQLength = new int[checkers];
		 lastQUpdateTime = new double[checkers];
		 lastUpdateTime = new double[checkers];
	 }
	
	 //Only used in StoreSim
	public static void displayStats(){
		
		System.out.println("\n** Simulation Results **\n");
		System.out.println(" Calculated Simulation Time: " + totalTime);
		
        System.out.println(" Average idle Time: " + averageIdle());
        System.out.println(" Average busy Time: " + averageBusy());
        
        System.out.println();
        
        System.out.println(" Max wait time: " + maxWait);
        System.out.println(" Average wait time: " + (averageWait/customerServed));
        
        System.out.println();
        
        System.out.println(" Max Queue length: " + maxQLength);
        System.out.println(" Average Queue length: " + (averageQueue()/totalTime));
        
        System.out.println();
        
        System.out.println(" Number of Express Lanes: "+CheckerEvent.checkExpress());
        System.out.println(" Number of Checkers: "+CheckerEvent.checkNum());
        
        System.out.println();
        
        System.out.println(" Total Customers: "+customerTotal);
        System.out.println(" Total Customers served: "+customerServed);
	}

}
