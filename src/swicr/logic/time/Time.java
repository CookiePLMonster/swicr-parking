package swicr.logic.time;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 2017-05-29.
 *
 * @author Adrian Zdanowicz
 */
public class Time implements Runnable {
    static private final int TIMEDELAY = 2000;
    static private final int TIMESTEP = 5;

    static private final int MINUTES_PER_DAY = 60 * 24;

    private int currentTime = 0;

    private JLabel clock;
    private Thread timeThread;

    private List<TimeTickEvent> tickEvents = new LinkedList<TimeTickEvent>();

    public Time(int startTimeHour, int startTimeMin) {
        currentTime = startTimeHour * 60 + startTimeMin;

        timeThread = new Thread(this, "Time");
        timeThread.start();
    }

    public void setTimeText(JLabel clockField) {
        this.clock = clockField;
    }

    public void registerTickEvent(TimeTickEvent event) {
        tickEvents.add(event);
    }


    @Override
    public void run() {
        while ( true ) {

            int curTime = currentTime;
            SwingUtilities.invokeLater(() -> {
                int hour = curTime / 60;
                int minute = curTime % 60;

                StringBuilder string = new StringBuilder();
                if ( hour < 10 ) string.append('0');
                string.append(hour);

                string.append(':');

                if ( minute < 10 ) string.append('0');
                string.append(minute);

                clock.setText(string.toString());
            });

            try {
                Thread.sleep(TIMEDELAY);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            currentTime += TIMESTEP;
            if (currentTime >= MINUTES_PER_DAY) {
                currentTime -= MINUTES_PER_DAY;
            }
            for ( TimeTickEvent e : tickEvents ) {
                e.onTimeTick(currentTime);
            }
        }
    }
}
