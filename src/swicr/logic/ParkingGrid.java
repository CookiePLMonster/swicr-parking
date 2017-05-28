package swicr.logic;

import java.awt.*;

/**
 * Created on 2017-05-27.
 *
 * @author Adrian Zdanowicz
 */
public class ParkingGrid {
    private static final int GRID_WIDTH = 10;

    private static final int GRID_HEIGHT = 10;

    public enum MoveDirection {
        MOVE_UP,
        MOVE_DOWN,
        MOVE_LEFT,
        MOVE_RIGHT
    }

    private Car[][] parkingSpaces = new Car[GRID_HEIGHT+1][GRID_WIDTH];

    public static int getGridWidth() {
        return GRID_WIDTH;
    }

    public static int getGridHeight() {
        return GRID_HEIGHT;
    }

    public boolean addCar(Car car) {
        int i;
        int j;
        for ( i=0; i<GRID_HEIGHT; i+=2) {
            for ( j=0; j<GRID_WIDTH-1;j++) {
                if (parkingSpaces[i][j] == null) {
                    parkingSpaces[i][j] = car;
                    return true;
                }
            }
        }

        for ( i=1; i<GRID_HEIGHT; i+=2) {
            for ( j=0; j<GRID_WIDTH-1;j++) {
                if (parkingSpaces[i][j] == null) {
                    parkingSpaces[i][j] = car;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean retrieveCarById( int carID ) {
        Coords carCoords = findCarCoordsById( carID );

        return false;
    }

    private void moveCar(int fromX, int fromY, MoveDirection direction) {
        if( parkingSpaces [fromX][fromY] != null){
            switch (direction) {
                case MOVE_DOWN:
                    if (parkingSpaces[fromX + 1][fromY] == null) {
                        if(fromX==getGridWidth()-1 && fromY==getGridHeight()-1){
                            parkingSpaces[fromX][fromY] = null;
                        } else {
                            parkingSpaces[fromX + 1][fromY] = parkingSpaces[fromX][fromY];
                            parkingSpaces[fromX][fromY] = null;
                        }
                        break;
                    }
                case MOVE_UP:
                    if (parkingSpaces[fromX - 1][fromY] == null) {
                        parkingSpaces[fromX - 1][fromY] = parkingSpaces[fromX][fromY];
                        parkingSpaces[fromX][fromY] = null;
                        break;
                    }
                case MOVE_LEFT:
                    if (parkingSpaces[fromX][fromY - 1] == null) {
                        parkingSpaces[fromX][fromY - 1] = parkingSpaces[fromX][fromY];
                        parkingSpaces[fromX][fromY] = null;
                        break;
                    }
                case MOVE_RIGHT:
                    if (parkingSpaces[fromX][fromY + 1] == null) {
                        parkingSpaces[fromX][fromY + 1] = parkingSpaces[fromX][fromY];
                        parkingSpaces[fromX][fromY] = null;
                        break;
                    }
            }
        }
    }

    public void paint(Graphics g, int marginX, int marginY, int circleSize) {
        int y = 0;
        int posY = 0;
        for ( Car[] row : parkingSpaces ) {
            int x = 0;
            int posX = 0;
            for ( Car space : row ) {
                if ( y != GRID_HEIGHT || x == GRID_WIDTH-1 ) {

                    if (space != null) {
                        g.setColor(Color.BLUE);
                        g.fillOval(marginX + posX, marginY + posY, circleSize, circleSize);

                        g.setColor(Color.WHITE);
                        g.drawString(space.getName(), marginX + posX + (circleSize / 2), marginY + posY + (circleSize / 2));
                    }

                    // Empty space
                    g.setColor(Color.BLACK);
                    g.drawOval(marginX + posX, marginY + posY, circleSize, circleSize);

                }

                x++;
                posX += (circleSize * 3) / 2;
            }
            y++;
            posY += (circleSize * 3) / 2;
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

    public void findWay(int carID){
        Coords currentPosition = findCarCoordsById(carID);
            if (currentPosition.y == getGridWidth() - 1) {  // jeśli w ostatniej kolumnie
                moveCar(currentPosition.x, currentPosition.y, MoveDirection.MOVE_DOWN);
            } else if (parkingSpaces[currentPosition.x][currentPosition.y + 1] == null ) {   // jeśli prosta droga do ostatniej kolumny
                moveCar(currentPosition.x, currentPosition.y, MoveDirection.MOVE_RIGHT);
            } else if ( currentPosition.x != getGridHeight()-1 && parkingSpaces[currentPosition.x+1][currentPosition.y] == null ) { //jeśli pod spodem miejsce wolne
                moveCar(currentPosition.x,currentPosition.y,MoveDirection.MOVE_DOWN);
            } else if ( currentPosition.x != 0 && parkingSpaces[currentPosition.x-1][currentPosition.y] == null) {  //jeśli nad samochodem jest miejsce wolne
                moveCar(currentPosition.x,currentPosition.y,MoveDirection.MOVE_UP);
            } else if (currentPosition.x != getGridHeight()-1){
                int i;
                for (i = 0; i<getGridWidth();i++){
                    moveCar(currentPosition.x, 9-i,MoveDirection.MOVE_RIGHT);
                } moveCar(currentPosition.x+1,0,MoveDirection.MOVE_UP);
                for(i = 0; i<getGridWidth();i++){
                    moveCar(currentPosition.x+1,i,MoveDirection.MOVE_LEFT);
                } moveCar(currentPosition.x,getGridWidth()-1,MoveDirection.MOVE_DOWN);
                moveCar(currentPosition.x+1,getGridWidth()-1,MoveDirection.MOVE_LEFT);
            } else {
                int i;
                for (i = 0; i<getGridWidth();i++){
                    moveCar(currentPosition.x, 9-i,MoveDirection.MOVE_RIGHT);
                } moveCar(currentPosition.x,getGridWidth()-1,MoveDirection.MOVE_UP);
                moveCar(currentPosition.x-1,0,MoveDirection.MOVE_DOWN);
                for(i = 0; i<getGridWidth();i++) {
                    moveCar(currentPosition.x - 1, i, MoveDirection.MOVE_LEFT);
                }
            }

    }
}
