package swicr.logic.time;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private List<TimeEntry> entries = new ArrayList<TimeEntry>();

    public TimeSchedule(String fileName) {
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
    public void onTimeTick(int time) {

    }
}
