package jgamepad.interfaces;
import jgamepad.enums.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Checks if multiple buttons are pressed at the same time
 */
public abstract class MultipleButtonListener {

    private Map<Button, Integer> buttonMap = new HashMap<>();
    private Button[] buttons;
    private boolean allButtonsPressed = false;

    /**
     * Constructor
     * @param buttons - List of buttons to check
     */
    public MultipleButtonListener(List<Button> buttons){
        if(buttons == null){
            throw new NullPointerException("Button list cannot be null");
        }
        if(buttons.contains(null)){
            throw new NullPointerException("Button list cannot contain null values");
        }
        if(buttons.isEmpty()){
            throw new IllegalStateException("Button list cannot be empty");
        }

        for(Button button : buttons){
            buttonMap.put(button, 0);
        }
        this.buttons = buttons.toArray(new Button[buttons.size()]);
    }

    /**
     * Constructor
     * @param buttons - Array of buttons to check
     */
    public MultipleButtonListener(Button[] buttons){
        if(buttons == null){
            throw new NullPointerException("Button list cannot be null");
        }

        for(Button button : buttons){
            if(button == null)
                throw new NullPointerException("Button list cannot contain null values");

            buttonMap.put(button, 0);
        }
        this.buttons = buttons;
    }

    /**
     * Checks the status of the buttons.
     * If all the buttons are pressed it runs
     * @param data - Array with updated controller info
     */
    public final void checkState(int[] data){
        for(Button button : buttons){
            if(data[button.value] != buttonMap.get(button)){
                buttonMap.put(button, data[button.value]);
            }
        }
        boolean newState = check();
        if(newState != allButtonsPressed){
            allButtonsPressed = check();
            run(allButtonsPressed);
        }
    }

    private boolean check(){
        if(!buttonMap.containsValue(0))
            return true;

        return false;
    }

    public abstract void run(boolean pressed);

}
