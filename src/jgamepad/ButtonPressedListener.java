package jgamepad;

public class ButtonPressedListener implements ButtonListener{

    private int button;
    private ButtonPressedEvent pressEvent;
    private int state = 0;

    public ButtonPressedListener(int button, ButtonPressedEvent pressEvent){
        this.button = button;
        this.pressEvent = pressEvent;
    }

    public int getButton() {
        return button;
    }

    public int getState() {
        return state;
    }

    public void run(){
        if(state == 1)
            Logger.log(button + ": pressed");
        else
            Logger.log(button + ": released");
        pressEvent.run(state == 1);
    }

    public void swapState(){
        state = state == 0 ? 1 : 0;
    }
}
