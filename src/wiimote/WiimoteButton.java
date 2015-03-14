package wiimote;

public enum WiimoteButton{
    A (0x0008),
    B (0x0004),
    MINUS (0X0010),
    PLUS (0X1000),
    ONE (0x0002),
    TWO (0x0001),
    HOME (0x0080),
    LEFT (0x0100),
    RIGHT (0x0200),
    UP (0x0800),
    DOWN (0x0400),

    NUNCHUCK_C (0x10002),
    NUNCHUCK_Z (0x10001);

    public int value;

    WiimoteButton(int value){
        this.value = value;
    }
}
