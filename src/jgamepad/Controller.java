package jgamepad;


import java.util.ArrayList;
import java.util.List;

public class Controller implements Runnable{

    private int controller;
    private int pullRate = 100;
    private int connected;
    private boolean run = false;

    private ControllerInterface controllerInput;
    private List<ButtonListener> buttonListeners = new ArrayList<>();
    private int[] data = new int[22];
    private ConnectionChangedEvent connectionChangedEvent;

    public Controller(int controller){
        init(controller);
    }

    public Controller(int controller, int pullRate){
        this.pullRate = pullRate;
        init(controller);
    }

    public void run(){
        run = true;
        while(run){
            try {
                controllerInput.populateControllerData(controller, data);
                checkConnection();
                checkListeners();
                Thread.sleep(pullRate);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        run = false;
    }

    public void addButtonListener(ButtonListener buttonListener){
        Logger.log("Added listener for: " + buttonListener.getButton());
        buttonListeners.add(buttonListener);
    }

    public void addButtonListener(List<ButtonListener> buttonListenerList){
        buttonListeners.addAll(buttonListenerList);
    }

    public void removeButtonListener(ButtonListener buttonListener){
        Logger.log("Removed listener for: " + buttonListener.getButton());
        buttonListeners.remove(buttonListener);
    }

    public void removeButtonListener(int button){
        Logger.log("Removed listeners for: " + button);
        for(int i = 0; i < buttonListeners.size(); i++){
            if(button == buttonListeners.get(i).getButton()){
                buttonListeners.remove(i);
                i--;
            }
        }
    }

    public void removeButtonListener(List<ButtonListener> buttonListenerList){
        buttonListeners.removeAll(buttonListenerList);
    }

    public void clearListeners(){
        Logger.log("Listeners cleared");
        buttonListeners.clear();
    }

    public boolean connected(){
        return connected == 1;
    }

    public boolean getButtonValue(int button){
        return data[button] == 1;
    }

    public int getAnalogValue(int analog){
        return data[analog];
    }

    public void vibrate(){
        vibrate(20000, 20000, 500);
    }

    public void vibrate(int duration){
        vibrate(25000, 25000, duration);
    }

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

    public void turnOff(){
        Logger.log("Controller " + controller + ": turning off");
        controllerInput.turnControllerOff(controller);
    }

    public int getPullRate() {
        return pullRate;
    }

    public void setPullRate(int pullRate) {
        this.pullRate = pullRate;
    }

    public void setConnectionChangedEvent(ConnectionChangedEvent connectionChangedEvent) {
        Logger.log("ConnectionChangedEvent set for controller: " + controller);
        this.connectionChangedEvent = connectionChangedEvent;
    }

    public int getControllerNumber(){
        return controller;
    }

    private void init(int controller){
        Logger.log("Initiating controller listener for controller nr: " + controller);
        this.controller = controller;
        controllerInput = ControllerInput.ci;
    }

    private void checkListeners(){
        for(int i = 0; i < buttonListeners.size(); i++){
            ButtonListener buttonListener = buttonListeners.get(i);
            if (data[buttonListener.getButton()] != buttonListener.getState()) {
                buttonListener.swapState();
                buttonListener.run();
            }
        }
    }

    private void checkConnection(){
        if(data[21] != connected){
            connected = data[21];

            if(connected == 1)
                Logger.log("Controller " + controller + ": Connected");
            else
                Logger.log("Controller " + controller + ": Disconnected");

            if(connectionChangedEvent != null) {
                connectionChangedEvent.run(connected == 1);
            }
        }
    }


}
