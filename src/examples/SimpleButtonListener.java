package examples;

import jgamepad.Controller;
import jgamepad.enums.Button;
import jgamepad.listeners.ButtonHoldListener;
import jgamepad.listeners.ButtonPressedListener;

class SimpleButtonListener {

    public static void main(String[] args) {
        Controller controller = new Controller(0);

        //Prints when A is pressed and released
        controller.addButtonListener(new ButtonPressedListener(Button.A, pressed -> {
            if(pressed){
                System.out.println("A pressed!");
            }
            else{
                System.out.println("A released");
            }
        }));

        //Prints when start is held for one second
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
