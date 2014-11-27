package jgamepad.listeners;

import jgamepad.enums.Button;
import jgamepad.interfaces.ButtonListener;
import jgamepad.interfaces.ButtonPressedEvent;

/**
 * Listener that will trigger on button presses
 */
public class ButtonPressedListener implements ButtonListener {

    private Button button;
    private ButtonPressedEvent pressEvent;
    private int state = 0;

    /**
     * Creates a listener that will trigger when the set button is pressed
     * @param button - The button to trigger on
     * @param pressEvent - The action to run
     */
    public ButtonPressedListener(Button button, ButtonPressedEvent pressEvent){
        this.button = button;
        this.pressEvent = pressEvent;
    }

    public Button getButton() {
        return button;
    }

    public int getState() {
        return state;
    }

    public void run(){
        pressEvent.run(state == 1);
    }

    public void swapState(){
        state = state == 0 ? 1 : 0;
    }
}
