package swicr.view;

import swicr.logic.time.TimeTickEvent;

import javax.swing.*;

/**
 * Created on 2017-05-29.
 *
 * @author Adrian Zdanowicz
 */
public class ClockLabel extends JLabel implements TimeTickEvent {
    @Override
    public void onTimeTick(int time) {
        SwingUtilities.invokeLater(() -> {
            int hour = time / 60;
            int minute = time % 60;

            StringBuilder string = new StringBuilder();
            if ( hour < 10 ) string.append('0');
            string.append(hour);

            string.append(':');

            if ( minute < 10 ) string.append('0');
            string.append(minute);

            setText(string.toString());
        });
    }
}
