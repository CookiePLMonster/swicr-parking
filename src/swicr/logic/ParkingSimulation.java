package swicr.logic;

import swicr.logic.model.CarRepository;
import swicr.logic.model.FindWayJob;
import swicr.logic.model.Job;
import swicr.logic.time.Time;
import swicr.logic.time.TimeSchedule;
import swicr.logic.time.TimeTickEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created on 2017-05-29.
 *
 * @author Adrian Zdanowicz
 */
public class ParkingSimulation implements Runnable {
    public static final int SIMULATION_RATE = 60;
    public static final int SIMULATION_TICK_TIME = 250;

    private Thread thread;

    private CarRepository cars;

    private ParkingGrid grid = new ParkingGrid(this);
    private Queue<Job> jobs = new ConcurrentLinkedQueue<>();

    private Time time = new Time(6, 0);
    private TimeSchedule schedule;

    public ParkingSimulation() {
        cars = new CarRepository("time.csv");
        schedule = new TimeSchedule(this, cars);
    }

    public int scaledTickTime() {
        double scaledTick = (double)SIMULATION_TICK_TIME * (100.0/time.getTimeScale());
        return (int)scaledTick;
    }

    @Override
    public void run() {
        while (true) {
            double timestep = 1000.0 / SIMULATION_RATE;
            grid.setupMoveVector(timestep);
            Job currentJob = jobs.peek();
            if (currentJob != null) {
                if (currentJob.doJob()) {
                    jobs.remove(currentJob);
                }
            } else {
                if ( !time.getIsStopped() ) {
                    maintenanceJob();
                }
            }

            int delay = scaledTickTime();
            while ( delay > 0 ) {
                try {
                    Thread.sleep((int)timestep);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                grid.repaintCanvas();
                delay -= (int)timestep;
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

    public CarRepository getCarsRepository() {
        return cars;
    }

    public boolean addCar(int carID) {
        return grid.addCar(cars.fetchCar(carID));
    }

    public void callCarOut(int carID) {
        if ( !cars.isParked(carID) ) return;
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
    public boolean toggleTimeState() {
        return time.toggleTimeState();
    }
    public void setTimeScale(int value) {
        time.setTimeScale(value);
    }

    public void insert(int carID) {
        if ( cars.isParked(carID) ) return;
        jobs.add(new FindWayJob(carID) {
            @Override
            public boolean doJob() {
                return grid.insertJob(this);
            }
        });
    }

    public void insertRandom() {
        List<Integer> asList = new ArrayList(cars.getCarsNotParked());
        Collections.shuffle(asList);
        insert(asList.get(0));
    }

    public void maintenanceJob() {
        jobs.add(() -> grid.maintenanceJobs());
    }
}