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

    //Analog information might not be zero when the sticks are not moved.
    //This creates a buffer to filter out those values
    private static final int IGNORE_ZONE = 5000;

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
            e.printStackTrace();
            System.exit(1);
        }

        while(keepRunning) {
            try {
                //Pulls the left analog stick's X and Y values
                x = controller.getAnalogValue(Analog.leftStickX);
                y = controller.getAnalogValue(Analog.leftStickY);

                //Calculate the amount of movement for both X and Y
                if(x > IGNORE_ZONE || x < -IGNORE_ZONE)
                    x /= speedFactor;
                else
                    x = 0;
                if(y > IGNORE_ZONE || y < -IGNORE_ZONE)
                    y /= speedFactor;
                else
                    y = 0;

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
