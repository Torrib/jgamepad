package examples;

import jgamepad.Controller;
import jgamepad.enums.Button;
import jgamepad.listeners.MultipleButtonHoldListener;
import jgamepad.listeners.MultipleButtonPressedListener;

/**
 * Shows example usage of the MultipleButtonPressedListener
 * and the MultipleButtonHoldListener classes
 */
public class SimpleMultipleButtonListener {

    public static void main(String[] args){
        Controller.dllPath = System.getProperty("user.dir") + "\\release";
        Controller controller = new Controller(0);

        //Creates an array of buttons, any List object works as well
        Button[] buttons1 = {Button.L1, Button.R1};

        //When L1 and R1 are pressed at the same time the controller vibrates
        controller.addMultipleButtonListener(new MultipleButtonPressedListener(buttons1, pressed -> {
                if(pressed) {
                    System.out.println("Pressed!");
                    controller.vibrate();
                }
        }));

        Button[] buttons2 = {Button.START, Button.BACK};

        // Prints out when the start and back buttons have been held for 1 second
        controller.addMultipleButtonListener(new MultipleButtonHoldListener(buttons2, 1000,
                () -> System.out.println("Back and start held for 1 second")));

    }
}
