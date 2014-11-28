package examples;

import jgamepad.Controller;
import jgamepad.enums.Button;
import jgamepad.interfaces.ButtonPressedEvent;
import jgamepad.listeners.ButtonHoldListener;
import jgamepad.listeners.ButtonPressedListener;

public class Test {

    public static void main(String[] args){
        Controller controller = new Controller(0);

        controller.addButtonListener(new ButtonPressedListener(Button.A, new ButtonPressedEvent() {
            @Override
            public void run(boolean pressed) {
                System.out.println("A pressed");
            }
        }));

        controller.addButtonListener(new ButtonHoldListener(Button.A, 1000, new Runnable() {
            @Override
            public void run() {
                System.out.println("A held!");
            }
        }));
    }
}
