package swicr.view;

import swicr.logic.ParkingGrid;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 2017-05-26.
 *
 * @author Adrian Zdanowicz
 */
public class CarSpriteCanvas extends JPanel {
    public final static int CIRCLE_SIZE = 25;
    public final static int CIRCLE_DIST = (CIRCLE_SIZE * 3) / 2;
    private ParkingGrid grid;

    public CarSpriteCanvas() {
        setMinimumSize(new Dimension(ParkingGrid.getGridWidth() * CIRCLE_DIST, (ParkingGrid.getGridHeight() + 1) * CIRCLE_DIST));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        grid.paint(g, 10, 10, CIRCLE_SIZE);
    }

    public void setGrid(ParkingGrid grid) {
        this.grid = grid;
    }
}
