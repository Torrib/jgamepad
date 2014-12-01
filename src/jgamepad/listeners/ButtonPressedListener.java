package jgamepad.listeners;

import jgamepad.enums.Button;
import jgamepad.interfaces.ButtonListener;
import jgamepad.interfaces.ButtonPressedEvent;

/**
 * Listener that will trigger on button presses
 */
public class ButtonPressedListener extends ButtonListener {

    private ButtonPressedEvent pressEvent;

    /**
     * Creates a listener that will trigger when the set button is pressed
     * @param button - The button to trigger on
     * @param pressEvent - The action to run
     */
    public ButtonPressedListener(Button button, ButtonPressedEvent pressEvent){
        super(button);
        if(pressEvent == null){
            throw new NullPointerException("ButtonPressedEvent object cannot be null");
        }

        this.pressEvent = pressEvent;
    }

    public void run(boolean pressed){
        pressEvent.run(pressed);
    }
}
