package swicr.logic;

import swicr.logic.model.FindWayJob;
import swicr.view.CarSpriteCanvas;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 2017-05-27.
 *
 * @author Adrian Zdanowicz
 */
public class ParkingGrid {
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;

    private JPanel canvasForPaint;

    public void repaintCanvas() {
        canvasForPaint.repaint();
    }

    public enum MoveDirection {
        MOVE_UP,
        MOVE_DOWN,
        MOVE_LEFT,
        MOVE_RIGHT
    }

    private ParkingSimulation parentSimulation;

    private Car[][] parkingSpaces = new Car[GRID_HEIGHT+1][GRID_WIDTH];

    private MoveDirection[][] moveVectors;
    private double moveDelta;
    private double timeStep;

    public ParkingGrid(ParkingSimulation parentSimulation) {
        this.parentSimulation = parentSimulation;
    }

    public static int getGridWidth() {
        return GRID_WIDTH;
    }

    public static int getGridHeight() {
        return GRID_HEIGHT;
    }

    public boolean addCar(Car car) { //przy wstawieniu zwraca true
        int i;
        int j;
        for(i=getGridHeight()-1;i>=0;i--){
            if(parkingSpaces[i][0] == null){
                for(j=0;j<getGridWidth();j++){
                    moveCar(i,j,MoveDirection.MOVE_LEFT, false);
                }
                parkingSpaces[i][getGridWidth()-2] = car;
                return true;
            }
        }
        return false;
    }

    private void moveCar(int fromX, int fromY, MoveDirection direction) {
        moveCar(fromX, fromY, direction, true);
    }

    private void moveCar(int fromX, int fromY, MoveDirection direction, boolean animatedMove) {
        if( parkingSpaces [fromX][fromY] != null){
            switch (direction){
                case MOVE_DOWN:
                    if(parkingSpaces [fromX+1][fromY] == null){
                        parkingSpaces [fromX+1][fromY] = parkingSpaces[fromX][fromY];
                        parkingSpaces[fromX][fromY] = null;
                        if ( animatedMove ) moveVectors[fromX+1][fromY] = direction;
                    }
                    break;
                case MOVE_UP:
                    if(parkingSpaces [fromX-1][fromY] == null){
                        parkingSpaces [fromX-1][fromY] = parkingSpaces[fromX][fromY];
                        parkingSpaces[fromX][fromY] = null;
                        if ( animatedMove ) moveVectors[fromX-1][fromY] = direction;
                    }
                    break;
                case MOVE_LEFT:
                    if(parkingSpaces [fromX][fromY-1] == null){
                        parkingSpaces [fromX][fromY-1] = parkingSpaces[fromX][fromY];
                        parkingSpaces[fromX][fromY] = null;
                        if ( animatedMove ) moveVectors[fromX][fromY-1] = direction;
                    }
                    break;
                case MOVE_RIGHT:
                    if(parkingSpaces [fromX][fromY+1] == null){
                        parkingSpaces [fromX][fromY+1] = parkingSpaces[fromX][fromY];
                        parkingSpaces[fromX][fromY] = null;
                        if ( animatedMove ) moveVectors[fromX][fromY+1] = direction;
                    }
                    break;
            }
        }
    }

    public synchronized void setupMoveVector(double timeStep) {
        moveVectors = new MoveDirection[GRID_HEIGHT+1][GRID_WIDTH];
        this.timeStep = timeStep;
        this.moveDelta = CarSpriteCanvas.CIRCLE_DIST;
    }

    public void paint(Graphics g, int marginX, int marginY, int circleSize) {
        paintEmptySpaces(g, marginX, marginY, circleSize);

        marginX += 2;
        marginY += 2;
        circleSize -= 4;

        final double delta;
        synchronized (this) {
            delta = moveDelta;
        }
        int y = 0;
        int posY = 0;
        for ( Car[] row : parkingSpaces ) {
            int x = 0;
            int posX = 0;
            for ( Car space : row ) {
                if ( y != GRID_HEIGHT || x == GRID_WIDTH-1 ) {

                    if (space != null) {
                        int positionX = marginX + posX;
                        int positionY = marginY + posY;

                        if ( moveVectors[y][x] != null ) {
                            switch (moveVectors[y][x]) {
                                case MOVE_DOWN:
                                    positionY -= delta;
                                    break;
                                case MOVE_UP:
                                    positionY += delta;
                                    break;
                                case MOVE_LEFT:
                                    positionX += delta;
                                    break;
                                case MOVE_RIGHT:
                                    positionX -= delta;
                                    break;
                            }
                        }

                        g.setColor(space.getColor());
                        g.fillOval(positionX, positionY, circleSize, circleSize);

                        g.setColor(Color.WHITE);
                        g.drawString(space.getName(), positionX + (circleSize / 2), positionY + (circleSize / 2));
                    }

                }

                x++;
                posX += CarSpriteCanvas.CIRCLE_DIST;
            }
            y++;
            posY += CarSpriteCanvas.CIRCLE_DIST;
        }
        synchronized (this) {
            moveDelta -= (timeStep / parentSimulation.scaledTickTime()) * CarSpriteCanvas.CIRCLE_DIST;
        }
    }

    private void paintEmptySpaces(Graphics g, int marginX, int marginY, int circleSize) {
        g.setColor(Color.BLACK);

        int y = 0;
        int posY = 0;
        for ( Car[] row : parkingSpaces ) {
            int x = 0;
            int posX = 0;
            for ( Car ignored : row ) {
                if ( y != GRID_HEIGHT || x == GRID_WIDTH-1 ) {
                    // Empty space
                    g.drawOval(marginX + posX, marginY + posY, circleSize, circleSize);

                }

                x++;
                posX += CarSpriteCanvas.CIRCLE_DIST;
            }
            y++;
            posY += CarSpriteCanvas.CIRCLE_DIST;
        }
    }


    private Coords findCarCoordsById( int carID ) {
        int x = 0;
        for ( Car[] row : parkingSpaces ) {
            int y = 0;
            for ( Car space : row ) {
                if ( space != null && space.getId() == carID ) {
                    return new Coords(x, y);
                }

                y++;
            }
            x++;
        }
        return null;
    }

    public boolean maintenanceJobs(){
        int i,j;
        for(i=0; i<getGridHeight();i++){
            for(j=getGridWidth()-3;j>=0;j--){
                moveCar(i,j,MoveDirection.MOVE_RIGHT);
            }
        }
        return true;
    }

    protected boolean insertJob(FindWayJob job) {
        if ( findCarCoordsById(job.carID) == null){  // jeśli nie ma takiego samochodu - wprowadź go
            parkingSpaces[getGridHeight()][getGridWidth()-1] = parentSimulation.getCarsRepository().fetchCar(job.carID);
            return false;
        }
        Coords currentCoords = findCarCoordsById(job.carID);

        if ( currentCoords.x == getGridHeight()){  // jeśli jest na miejscu startowym
            moveCar(currentCoords.x,currentCoords.y,MoveDirection.MOVE_UP);
            return false;
        } else if ( parkingSpaces[currentCoords.x][currentCoords.y-1] == null){ // jeśli miejsce po lewo jest wolne
            moveCar(currentCoords.x,currentCoords.y,MoveDirection.MOVE_LEFT);
            return true;
        } else if ( parkingSpaces[currentCoords.x][0] == null){ // jeśli pierwsze miejsce w rzędzie jest wolne
            int i;
            for( i=0; i< getGridWidth();i++ ){
                moveCar(currentCoords.x,i,MoveDirection.MOVE_LEFT);
            }
            return true;
        } else {
            moveCar(currentCoords.x,currentCoords.y,MoveDirection.MOVE_UP);
            return false;
        }


    }

    // Jobs
    protected boolean findWayJob(FindWayJob job){
        Coords currentPosition = findCarCoordsById(job.carID);
        if ( currentPosition == null ) return true;

        if ( currentPosition.y == GRID_HEIGHT-1 && currentPosition.x == GRID_WIDTH) {
            parentSimulation.getCarsRepository().carGotMovedOut(job.carID);
            parkingSpaces[currentPosition.x][currentPosition.y] = null;
            return true;
        }

        if (currentPosition.y == getGridWidth() - 1 ) {  // jeśli w ostatniej kolumnie
            moveCar(currentPosition.x, currentPosition.y, MoveDirection.MOVE_DOWN);
        } else if (parkingSpaces[currentPosition.x][currentPosition.y + 1] == null && !job.visited[currentPosition.x][currentPosition.y + 1]) {   // jeśli prosta droga do ostatniej kolumny
            moveCar(currentPosition.x, currentPosition.y, MoveDirection.MOVE_RIGHT);
            job.visited[currentPosition.x][currentPosition.y+1] = true;
        } else if ( currentPosition.x != 0 && parkingSpaces[currentPosition.x-1][currentPosition.y] == null && !job.visited[currentPosition.x - 1][currentPosition.y]) {  //jeśli nad samochodem jest miejsce wolne
            moveCar(currentPosition.x,currentPosition.y,MoveDirection.MOVE_UP);
            job.visited[currentPosition.x+1][currentPosition.y] = true;
        } else if (currentPosition.x != getGridHeight()-1){
            int i;
            for (i = 0; i<getGridWidth();i++){
                moveCar(currentPosition.x, getGridWidth()-1-i,MoveDirection.MOVE_RIGHT);
            } moveCar(currentPosition.x+1,0,MoveDirection.MOVE_UP);
            for(i = 0; i<getGridWidth();i++){
                moveCar(currentPosition.x+1,i,MoveDirection.MOVE_LEFT);
            } moveCar(currentPosition.x,getGridWidth()-1,MoveDirection.MOVE_DOWN, false);
            moveCar(currentPosition.x+1,getGridWidth()-1,MoveDirection.MOVE_LEFT, false);
            moveVectors[currentPosition.x+1][getGridWidth()-2] = MoveDirection.MOVE_DOWN;
        } else {
            int i;
            for (i = 0; i<getGridWidth();i++){
                moveCar(currentPosition.x, 9-i,MoveDirection.MOVE_RIGHT);
            } moveCar(currentPosition.x,getGridWidth()-1,MoveDirection.MOVE_UP, false);
            moveCar(currentPosition.x-1,0,MoveDirection.MOVE_DOWN);
            for(i = 0; i<getGridWidth();i++) {
                moveCar(currentPosition.x - 1, i, MoveDirection.MOVE_LEFT);
            }
            moveVectors[currentPosition.x-1][getGridWidth()-2] = MoveDirection.MOVE_UP;
        }
        return false;
    }

    public void setupCanvas(CarSpriteCanvas canvas) {
        this.canvasForPaint = canvas;
        canvas.setGrid(this);
    }
}
