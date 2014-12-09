package examples;

import jgamepad.Controller;
import jgamepad.enums.Analog;
import jgamepad.enums.Button;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.MouseInfo;

/**
 * Pulls information from the analog sticks and moves the mouse pointer accordingly
 */
class MouseSimulator {

    //This creates a buffer to filter out those values

    public static void main(String[] args){

        //Sets the path to controller.dll directory
        Controller.dllPath = System.getProperty("user.dir") + "\\release";

        Controller controller = new Controller(0);

        int speedFactor = 1200;
        int x;
        int y;
        boolean keepRunning = true;

        Robot robot = null;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println("Unable to load Robot - Exiting");
            e.printStackTrace();
            System.exit(1);
        }

        while(keepRunning) {
            try {
                //Pulls the left analog stick's X and Y values
                x = controller.getAnalogValue(Analog.leftStickX, true);
                y = controller.getAnalogValue(Analog.leftStickY, true);

                //Calculate the amount of movement for both X and Y
                x /= speedFactor;
                y /= speedFactor;

                //Move the mouse
                if(y != 0 || x != 0){
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    robot.mouseMove((int) point.getX() + x, (int) point.getY() - y);
                }

                //Pulls the button value for GUIDE and exits when it is pressed
                if(controller.getButtonValue(Button.GUIDE))
                    keepRunning = false;

                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Stops the controller and exits
        controller.stop();
        System.exit(0);
    }
}
