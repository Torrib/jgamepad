package jgamepad.enums;

/**
 * Enum representing the analog values that can be accessed using getAnalogValue
 */
public enum Analog {

    leftStickY (15),
    leftStickX (16),
    L2 (17),
    rightStickY (18),
    rightStickX (19),
    R2 (20);

    public int value;

    Analog(int value) {
        this.value = value;
    }
}
