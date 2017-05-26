package swicr;

import java.awt.*;

/**
 * Created on 2017-05-26.
 *
 * @author Adrian Zdanowicz
 */
public class Car {
    private final int CIRCLE_SIZE = 50;
    private int x, y;

    public Car(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick() {

    }

    public void paint(Graphics g, int marginX, int marginY) {
        g.setColor(Color.BLUE);
        g.fillOval(marginX + x, marginY + y, CIRCLE_SIZE, CIRCLE_SIZE);

        g.setColor(Color.BLACK);
        g.drawOval(marginX + x, marginY + y,CIRCLE_SIZE, CIRCLE_SIZE);
    }
}
