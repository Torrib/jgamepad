package jgamepad;

import jgamepad.enums.Analog;
import jgamepad.enums.Button;
import jgamepad.interfaces.ConnectionChangedEvent;
import jgamepad.interfaces.ButtonListener;
import jgamepad.interfaces.ControllerInterface;
import jgamepad.interfaces.MultipleButtonListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class representing a controller.
 * One controller class should be created for every physical controller used
 */
public class Controller implements Runnable{

    public static String dllPath = "";
    public static boolean debug = false;

    private int controller;
    private int pullDelay = 100;
    private boolean connected = false;
    private boolean run = false;
    private int[] data = new int[22];

    private ControllerInterface controllerInput;
    private List<ButtonListener> buttonListeners = new ArrayList<>();
    private List<MultipleButtonListener> multipleButtonsListeners = new ArrayList<>();
    private List<ConnectionChangedEvent> connectionChangedEvents = new ArrayList<>();

    /**
     * Creates an instance of the passed controller (0 - 3)
     * @param controller - Integer between 0 and 3
     */
    public Controller(int controller){
        init(controller);
    }

    /**
     * Creates an instance of the passed controller (0 - 3)
     * @param controller - Integer between 0 and 3
     * @param pullDelay - How often controller information will be pulled (Default 100ms)
     */
    public Controller(int controller, int pullDelay){
        this.pullDelay = pullDelay;
        init(controller);
    }

    /**
     * Starts the controller pulling
     */
    public void run(){
        run = true;
        while(run){
            try {
                controllerInput.populateControllerData(controller, data);
                checkConnection();
                checkListeners();
                Thread.sleep(pullDelay);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops the controller pulling
     */
    public void stop(){
        run = false;
    }

    /**
     * Adds a buttonListener to the controllers button listeners
     * @param buttonListener - ButtonHoldListener or ButtonPressedListener
     */
    public void addButtonListener(ButtonListener buttonListener){
        Logger.log("Added listener for: " + buttonListener.getButton());
        buttonListeners.add(buttonListener);
    }

    /**
     * Add a list of buttonListeners
     * @param buttonListenerList - List of button listeners
     */
    public void addButtonListener(List<ButtonListener> buttonListenerList){
        buttonListeners.addAll(buttonListenerList);
    }

    /**
     * Removes a buttonListener from the list of button listeners
     * @param buttonListener - ButtonListener object
     */
    public void removeButtonListener(ButtonListener buttonListener){
        Logger.log("Removed listener for: " + buttonListener.getButton());
        buttonListeners.remove(buttonListener);
    }

    /**
     * Removes all listeners for the Button
     * @param button - Button object
     */
    public void removeButtonListener(Button button){
        Logger.log("Removed listeners for: " + button);
        for(int i = 0; i < buttonListeners.size(); i++){
            if(button == buttonListeners.get(i).getButton()){
                buttonListeners.remove(i);
                i--;
            }
        }
    }

    /**
     * Removes all buttonListeners contained in the buttonListenerList
     * @param buttonListenerList - List containing ButtonListener objects
     */
    public void removeButtonListener(List<ButtonListener> buttonListenerList){
        buttonListeners.removeAll(buttonListenerList);
    }

    public void addButtonListener(MultipleButtonListener multipleButtonListener){
        multipleButtonsListeners.add(multipleButtonListener);
    }

    /**
     * Removes all ButtonListeners
     */
    public void clearButtonListeners(){
        Logger.log("Listeners cleared");
        buttonListeners.clear();
    }

    /**
     * Checks if the controller is connected
     * @return - Returns true if the controller is connected
     */
    public boolean connected(){
        return connected;
    }

    /**
     * Returns true if the passed Button is pressed
     * @param button - The button to check if it is pressed or not
     * @return boolean
     */
    public boolean getButtonValue(Button button){
        if(button == null){
            throw new NullPointerException("Button cannot be null");
        }
        return data[button.value] == 1;
    }

    /**
     * Returns a value for the analog elements on the controller
     * @param analog - The analog to get the value from
     * @return int
     */
    public int getAnalogValue(Analog analog){
        if(analog == null){
            throw new NullPointerException("Analog cannot be null");
        }
        return data[analog.value];
    }

    /**
     * Vibrate the controller for 500 ms
     */
    public void vibrate(){
        vibrate(20000, 20000, 500);
    }

    /**
     * Vibrate the controller
     * @param duration - The duration of the vibration (ms)
     */
    public void vibrate(int duration){
        vibrate(25000, 25000, duration);
    }

    /**
     * Vibrate the controller
     * @param leftMotor - The strength of the vibration (0 - 30000)
     * @param rightMotor - The strength of the vibration (0 - 30000)
     * @param duration - The duration of the vibration (ms)
     */
    public void vibrate(int leftMotor, int rightMotor, int duration){
        Logger.log("Controller " + controller + ": vibrating");
        (new Thread() {
            public void run() {
                try {
                    controllerInput.startVibration(controller, leftMotor, rightMotor);
                    Thread.sleep(duration);
                    controllerInput.stopVibration(controller);
                }
                catch (InterruptedException e){ e.printStackTrace();}
            }
        }).start();
    }

    /**
     * Turns off the controller
     * <b>Note: </b> Only works for Xbox360 controllers
     */
    public void turnOff() {
        Logger.log("Controller " + controller + ": turning off");
        controllerInput.turnControllerOff(controller);
    }

    /**
     * Set the pull delay(How often data is pulled from the controller)
     * @param pullDelay - Delay in ms (default: 100)
     */
    public void setPullDelay(int pullDelay) {
        this.pullDelay = pullDelay;
    }

    /**
     * Add an event that will trigger every time the controller connects or disconnects
     * @param connectionChangedEvent - ConnectionChangedEvent
     */
    public void addConnectionChangedEvent(ConnectionChangedEvent connectionChangedEvent) {
        Logger.log("ConnectionChangedEvent set for controller: " + controller);
        connectionChangedEvents.add(connectionChangedEvent);
    }

    /**
     * Removes the ConnectionChangedEvent from the list
     * @param connectionChangedEvent - ConnectionChangedEvent
     */
    public void removeConnectionChangedEvent(ConnectionChangedEvent connectionChangedEvent){
        connectionChangedEvents.remove(connectionChangedEvent);
    }

    /**
     * Removes all ConnectionChangedEvents
     */
    public void clearConnectionChangedEvents(){
        connectionChangedEvents.clear();
    }

    /**
     * Returns the controller number (0 - 3)
     * @return int
     */
    public int getControllerNumber(){
        return controller;
    }


    private void init(int controller){
        this.controller = controller;
        Logger.log("Initiating controller listener for controller nr: " + controller);
        ControllerInput.path = dllPath;
        controllerInput = ControllerInput.getInterface();

        (new Thread(this)).start();
    }

    private void checkListeners(){
        for (int i = 0; i < buttonListeners.size(); i++) {
            ButtonListener buttonListener = buttonListeners.get(i);
            if (data[buttonListener.getButton().value] != buttonListener.getState()) {
                buttonListener.swapState();
                buttonListener.run(data[buttonListener.getButton().value] == 1);
            }
        }

        for(int i = 0; i < multipleButtonsListeners.size(); i++){
            multipleButtonsListeners.get(i).checkState(data);
        }

    }

    private void checkConnection(){
        //Checks if the connection status have changed
        if((data[21] == 1) != connected){
            connected = (data[21] == 1);

            if(connected)
                Logger.log("Controller " + controller + ": Connected");
            else
                Logger.log("Controller " + controller + ": Disconnected");

            for(int i = 0; i < connectionChangedEvents.size(); i++){
                connectionChangedEvents.get(i).run(connected);
            }
        }
    }
}
