package jgamepad;

import java.util.Timer;
import java.util.TimerTask;

public class ButtonHoldListener implements ButtonListener{

    private int button;
    private Runnable holdEvent;
    private int state = 0;
    private int delay;
    private Timer timer;

    public ButtonHoldListener( int button, int delay, Runnable holdEvent){
        this.button = button;
        this.holdEvent = holdEvent;
        this.delay = delay;
    }

    public int getState() {
        return state;
    }

    public void run(){
        if(state == 1){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Logger.log("Button " + button + ": hold triggered");
                    (new Thread(holdEvent)).start();
                }
            }, delay);
        }
        else{
            Logger.log("Button " + button + ": hold canceled");
            timer.cancel();
        }
    }

    public void swapState(){
        state = state == 0 ? 1 : 0;
    }

    public int getButton(){
        return button;
    }
}
