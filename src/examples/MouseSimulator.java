package examples;

import jgamepad.Controller;
import jgamepad.enums.Analog;
import jgamepad.enums.Button;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.MouseInfo;

class MouseSimulator {

    private static final int IGNORE_ZONE = 5000;

    public static void main(String[] args){
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
                x = controller.getAnalogValue(Analog.leftStickX);
                y = controller.getAnalogValue(Analog.leftStickY);

                if(x > IGNORE_ZONE || x < -IGNORE_ZONE)
                    x /= speedFactor;
                else
                    x = 0;
                if(y > IGNORE_ZONE || y < -IGNORE_ZONE)
                    y /= speedFactor;
                else
                    y = 0;

                if(y != 0 || x != 0){
                    Point point = MouseInfo.getPointerInfo().getLocation();

                    robot.mouseMove((int) point.getX() + x, (int) point.getY() - y);
                }

                if(controller.getButtonValue(Button.GUIDE))
                    keepRunning = false;


                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.stop();
        System.exit(0);
    }
}
