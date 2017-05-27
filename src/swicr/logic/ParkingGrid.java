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

    private Car[][] parkingSpaces = new Car[GRID_HEIGHT+1][GRID_WIDTH];

    public static int getGridWidth() {
        return GRID_WIDTH;
    }

    public static int getGridHeight() {
        return GRID_HEIGHT;
    }

    public boolean addCar(Car car) {
        // AZ: Tutaj logika dodawania samochodow i ustawiania ich w odpowiedni sposob!
        int i = 0;
        for ( Car[] row : parkingSpaces ) {
            if ( i == GRID_HEIGHT ) return false; // Nie pozwol dodac pojazdu do najnizszego rzedu

            int j = 0;
            for (Car space : row) {
                if (space == null) {
                    parkingSpaces[i][j] = car;
                    return true;
                }
                j++;
            }
            i++;
        }
        return false;
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
}
