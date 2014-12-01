package jgamepad.listeners;

import jgamepad.enums.Button;
import jgamepad.interfaces.ButtonPressedEvent;
import jgamepad.interfaces.MultipleButtonListener;

import java.util.List;

/**
 * Checks if several buttons is pressed at the same time
 */
public class MultipleButtonPressedListener extends MultipleButtonListener{

    private ButtonPressedEvent pressedEvent;

    /**
     * Checks if several buttons is pressed at the same time
     * @param buttons - List of buttons to check
     * @param pressedEvent - ButtonPressedEvent to run when the buttons are pressed
     */
    public MultipleButtonPressedListener(List<Button> buttons, ButtonPressedEvent pressedEvent){
        super(buttons);
        init(pressedEvent);
    }

    /**
     * Checks if several buttons is pressed at the same time
     * @param buttons - Array of buttons to check
     * @param pressedEvent - ButtonPressedEvent to run when the buttons are pressed
     */
    public MultipleButtonPressedListener(Button[] buttons, ButtonPressedEvent pressedEvent){
        super(buttons);
        init(pressedEvent);
    }

    private void init(ButtonPressedEvent pressedEvent){
        if(pressedEvent == null){
            throw new NullPointerException("Runnable object cannot be null");
        }
        this.pressedEvent = pressedEvent;
    }

    @Override
    public void run(boolean pressed) {
        pressedEvent.run(pressed);
    }
}
