package examples;

import jgamepad.Analog;
import jgamepad.Button;
import jgamepad.Controller;

public class SimpleAnalogLoop {
    public static void main(String[] args) {
        Controller controller = new Controller(0);
        (new Thread(controller)).start();

        boolean keepRunning = true;


        while(keepRunning) {
            try {
                System.out.println("Left stick X: " + controller.getAnalogValue(Analog.leftStickX));
                System.out.println("Left stick Y: " + controller.getAnalogValue(Analog.leftStickY));
                System.out.println("Right stick X: " + controller.getAnalogValue(Analog.rightStickX));
                System.out.println("Right stick Y: " + controller.getAnalogValue(Analog.rightStickY));

                if(controller.getButtonValue(Button.guide)){
                    System.out.println("Exiting");
                    System.exit(0);
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
