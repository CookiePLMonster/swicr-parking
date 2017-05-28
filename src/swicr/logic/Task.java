package swicr.logic;

/**
 * Created by Jarek on 2017-05-28.
 */
public class Task {
    private Coords cod;
    private ParkingGrid.MoveDirection direction;

    public Task(Coords c, ParkingGrid.MoveDirection md ){
        this.cod = c;
        this.direction = md;
    }

    public Coords getCoords(){
        return cod;
    }

    public ParkingGrid.MoveDirection getDirection(){
        return direction;
    }

}
