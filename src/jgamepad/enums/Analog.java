package jgamepad.enums;

/**
 * Enum representing the analog values that can be accessed using getAnalogValue
 */
public enum Analog {

    L2 (15),
    R2 (16),
    leftStickX (17),
    leftStickY (18),
    rightStickX (19),
    rightStickY (20);

    public int value;

    Analog(int value) {
        this.value = value;
    }
}
