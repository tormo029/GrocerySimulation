// Priority Queue and Simulation
// Segment class used in priority queue (PQ.java)
// Uses queue class Q1.java

//Class borrowed from Car Simulator
public class Segment {

    private double time;
    private Q1 q;
    private Segment next;

    // constructor

    public Segment(double t) {
        time = t;
        q = new Q1();
        next = null;
    }

    // methods

    public double getTime() {
        return time;
    }

    public Q1 getEvents() {
        return q;
    }

    public Segment getNext() {
        return next;
    }

    public void setNext(Segment nextSegment) {
        next = nextSegment;
    }

}  // Segment class
