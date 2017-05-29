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

    private Thread timeThread;

    private List<TimeTickEvent> tickEvents = new LinkedList<TimeTickEvent>();

    public Time(int startTimeHour, int startTimeMin) {
        currentTime = startTimeHour * 60 + startTimeMin;

        timeThread = new Thread(this, "Time");
    }

    public void registerTickEvent(TimeTickEvent event) {
        tickEvents.add(event);
    }

    private void dispatchTickEvents(int time) {
        for ( TimeTickEvent e : tickEvents ) {
            e.onTimeTick(time);
        }
    }

    public void start() {
        timeThread.start();
    }

    @Override
    public void run() {
        dispatchTickEvents(currentTime);

        while ( true ) {
            try {
                Thread.sleep(TIMEDELAY);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            currentTime += TIMESTEP;
            if (currentTime >= MINUTES_PER_DAY) {
                currentTime -= MINUTES_PER_DAY;
            }
            dispatchTickEvents(currentTime);
        }
    }
}
