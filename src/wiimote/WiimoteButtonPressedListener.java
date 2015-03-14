package wiimote;

import jgamepad.interfaces.ButtonPressedEvent;

/**
 * Listener that will trigger on button presses
 */
public class WiimoteButtonPressedListener {

    private ButtonPressedEvent pressEvent;
    private WiimoteButton button;

    public WiimoteButtonPressedListener(WiimoteButton button, ButtonPressedEvent pressEvent){
        if(pressEvent == null){
            throw new NullPointerException("ButtonPressedEvent object cannot be null");
        }

        this.button = button;
        this.pressEvent = pressEvent;
    }

    public void run(boolean pressed){
        pressEvent.run(pressed);
    }

    public WiimoteButton getButton(){
        return button;
    }
}
