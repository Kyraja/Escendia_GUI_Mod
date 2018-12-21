package net.escendia.gui.model.components.impl;


import net.escendia.gui.controll.KeyBoardService;
import net.escendia.gui.controll.MouseService;
import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.form.style.FormStyle;
import net.escendia.gui.model.gui.DrawUtils;
import net.escendia.gui.model.gui.KeyBoard;
import net.escendia.gui.model.gui.Mouse;
import net.escendia.gui.model.listeners.*;
import net.escendia.ioc.InversionOfControl;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class TextArea extends EditableText implements ClipboardOwner{


    private static final long textCursorVisibleTime = 1000;

    private Element buttonLineBefore;
    private Element buttonLineAfter;
    private long lastInputOrKeyPressed = System.currentTimeMillis();
    private boolean canUpdateText;

    public TextArea(){
        super("");
        this.buttonLineAfter.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(Element element) {
                showLineAfter();

            }
        });

        this.buttonLineBefore.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(Element element) {
                showLineBefore();
            }
        });

        this.addOnCopyListener(new OnCopyListener() {
            @Override
            public void onCopy(Element element) {
                ClipboardOwner clipboardOwner = (ClipboardOwner) element;
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), clipboardOwner);
            }
        });

        this.addOnKeyPressedListener(new OnKeyPressedListener() {
            @Override
            public void onKeyPressed(Element element, KeyBoard keyBoard) {
                lastInputOrKeyPressed = System.currentTimeMillis();

                if (keyBoard.getKeyListener(Keyboard.KEY_LEFT).isPressed())
                    moveCursorLeft();
                else if (keyBoard.getKeyListener(Keyboard.KEY_RIGHT).isPressed())
                    moveCursorRight();
                else if (keyBoard.getKeyListener(Keyboard.KEY_UP).isPressed())
                    moveCursorUp();
                else if (keyBoard.getKeyListener(Keyboard.KEY_DOWN).isPressed())
                    moveCursorDown();
                else if (keyBoard.getKeyListener(Keyboard.KEY_DELETE).isPressed())
                    deleteNextChar();
            }
        });

        this.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(Element element) {
                Mouse mouse = InversionOfControl.get().build(MouseService.class).getMouse();
                FormStyle formStyle = getForm().getFormStyle();
                lastInputOrKeyPressed = System.currentTimeMillis();

                if (mouse.getMouseXPosition() >= formStyle.getXPosition() && mouse.getMouseXPosition() <= formStyle.getXPosition() + formStyle.getWidth() && mouse.getMouseYPosition() >= formStyle.getYPosition() && mouse.getMouseYPosition() <= formStyle.getYPosition() + formStyle.getHeight())
                    setCursorLocation(mouse.getMouseXPosition() - formStyle.getXPosition(), mouse.getMouseYPosition() + formStyle.getStringHeight() * getStartLineToShow() - formStyle.getYPosition());
                else
                    setCursorLocation(mouse.getMouseXPosition() - formStyle.getXPosition(), formStyle.getStringHeight() * getStartLineToShow());
            }
        });

        this.addOnInputListener(new OnInputListener() {
            @Override
            public void onInputListener(Element element, char input) {
                lastInputOrKeyPressed = System.currentTimeMillis();

                switch (input) {
                    case 8:
                        deleteChar();
                        break;
                    case 9:
                        addInput((char) 32);
                        addInput((char) 32);
                        addInput((char) 32);
                        addInput((char) 32);
                        break;
                    case 13:
                        addInput(input);
                        break;
                    default:
                        if (input >= 32)
                            addInput(input);
                        break;
                }
            }
        });

        this.addOnPasteListener(new OnPasteListener() {
            @Override
            public void onPaste(Element element) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);
                boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
                String result = "";

                if (hasTransferableText) {
                    try {
                        result = (String) contents.getTransferData(DataFlavor.stringFlavor);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        ex.printStackTrace();
                    }
                }

                setText(getText() + result);
            }
        });
    }

    public TextArea(String text, Element buttonLineBefore, Element buttonLineAfter){
        super(text);
        this.add(buttonLineAfter);
        this.add(buttonLineBefore);
        this.add(buttonLineBefore);
        this.add(buttonLineAfter);
        this.buttonLineBefore = buttonLineBefore;
        this.buttonLineAfter = buttonLineAfter;

        this.buttonLineAfter.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(Element element) {
                showLineAfter();
            }
        });

        this.buttonLineBefore.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(Element element) {
                showLineBefore();
            }
        });

        this.addOnCopyListener(new OnCopyListener() {
            @Override
            public void onCopy(Element element) {
                ClipboardOwner clipboardOwner = (ClipboardOwner) element;
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), clipboardOwner);
            }
        });

        this.addOnKeyPressedListener(new OnKeyPressedListener() {
            @Override
            public void onKeyPressed(Element element, KeyBoard keyBoard) {
                lastInputOrKeyPressed = System.currentTimeMillis();

                if (keyBoard.getKeyListener(Keyboard.KEY_LEFT).isPressed())
                    moveCursorLeft();
                else if (keyBoard.getKeyListener(Keyboard.KEY_RIGHT).isPressed())
                    moveCursorRight();
                else if (keyBoard.getKeyListener(Keyboard.KEY_UP).isPressed())
                    moveCursorUp();
                else if (keyBoard.getKeyListener(Keyboard.KEY_DOWN).isPressed())
                    moveCursorDown();
                else if (keyBoard.getKeyListener(Keyboard.KEY_DELETE).isPressed())
                    deleteNextChar();
            }
        });

        this.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(Element element) {
                Mouse mouse = InversionOfControl.get().build(MouseService.class).getMouse();
                FormStyle formStyle = getForm().getFormStyle();
                lastInputOrKeyPressed = System.currentTimeMillis();

                if (mouse.getMouseXPosition() >= formStyle.getXPosition() && mouse.getMouseXPosition() <= formStyle.getXPosition() + formStyle.getWidth() && mouse.getMouseYPosition() >= formStyle.getYPosition() && mouse.getMouseYPosition() <= formStyle.getYPosition() + formStyle.getHeight())
                    setCursorLocation(mouse.getMouseXPosition() - formStyle.getXPosition(), mouse.getMouseYPosition() + formStyle.getStringHeight() * getStartLineToShow() - formStyle.getYPosition());
                else
                    setCursorLocation(mouse.getMouseXPosition() - formStyle.getXPosition(), formStyle.getStringHeight() * getStartLineToShow());
            }
        });

        this.addOnInputListener(new OnInputListener() {
            @Override
            public void onInputListener(Element element, char input) {
                lastInputOrKeyPressed = System.currentTimeMillis();

                switch (input) {
                    case 8:
                        deleteChar();
                        break;
                    case 9:
                        addInput((char) 32);
                        addInput((char) 32);
                        addInput((char) 32);
                        addInput((char) 32);
                        break;
                    case 13:
                        addInput(input);
                        break;
                    default:
                        if (input >= 32)
                            addInput(input);
                        break;
                }
            }
        });

        this.addOnPasteListener(new OnPasteListener() {
            @Override
            public void onPaste(Element element) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);
                boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
                String result = "";

                if (hasTransferableText) {
                    try {
                        result = (String) contents.getTransferData(DataFlavor.stringFlavor);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        ex.printStackTrace();
                    }
                }

                setText(getText() + result);
            }
        });

    }




    @Override
    public void update() {
        super.update();
        canUpdateText = getForm().getFormStyle().getFont() != null && getForm().getFormStyle().getStringHeight() != null;

        if(canUpdateText){
            if(isTextUpdated())super.onValueChange();
        }
    }

    public void add(Element element){
        if(buttonLineAfter != element && buttonLineBefore != element)
            super.addChildrenElement(element);
    }

    @Override
    public void draw() {
        super.draw();

        if(canUpdateText) {
            if (InversionOfControl.get().build(KeyBoardService.class).getKeyBoard() != null) {
                long time = System.currentTimeMillis();

                if (lastInputOrKeyPressed + textCursorVisibleTime >= time || time % textCursorVisibleTime * 2 <= textCursorVisibleTime){
                    FormStyle formStyle = getForm().getFormStyle();
                    DrawUtils drawUtils = InversionOfControl.get().build(DrawUtils.class);
                    drawUtils.fillRectangle(formStyle.getXPosition() + getCursorX(), formStyle.getYPosition()+ getCursorY() + formStyle.getStringHeight() * - getStartLineToShow(), .5, formStyle.getStringHeight(), formStyle.getCursorColor());
                }
            }
        }
    }

    @Override
    public String getValue() {
        return getText();
    }

    @Override
    public void setValue(String value) {
        setText(value);
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {}
}
