package swicr.logic.time;

import swicr.logic.ParkingSimulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2017-05-28.
 *
 * @author Adrian Zdanowicz
 */
public class TimeSchedule implements TimeTickEvent {

    private class TimeEntry {
        private int carID;
        private int driveoff;
        private int drivein;

        public TimeEntry(int carID, int driveoff, int drivein) {
            this.carID = carID;
            this.driveoff = driveoff;
            this.drivein = drivein;
        }
    }

    private ParkingSimulation parent;

    private List<TimeEntry> entries = new ArrayList<TimeEntry>();

    public TimeSchedule(ParkingSimulation parent, String fileName) {
        this.parent = parent;

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                int carID = Integer.parseInt(tokens[0]);
                int driveoff = Integer.parseInt(tokens[4]);
                int drivein = Integer.parseInt(tokens[5]);

                entries.add(new TimeEntry(carID, driveoff, drivein));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setInitialState(int time) {
        List<TimeEntry> carsPresent = entries.stream().filter(e -> {
            if ( e.driveoff < e.drivein ) {
                return (time+Time.getResolution()) < e.driveoff || time >= e.drivein;
            }
            return time >= e.drivein && (time+Time.getResolution()) < e.driveoff;

        }).collect(Collectors.toList());

        for ( TimeEntry e : carsPresent ) {
            parent.addCar(e.carID);
        }
    }

    @Override
    public void onTimeTick(int time) {
        List<TimeEntry> carsToAdd = entries.stream().filter((foo) -> foo.drivein >= time && foo.drivein < (time+Time.getResolution()) ).collect(Collectors.toList());
        List<TimeEntry> carsToExit = entries.stream().filter((foo) -> foo.driveoff >= time && foo.driveoff < (time+Time.getResolution()) ).collect(Collectors.toList());

        for ( TimeEntry e : carsToAdd ) {
            parent.insert(e.carID);
        }

        for ( TimeEntry e : carsToExit ) {
            parent.callCarOut(e.carID);
        }
    }
}
