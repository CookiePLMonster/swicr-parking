package swicr.logic;

import swicr.logic.model.FindWayJob;
import swicr.logic.time.Time;
import swicr.logic.time.TimeSchedule;
import swicr.logic.time.TimeTickEvent;

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
    private Queue<FindWayJob> jobs = new ConcurrentLinkedQueue<FindWayJob>();

    private Time time = new Time(6, 0);
    private TimeSchedule schedule;

    public ParkingSimulation() {
        schedule = new TimeSchedule(this, "time.csv");
    }

    @Override
    public void run() {
        while (true) {
            FindWayJob currentJob = jobs.peek();
            if (currentJob != null) {
                if (currentJob.doJob()) {
                    jobs.remove(currentJob);
                }
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
        time.registerTickEvent(schedule);
        time.start();

        thread = new Thread(this, "Simulation");
        thread.start();
    }

    public ParkingGrid getGrid() {
        return grid;
    }

    public boolean addCar(int carID) {
        return grid.addCar(new Car(carID));
    }

    public void callCarOut(int carID) {
        jobs.add(new FindWayJob(carID) {
            @Override
            public boolean doJob() {
                return grid.findWayJob(this);
            }
        });
    }

    public void setupTime(TimeTickEvent clockField) {
        time.registerTickEvent(clockField);
    }

    public void insert(int carID) {
        jobs.add(new FindWayJob(carID) {
            @Override
            public boolean doJob() {
                return grid.insertJob(this);
            }
        });
    }
}