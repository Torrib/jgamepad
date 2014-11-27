package jgamepad.enums;

/**
 * Enum representing the buttons that can be used
 */
public enum Button {

    A (0),
    B (1),
    X (2),
    Y (3),
    L1 (4),
    R1 (5),
    START (6),
    BACK (7),
    L3 (8),
    R3 (9),
    GUIDE (10),
    DOWN (11),
    UP (12),
    LEFT (13),
    RIGHT (14);

    public int value;

    Button(int value){
        this.value = value;
    }
}
