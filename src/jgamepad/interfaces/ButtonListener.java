package jgamepad.interfaces;

import jgamepad.enums.Button;

public interface ButtonListener {

    public void run();
    public void swapState();
    public Button getButton();
    public int getState();
}
