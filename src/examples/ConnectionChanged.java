package examples;

import jgamepad.Controller;
import jgamepad.enums.Button;
import jgamepad.listeners.ButtonPressedListener;

/**
 * Prints out every time controller 0 connects or disconnects
 */
public class ConnectionChanged {

    public static void main(String[] args) {

        //Sets the path to controller.dll directory
        Controller.dllPath = System.getProperty("user.dir") + "\\release";

        //Creates an instance of the first controller (0)
        Controller controller = new Controller(0);

        //Adds an connection changed event to the controller
        controller.addConnectionChangedEvent(connected ->{
            if(connected){
                System.out.println("Controller 1 has connected");
            }
            else{
                System.out.println("Pause the game!!! controller disconnected");
            }
        });

        //Adds a listener to the GUIDE button stopping the controller and exiting the program when pressed
        controller.addButtonListener(new ButtonPressedListener(Button.GUIDE, pressed -> {
            controller.stop();
            System.exit(0);
        }));
    }
}
