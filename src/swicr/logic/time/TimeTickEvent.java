package swicr.logic.time;

/**
 * Created on 2017-05-29.
 *
 * @author Adrian Zdanowicz
 */
public interface TimeTickEvent {
    void setInitialState(int time);
    void onTimeTick(int time);
}
