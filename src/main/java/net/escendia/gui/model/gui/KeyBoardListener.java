package net.escendia.gui.model.gui;

import org.lwjgl.input.Keyboard;

public class KeyBoardListener {

    private final int key;
    private long lastInputTime = System.currentTimeMillis();
    private boolean secondInput = false;
    private boolean pressed = false;
    private boolean down = false;
    private boolean downLastUpdate = false;

    public KeyBoardListener(int key) {
        this.key = key;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isDown() {
        return down;
    }

    public boolean update(long afterFirstInput, long afterSecondInput) {

        if (Keyboard.isKeyDown(key)) {
            downLastUpdate = down;
            down = true;

            if (!downLastUpdate) {
                lastInputTime = System.currentTimeMillis();
                secondInput = false;

                pressed = true;
            } else if (secondInput && System.currentTimeMillis() - lastInputTime >= afterSecondInput) {
                lastInputTime = System.currentTimeMillis();

                pressed = true;
            } else if (System.currentTimeMillis() - lastInputTime >= afterFirstInput) {
                lastInputTime = System.currentTimeMillis();
                secondInput = true;

                pressed = true;
            } else
                pressed = false;
        } else {
            down = false;
            pressed = false;
            lastInputTime = System.currentTimeMillis();
            secondInput = false;
        }
        return pressed;
    }

    public Integer getKey() {
        return this.key;
    }
}

