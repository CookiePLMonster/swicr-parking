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
    private final int CIRCLE_SIZE = 25;
    private ParkingGrid grid;

    public CarSpriteCanvas() {
        setMinimumSize(new Dimension(ParkingGrid.getGridWidth() * (CIRCLE_SIZE*3)/2, (ParkingGrid.getGridHeight() + 1) * (CIRCLE_SIZE*3)/2));
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
