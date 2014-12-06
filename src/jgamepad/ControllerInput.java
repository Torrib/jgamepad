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
            if(Platform.isWindows()) {
                initWindows();
            }
            else if(Platform.isLinux()){
                initLinux();
            }
        } catch (UnsatisfiedLinkError ule) {
            Logger.log("Could not find the library", true);
            System.exit(1);
        } catch (Exception e) {
            Logger.log(e.toString(), true);
            Logger.log(e.getMessage(), true);
        }
        return ci;
    }

    private static void initWindows(){
        if (!path.endsWith("\\") && path != "")
            path += "\\";

        if (Platform.is64Bit()) {
            ci = (ControllerInterface) Native.loadLibrary(path + "Controller64.dll", ControllerInterface.class);
            Logger.log("Controller64.dll loaded");
        } else {
            ci = (ControllerInterface) Native.loadLibrary(path + "Controller.dll", ControllerInterface.class);
            Logger.log("Controller.dll loaded");
        }
        Logger.log("Loading controller interface");

        boolean loaded = ci.initController();
        if (!loaded) {
            Logger.log("Unable to load controller.dll", true);
            System.exit(1);
        }
    }

    private static void initLinux(){
//        if (!path.endsWith("\\") && path != "")
//            path += "\\";

        if (Platform.is64Bit()) {
            ci = (ControllerInterface) Native.loadLibrary(path + "test", ControllerInterface.class);
            Logger.log("Library loaded");
        } else {
            ci = (ControllerInterface) Native.loadLibrary(path + "test", ControllerInterface.class);
            Logger.log("Library loaded");
        }
        Logger.log("Loading controller interface");

        boolean loaded = ci.initController();
        if (!loaded) {
            Logger.log("Unable to load library", true);
            System.exit(1);
        }
    }
}
