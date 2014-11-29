package examples;

import jgamepad.enums.Analog;
import jgamepad.enums.Button;
import jgamepad.Controller;

/**
 * Simple class that prints out the analog sticks movement
 */
class SimpleAnalogLoop {
    public static void main(String[] args) {

        //Sets the path to controller.dll directory
        Controller.dllPath = System.getProperty("user.dir") + "\\release";

        Controller controller = new Controller(0);

        boolean keepRunning = true;

        while(keepRunning) {
            try {
                System.out.println("Left stick X: " + controller.getAnalogValue(Analog.leftStickX));
                System.out.println("Left stick Y: " + controller.getAnalogValue(Analog.leftStickY));
                System.out.println("Right stick X: " + controller.getAnalogValue(Analog.rightStickX));
                System.out.println("Right stick Y: " + controller.getAnalogValue(Analog.rightStickY));

                if(controller.getButtonValue(Button.GUIDE)){
                    System.out.println("Exiting");
                    controller.stop();
                    System.exit(0);
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
