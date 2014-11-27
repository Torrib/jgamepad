package jgamepad.listeners;

import jgamepad.enums.Button;
import jgamepad.interfaces.ButtonListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Listener object that perform an action when a button has been held for a set duration.
 */
public class ButtonHoldListener implements ButtonListener {

    private Button button;
    private Runnable holdEvent;
    private int state = 0;
    private int delay;
    private Timer timer;

    /**
     * Creates a listener object that perform an action when a button has been held for a set duration.
     * @param button - The button to trigger on.
     * @param delay - The duration the button have to be pressed (ms).
     * @param holdEvent - The event to trigger.
     */
    public ButtonHoldListener( Button button, int delay, Runnable holdEvent){
        this.button = button;
        this.holdEvent = holdEvent;
        this.delay = delay;
    }

    /**
     * Returns the current state of the button
     * @return int
     */
    public int getState() {
        return state;
    }

    /**
     * When the set button is pressed a timer is started with the set duration.
     * If the button is released the timer is stopped, otherwise the action is run.
     */
    public void run(){
        if(state == 1){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    (new Thread(holdEvent)).start();
                }
            }, delay);
        }
        else{
            timer.cancel();
        }
    }

    public void swapState(){
        state = state == 0 ? 1 : 0;
    }

    public Button getButton(){
        return button;
    }
}
