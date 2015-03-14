package examples;

import jgamepad.interfaces.ButtonPressedEvent;
import wiimote.Wiimote;
import wiimote.WiimoteButton;
import wiimote.WiimoteButtonPressedListener;

import java.lang.annotation.ElementType;

public class Test {

    public static void main(String[] args){

        Wiimote wiimote = new Wiimote();

        int t = wiimote.connect();
            wiimote.setLeds(0, Wiimote.WII_LED_2 ^ Wiimote.WII_LED_4);

            //        wiimote.startRumble(0);
            //
            //        try {
            //            Thread.sleep(1000);
            //        } catch (InterruptedException e) {
            //            e.printStackTrace();
            //        }
            //        wiimote.stopRumble(0);


            wiimote.addButtonListener(0, new WiimoteButtonPressedListener(WiimoteButton.A, new ButtonPressedEvent() {
                @Override
                public void run(boolean pressed) {
                    if (pressed) {
                        System.out.println("A pressed");
                    }
                    else{
                        System.out.println("A released");
                    }
                }
            }));

            wiimote.addButtonListener(0, new WiimoteButtonPressedListener(WiimoteButton.B, new ButtonPressedEvent() {
                @Override
                public void run(boolean pressed) {
                    if (pressed) {
                        System.out.println("C pressed");
                    }
                }
            }));

        while (true){
            System.out.println(wiimote.getNunchuckStatus(0));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        }
}