package net.escendia.gui.model.components;

public class Char {

    private GroupChar charGroup;
    private double cursorX;
    private double cursorY;
    private int lineIndex;
    private Char before;
    private Char after;
    private final String value;


    public Char(){value="";}

    public Char(char value, GroupChar charGroup) {
        this.value = String.valueOf(value);
        this.charGroup = charGroup;
    }

    public int getIntValue() {
        return (int) value.charAt(0);
    }

    public String getValue() {
        return value;
    }

    public Char getBefore() {
        return before;
    }

    public Char getAfter() {
        return after;
    }

    public double getCursorX() {
        return cursorX;
    }

    public double getCursorY() {
        return cursorY;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public GroupChar getCharGroup() {
        return charGroup;
    }

    public void setBefore(Char before) {
        this.before = before;
    }

    public void setAfter(Char after) {
        this.after = after;
    }

    public void setCursorX(double cursorX) {
        this.cursorX = cursorX;
    }

    public void setCursorY(double cursorY) {
        this.cursorY = cursorY;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public void setCharGroup(GroupChar charGroup) {
        this.charGroup = charGroup;
    }
}
