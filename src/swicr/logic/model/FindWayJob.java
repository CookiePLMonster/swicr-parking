package swicr.logic.model;

import swicr.logic.ParkingGrid;

/**
 * Created on 2017-05-29.
 *
 * @author Adrian Zdanowicz
 */ // Job classes
public class FindWayJob {
    public int carID;
    public boolean[][] visited;

    public FindWayJob(ParkingGrid grid, int carID) {
        this.carID = carID;
        this.visited = new boolean[grid.getGridHeight() + 1][grid.getGridWidth()];
    }
}
