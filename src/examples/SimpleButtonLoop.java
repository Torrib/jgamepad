package examples;

import jgamepad.enums.Button;
import jgamepad.Controller;


/**
 * Simple loop that waits for A to be pressed and prints it out
 * Exits when the GUIDE button is pressed
 */
public class SimpleButtonLoop {

    public static void main(String[] args){
        Controller controller = new Controller(0);
        (new Thread(controller)).start();

        boolean keepRunning = true;

        while(keepRunning) {
            try {
                if(controller.getButtonValue(Button.A)){
                    System.out.println("A pressed!");
                }
                if(controller.getButtonValue(Button.GUIDE)){
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
