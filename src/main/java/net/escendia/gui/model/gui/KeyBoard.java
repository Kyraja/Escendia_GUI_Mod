package net.escendia.gui.model.gui;

import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.Inject;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@Singleton
public class KeyBoard {

    @Inject
    public EscendiaLogger logger;

    private final HashMap<Integer, KeyBoardListener> keyBoardListener;


    private static final long timeAfterFirstInput = 650;
    private static final long timeAfterSecondInput = 70;

    private static boolean keyPressed = false;
    private boolean secondInput = false;
    private long lastKeyInput = System.currentTimeMillis();
    private int lastInputKey = 0;
    private char input;

    public KeyBoard(){
        logger = InversionOfControl.get().build(EscendiaLogger.class);
        keyBoardListener = new HashMap<>();
        addKeyBoardListener(Keyboard.KEY_LEFT);
        addKeyBoardListener(Keyboard.KEY_UP);
        addKeyBoardListener(Keyboard.KEY_DOWN);
        addKeyBoardListener(Keyboard.KEY_RIGHT);
        addKeyBoardListener(Keyboard.KEY_LSHIFT);
        addKeyBoardListener(Keyboard.KEY_RSHIFT);
        addKeyBoardListener(Keyboard.KEY_INSERT);
        addKeyBoardListener(Keyboard.KEY_DELETE);
        addKeyBoardListener(Keyboard.KEY_LMENU);
        addKeyBoardListener(Keyboard.KEY_RMENU);
        addKeyBoardListener(Keyboard.KEY_LCONTROL);
        addKeyBoardListener(Keyboard.KEY_RCONTROL);
        addKeyBoardListener(Keyboard.KEY_ESCAPE);
        addKeyBoardListener(Keyboard.KEY_INSERT);
        addKeyBoardListener(Keyboard.KEY_N);

    }


    public HashMap<Integer, KeyBoardListener> getKeyBoardListener() {
        return keyBoardListener;
    }


    public KeyBoardListener getKeyListener(int key) {
        return keyBoardListener.get(key);
    }

    public void addKeyBoardListener(int key){
        keyBoardListener.put(key, new KeyBoardListener(key));
    }

    public void update(){
        logger.warn("KeyBoard - update() - Start - " + System.currentTimeMillis());
        boolean pressed = false;
        Keyboard.next();
        if(Keyboard.getEventKeyState()){
            int c =  Keyboard.getEventCharacter();

            if(c != 0){
                if(c != lastInputKey){
                    lastInputKey = c;
                    lastKeyInput = System.currentTimeMillis();
                    secondInput = false;

                    this.input = c >= 63000?0:(char) c;
                }
                else if(secondInput && System.currentTimeMillis() - lastKeyInput >= timeAfterSecondInput){
                    lastKeyInput = System.currentTimeMillis();

                    this.input = c >= 63000?0:(char) c;
                }
                else if(System.currentTimeMillis() - lastKeyInput >= timeAfterFirstInput){
                    lastKeyInput = System.currentTimeMillis();
                    secondInput = true;

                    this.input = c >= 63000?0:(char) c;
                }
                else
                    input =  0;
            }
            else {
                input =  0;
                lastInputKey = 0;
                lastKeyInput = System.currentTimeMillis();
                secondInput = false;
            }
        } else {
            input =  0;
            lastInputKey = 0;
            lastKeyInput = System.currentTimeMillis();
            secondInput = false;
        }


        keyPressed = false;

        for(KeyBoardListener keyListener : keyBoardListener.values()){
            pressed = keyListener.update(timeAfterFirstInput, timeAfterSecondInput);
            if(pressed)keyPressed = true;
        }
        logger.warn("KeyBoard - update() - Finish - " + System.currentTimeMillis());
    }

    public char getInput() {
        return input;
    }

    public static boolean isKeyPressed() {
        return keyPressed;
    }

    public ArrayList<Integer> getPressedKeys(){
        ArrayList<Integer> pressedKeyList = new ArrayList<>();
        for(KeyBoardListener keyBoardListener : keyBoardListener.values()){
            if(keyBoardListener.isPressed())pressedKeyList.add(keyBoardListener.getKey());
        }
        return pressedKeyList;
    }
}
