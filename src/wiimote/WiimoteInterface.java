package wiimote;

import com.sun.jna.Callback;
import com.sun.jna.Library;

public interface WiimoteInterface extends Library{

    void initWiimotes(int maxWiimotes);
    int connect();
    void setLeds();
    void setCustomLeds(int wiimote, int leds);

    void startRumble(int wiimote);
    void stopRumble(int wiimote);
    void wiimoteStatus(int wiimote);

    void updateListeners(int wiimote, int[] buttons, int size);
    int updateWiimote();
    int test(int x, int y);
    boolean nunchuckStatus(int wiimote, Nunchuck nunchuck);


    public interface eventCallback extends Callback {

        boolean callback(int wiimote, int button, boolean pressed);
    }
    public void setEventCallback(eventCallback evnHnd);

}
