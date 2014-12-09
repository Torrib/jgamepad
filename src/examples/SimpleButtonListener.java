package examples;

import jgamepad.Controller;
import jgamepad.enums.Button;
import jgamepad.listeners.ButtonHoldListener;
import jgamepad.listeners.ButtonPressedListener;

/**
 * Simple example that shows how the button listeners work
 */
class SimpleButtonListener {

    public static void main(String[] args) {

        //Sets the path to controller.dll directory
        //Controller.dllPath = System.getProperty("user.dir") + "\\release";

        Controller controller = new Controller(0);

        //Adds a button pressed listener to the controller that prints when A is pressed and released
        controller.addButtonListener(new ButtonPressedListener(Button.A, pressed -> {
            if(pressed){
                System.out.println("A pressed!");
            }
            else{
                System.out.println("A released");
            }
        }));

        //Adds a button hold listener that prints when the start button is held for one second
        controller.addButtonListener(new ButtonHoldListener(Button.START, 1000, () ->
                System.out.println("Start held for 1 second")));

        //Exits when GUIDE is pressed
        controller.addButtonListener(new ButtonPressedListener(Button.GUIDE, pressed -> {
            if(pressed){
                controller.stop();
                System.exit(0);
            }
        }));
    }
}
