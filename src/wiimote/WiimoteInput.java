package wiimote;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import jgamepad.interfaces.ControllerInterface;

public class WiimoteInput {
    public static WiimoteInterface wi;

    public static String path = "";

    static WiimoteInterface getInterface() {
        if(wi != null)
            return wi;

        try {
            if(Platform.isWindows()) {
                initWindows();
            }
            else if(Platform.isLinux()){
                //TODO Linux support
            }
        } catch (UnsatisfiedLinkError ule) {
            Logger.log("Could not find the library", true);
            System.exit(1);
        } catch (Exception e) {
            Logger.log(e.toString(), true);
            Logger.log(e.getMessage(), true);
        }
        return wi;
    }

    private static void initWindows(){
        if (!path.endsWith("\\") && path != "")
            path += "\\";

        if (Platform.is64Bit()) {
            //TODO fix 64bit
            wi = (WiimoteInterface) Native.loadLibrary(path + "wiimote.dll", WiimoteInterface.class);
            Logger.log("Controller64.dll loaded");
        } else {
            wi = (WiimoteInterface) Native.loadLibrary(path + "wiimote.dll", WiimoteInterface.class);
            Logger.log("Controller.dll loaded");
        }
        Logger.log("Loading controller interface");

        wi.initWiimotes(4);
    }

}
