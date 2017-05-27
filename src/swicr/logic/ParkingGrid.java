package swicr.logic;

import java.awt.*;

/**
 * Created on 2017-05-27.
 *
 * @author Adrian Zdanowicz
 */
public class ParkingGrid {
    private static final int GRID_WIDTH = 5;
    private static final int GRID_HEIGHT = 5;

    private Car[][] parkingSpaces = new Car[GRID_HEIGHT][GRID_WIDTH];

    public boolean addCar(Car car) {
        // AZ: Tutaj logika dodawania samochodow i ustawiania ich w odpowiedni sposob!
        int i = 0;
        for ( Car[] row : parkingSpaces ) {
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
        for ( Car[] row : parkingSpaces ) {
            int x = 0;
            for ( Car space : row ) {
                if ( space != null ) {
                    g.setColor(Color.BLUE);
                    g.fillOval(marginX + x, marginY + y, circleSize, circleSize);
                }

                // Empty space
                g.setColor(Color.BLACK);
                g.drawOval(marginX + x, marginY + y,circleSize, circleSize);

                x += 75;
            }
            y += 75;
        }
    }
}
