package swicr.logic;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created on 2017-05-29.
 *
 * @author Adrian Zdanowicz
 */
public class ParkingSimulation implements Runnable {
    private Thread thread;

    private ParkingGrid grid = new ParkingGrid();
    private Queue<Integer> findWayJobs = new ConcurrentLinkedQueue<Integer>();

    @Override
    public void run() {
        while (true) {
            Integer currentFindWay = findWayJobs.peek();
            if (currentFindWay != null) {
                if (grid.findWayJob(currentFindWay))
                    findWayJobs.remove();
            }


            grid.repaintCanvas();
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void start() {
        thread = new Thread(this, "Simulation");
        thread.start();
    }

    public ParkingGrid getGrid() {
        return grid;
    }

    public boolean addCar(Car car) {
        return grid.addCar(car);
    }

    public void findWay(int carID) {
        findWayJobs.add(carID);
    }
}