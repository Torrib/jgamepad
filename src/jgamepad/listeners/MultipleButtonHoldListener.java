package jgamepad.listeners;

import jgamepad.enums.Button;
import jgamepad.interfaces.MultipleButtonListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Takes a list of buttons, a delay and a runnable
 * Runs the runnable if all the buttons has been held for the set delay
 */
public class MultipleButtonHoldListener extends MultipleButtonListener {

    private int delay;
    private Timer timer;
    private Runnable holdEvent;

    /**
     * Takes a list of buttons, a delay and a runnable
     * Runs the runnable if all the buttons has been held for the set delay
     * @param buttons - List of the buttons to check
     * @param delay - How long the buttons need to be held (ms)
     * @param holdEvent - The Runnable to run when the buttons has been held
     */
    public MultipleButtonHoldListener(List<Button> buttons, int delay, Runnable holdEvent) {
        super(buttons);
        init(delay, holdEvent);
    }

    /**
     * Takes an array of buttons, a delay and a runnable
     * Runs the runnable if all the buttons has been held for the set delay
     * @param buttons - An array of buttons to check
     * @param delay - How long the buttons need to be held (ms)
     * @param holdEvent - The Runnable to run when the buttons has been held
     */
    public MultipleButtonHoldListener(Button[] buttons, int delay, Runnable holdEvent) {
        super(buttons);
        init(delay, holdEvent);
    }

    private void init(int delay, Runnable pressedEvent){
        if(pressedEvent == null){
            throw new NullPointerException("Runnable cannot be null");
        }

        this.delay = delay;
        this.holdEvent = pressedEvent;
    }

    public void run(boolean pressed) {
        if(pressed == true){
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
}
