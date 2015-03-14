package wiimote;

import com.sun.jna.Callback;
import jgamepad.listeners.ButtonPressedListener;

import java.util.*;

public class Wiimote implements Runnable{

    public static String DLL_PATH = "C:\\Users\\thb\\Documents\\Visual Studio 2013\\Projects\\wiimote\\Debug";
    public static int MAX_WIIMOTES = 4;

    public static final int WII_LED_1 = 0x10;
    public static final int WII_LED_2 = 0x20;
    public static final int WII_LED_3 = 0x40;
    public static final int WII_LED_4 = 0x80;

    private WiimoteInterface wi;
    private Map<Integer, List<WiimoteButtonPressedListener>> buttonPressedListeners = new HashMap<>();

    public Wiimote(){
        WiimoteInput.path = DLL_PATH;
        wi = WiimoteInput.getInterface();
        setEventCallback();
        initListeners();
        (new Thread(this)).start();
        System.out.println("Wiimote started");
    }

    public void run(){
        while(true){
            try {
                wi.updateWiimote();
                Thread.sleep(10);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public int connect(){
        return wi.connect();
    }

    public void setLeds(){
        wi.setLeds();
    }

    public void setLeds(int wiimote, int leds){
        wi.setCustomLeds(wiimote, leds);
    }

    public void startRumble(int wiimote){
        wi.startRumble(wiimote);
    }

    public void stopRumble(int wiimote){
        wi.stopRumble(wiimote);
    }

    //TODO default rumble, rumble with time

    public void status(int wiimote){
        wi.wiimoteStatus(wiimote);
    }

    public void addButtonListener(int wiimote, WiimoteButtonPressedListener listener){

        buttonPressedListeners.get(wiimote).add(listener);
        updateLibrary(wiimote);
    }

    public void removeButtonListener(int wiimote, WiimoteButtonPressedListener listener){
        buttonPressedListeners.get(wiimote).remove(listener);
        updateLibrary(wiimote);
    }

    public Nunchuck getNunchuckStatus(int wiimote){

        Nunchuck nunchuck = new Nunchuck();
        boolean nunchuckConnected = wi.nunchuckStatus(wiimote, nunchuck);
        if(!nunchuckConnected)
            System.out.println("Nunchucks not connected");
        return nunchuck;
    }






    private void updateLibrary(int wiimote){
        int[] buttons = getUniqueButtonValues(wiimote);
        wi.updateListeners(0, buttons, buttons.length);
    }

    private int[] getUniqueButtonValues(int wiimote){
        Set<Integer> hs = new HashSet();

        for(WiimoteButtonPressedListener listener : buttonPressedListeners.get(wiimote)){
            hs.add(listener.getButton().value);
        }

        int[] buttons = new int[hs.size()];

        int i = 0;

        for(Integer value : hs){
            buttons[i] = value;
            i++;
        }

        return buttons;
    }

    public int test(int x, int y){
        return wi.test(x, y);
    }


    private void setEventCallback(){
        wi.setEventCallback(new WiimoteInterface.eventCallback() {
            public boolean callback(int wiimote, int button, boolean pressed) {
                eventTriggered(wiimote, button, pressed);
                return true;
            }
        });
    }

    private void eventTriggered(int wiimote, int button, boolean pressed){
        for(WiimoteButtonPressedListener listener : buttonPressedListeners.get(wiimote)){
            if(listener.getButton().value == button){
                listener.run(pressed);
            }
        }
    }

    private void initListeners(){
        for(int i = 0; i < MAX_WIIMOTES; i++){
            buttonPressedListeners.put(i, new ArrayList<>());
        }
    }
}
