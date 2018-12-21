package net.escendia.gui.model.components.impl;

import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.listeners.OnClickListener;
import net.escendia.gui.model.listeners.OnCopyListener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class Paragraph extends Text implements ClipboardOwner {

    public Element buttonLineBefore;
    public Element buttonLineAfter;

    public Paragraph(String text, Element buttonLineAfter, Element buttonLineBefore){
        super(text);
        addChildrenElement(buttonLineAfter);
        addChildrenElement(buttonLineBefore);
        this.buttonLineAfter = buttonLineAfter;
        this.buttonLineBefore = buttonLineBefore;

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
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(getText()), clipboardOwner);
            }
        });
    }

    public Paragraph(){
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
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(getText()), clipboardOwner);
            }
        });
    }

    public Element getButtonLineAfter() {
        return buttonLineAfter;
    }

    public Element getButtonLineBefore() {
        return buttonLineBefore;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        onValueChange();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {}

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
    }
}
