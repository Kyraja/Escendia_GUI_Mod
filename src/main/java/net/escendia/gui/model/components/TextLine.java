package net.escendia.gui.model.components;

public class TextLine {


    private String lineText = "";
    private double xOffset = 0;

    public TextLine(String lineText, double xOffset) {
        this.lineText = lineText;
        this.xOffset = xOffset;
    }

    public TextLine() {}


    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public String getLineText() {
        return lineText;
    }

    public double getxOffset() {
        return this.xOffset;
    }
}
