package swicr.logic.time;

import swicr.logic.ParkingSimulation;
import swicr.logic.model.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public TimeSchedule(ParkingSimulation parent, CarRepository cars) {
        this.parent = parent;

        for (Map.Entry<Integer, CarRepository.CarEntry> car : cars.getEntries().entrySet() ) {
            entries.add(new TimeEntry(car.getKey(), car.getValue().getDriveoff(), car.getValue().getDrivein()));
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
