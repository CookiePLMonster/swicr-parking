package swicr.logic.model;

import swicr.logic.Car;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created on 2017-06-04.
 *
 * @author Adrian Zdanowicz
 */
public class CarRepository {

    public class CarEntry {
        private Color color;
        private int driveoff;
        private int drivein;

        public CarEntry(Color color, int driveoff, int drivein) {
            this.color = color;
            this.driveoff = driveoff;
            this.drivein = drivein;
        }

        public Color getColor() {
            return color;
        }

        public int getDriveoff() {
            return driveoff;
        }

        public int getDrivein() {
            return drivein;
        }
    }

    private Map<Integer, CarEntry> entries = new HashMap<>();
    private Set<Integer> carsNotParked = new HashSet<>();

    public CarRepository(String fileName) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                int carID = Integer.parseInt(tokens[0]);
                int red = Integer.parseInt(tokens[1]);
                int green = Integer.parseInt(tokens[2]);
                int blue = Integer.parseInt(tokens[3]);
                int driveoff = Integer.parseInt(tokens[4]);
                int drivein = Integer.parseInt(tokens[5]);

                entries.put(carID, new CarEntry(new Color(red, green, blue), driveoff, drivein));
                carsNotParked.add(carID);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Car fetchCar(int carID) {
        CarEntry carProperties = entries.get(carID);
        if ( carProperties != null ) {
            carsNotParked.remove(carID);
            return new Car(carID, carProperties.getColor());
        }
        return null;
    }

    public boolean isParked(int carID) {
        return entries.containsKey(carID) && !carsNotParked.contains(carID);
    }

    public void carGotMovedOut(int carID) {
        carsNotParked.add(carID);
    }

    public Map<Integer, CarEntry> getEntries() {
        return entries;
    }

    public Set<Integer> getCarsNotParked() {
        return carsNotParked;
    }
}
