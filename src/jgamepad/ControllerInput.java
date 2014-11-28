package jgamepad;


import com.sun.jna.Native;
import com.sun.jna.Platform;
import jgamepad.interfaces.ControllerInterface;

import java.util.concurrent.atomic.AtomicReference;

class ControllerInput {
    public static ControllerInterface ci;

    public static String path = "";

    static ControllerInterface getInterface() {
        if(ci != null)
            return ci;

        try {
            if(!path.endsWith("\\") && path != "")
                path += "\\";

            if (Platform.is64Bit()) {
                ci = (ControllerInterface) Native.loadLibrary(path + "Controller.dll", ControllerInterface.class); //TODO 64
                Logger.log("Controller64.dll loaded");
            } else {
                ci = (ControllerInterface) Native.loadLibrary(path + "Controller.dll", ControllerInterface.class);
                Logger.log("Controller.dll loaded");
            }
            Logger.log("Loading controller interface");

            boolean loaded = ci.initController();
            if (!loaded) {
                Logger.log("Unable to load controller.dll");
                System.exit(1);
            }
        } catch (UnsatisfiedLinkError ule) {
            Logger.log("Could not find Controller.dll");
            System.exit(1);
        } catch (Exception e) {
            Logger.log(e.toString());
            Logger.log(e.getMessage());
        }
        return ci;
    }
}
