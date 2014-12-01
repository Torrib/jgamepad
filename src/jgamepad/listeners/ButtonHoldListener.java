package jgamepad.listeners;

import jgamepad.enums.Button;
import jgamepad.interfaces.ButtonListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Listener object that perform an action when a button has been held for a set duration.
 */
public class ButtonHoldListener extends ButtonListener {

    private Runnable runnable;
    private int delay;
    private Timer timer;

    /**
     * Creates a listener object that perform an action when a button has been held for a set duration.
     * @param button - The button to trigger on.
     * @param delay - The duration the button have to be pressed (ms).
     * @param runnable - The event to trigger.
     */
    public ButtonHoldListener( Button button, int delay, Runnable runnable){
        super(button);
        if(runnable == null){
            throw new NullPointerException("Runnable object cannot be null");
        }

        this.runnable = runnable;
        this.delay = delay;
    }

    /**
     * When the set button is pressed a timer is started with the set duration.
     * If the button is released the timer is stopped, otherwise the action is run.
     */
    public void run(boolean pressed){
        if(pressed){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    (new Thread(runnable)).start();
                }
            }, delay);
        }
        else{
            timer.cancel();
        }
    }
}
