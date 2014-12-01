package jgamepad.interfaces;

import jgamepad.enums.Button;

public abstract class ButtonListener {

    private Button button;
    private int state = 0;

    public ButtonListener(Button button){
        if(button == null){
            throw new NullPointerException("Button object cannot be null");
        }
        this.button = button;
    }


    public Button getButton() {
        return button;
    }

    public int getState() {
        return state;
    }

    public void swapState(){
        state = state == 0 ? 1 : 0;
    }


    public abstract void run(boolean pressed);
}
