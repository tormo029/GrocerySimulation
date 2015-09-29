
public class ShopperMaker implements Event {

    // Code used from CarMaker

    public ShopperMaker(double intval, int[] items) {
        interval = intval;
        itemDist = new int[items.length];
        System.arraycopy(items, 0, itemDist, 0, items.length);
    }

    //Method makes an array used to generate a random interval
    private double intRandomInterval() {
    	double low = interval   - .75*interval;
    	double lowm = interval  - .5*interval;
    	double lowh = interval  - .2*interval;
    	double highh = interval + .2*interval;
    	double highm = interval + .5*interval;
    	double high = interval  + .75*interval;
    	double[] intervalDist = {low,low,lowm,lowm,lowm,lowh,lowh,lowh,lowh,interval,interval,
    			highh,highh,highh,highh,highm,highm,highm,high,high};
        return intervalDist[(int)Math.floor((Math.random()*intervalDist.length))];
    }

    //Uses provided array to get a random item count
    private double selectItemCount() {
        return itemDist[(int)Math.floor((Math.random()*itemDist.length))];
    }

    public void run() {
        StoreSim.agenda.add(new ShopperMaker(interval, itemDist), intRandomInterval());
        Shopper newShopper = new Shopper(StoreSim.agenda.getCurrentTime(), selectItemCount());
    }

    private double interval;
    private int[] itemDist;

}
