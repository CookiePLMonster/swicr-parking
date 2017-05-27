package swicr.logic;

/**
 * Created on 2017-05-26.
 *
 * @author Adrian Zdanowicz
 */
public class Car {
    private int id;
    private String name;

    public Car(int id) {
        this.id = id;
        this.name = Integer.toString(id);
    }

    public void tick() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
