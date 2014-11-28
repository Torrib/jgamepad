package examples;

import jgamepad.Controller;
import jgamepad.enums.Button;
import jgamepad.listeners.ButtonPressedListener;

public class ConnectionChanged {

    public static void main(String[] args) {
        Controller controller = new Controller(0);

        controller.addConnectionChangedEvent(connected ->{
            if(connected){
                System.out.println("Controller 1 has connected");
            }
            else{
                System.out.println("Pause the game!!! controller disconnected");
            }
        });

        controller.addButtonListener(new ButtonPressedListener(Button.GUIDE, pressed -> {
            controller.stop();
            System.exit(0);
        }));
    }
}
