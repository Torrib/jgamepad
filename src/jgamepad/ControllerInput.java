package jgamepad;


import com.sun.jna.Native;
import com.sun.jna.Platform;
import jgamepad.interfaces.ControllerInterface;

class ControllerInput {
    public static ControllerInterface ci;

    static {
        try {
            if (Platform.is64Bit()) {
                ci = (ControllerInterface) Native.loadLibrary("C:\\Users\\thb\\Downloads\\simpleDLL\\x64\\Release\\Controller.dll", ControllerInterface.class);
                Logger.log("Controller64.dll loaded");
            } else {
                ci = (ControllerInterface) Native.loadLibrary("C:\\Users\\thb\\Downloads\\simpleDLL\\x64\\Release\\Controller.dll", ControllerInterface.class);
                Logger.log("Controller.dll loaded");
            }
            Logger.log("Loading controller interface");

            boolean loaded = ci.initController();
            if(!loaded){
                Logger.log("Unable to load controller.dll");
                System.exit(1);
            }
        } catch (Exception e) {
            Logger.log(e.toString());
            Logger.log(e.getMessage());
        }
    }
}
