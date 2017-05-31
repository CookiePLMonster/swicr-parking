package swicr.logic.model;

import swicr.logic.ParkingGrid;

/**
 * Created on 2017-05-29.
 *
 * @author Adrian Zdanowicz
 */ // Job classes
public abstract class FindWayJob implements Job {
    public int carID;
    public boolean[][] visited;

    public FindWayJob(int carID) {
        this.carID = carID;
        this.visited = new boolean[ParkingGrid.getGridHeight() + 1][ParkingGrid.getGridWidth()];
    }

    @Override
    public abstract boolean doJob();

}
