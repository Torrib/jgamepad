package jgamepad;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class ControllerStructure extends Structure {
    public int up, down, left, right, start, back;
    public int aButton, bButton, xButton, yButton, guideButton;
    public int l1, l2, l3, r1, r2, r3;
    public int leftStickY, leftStickX, rightStickY, rightStickX;

    public int[] getArray(){
        return new int[] {aButton, bButton, yButton, xButton, l1, r1, back, start, l3, r3 };
    }

    protected List getFieldOrder() {
        return Arrays.asList(new String[]{"up", "down", "left", "right", "start", "back",
                "aButton", "bButton", "xButton", "yButton", "guideButton",
                "l1", "l2", "l3", "r1", "r2", "r3",
                "leftStickY", "leftStickX", "rightStickY", "rightStickX"

        });
    }
}
