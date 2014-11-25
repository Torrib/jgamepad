package jgamepad;

import com.sun.jna.Library;

public interface ControllerInterface extends Library {
    boolean initController();
    void startVibration(int controller, int leftMotor, int rightMotor);
    void stopVibration(int controller);
    void turnControllerOff(int controller); //Only works for xbox controllers
    void close(); //TODO
    void populateControllerData(int controller, int[] data);
    void getBatteryState(int controller); //TODO
}