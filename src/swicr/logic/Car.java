package swicr.logic;

import java.awt.*;

/**
 * Created on 2017-05-26.
 *
 * @author Adrian Zdanowicz
 */
public class Car {
    private int id;
    private String name;
    private Color color;

    public Car(int id, Color color) {
        this.id = id;
        this.name = Integer.toString(id);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
