package net.escendia.gui.model.components.impl;

import net.escendia.gui.model.components.Char;
import net.escendia.gui.model.components.ElementValuable;
import net.escendia.gui.model.components.GroupChar;
import net.escendia.gui.model.components.TextLine;
import net.escendia.gui.model.font.Font;
import net.escendia.gui.model.form.style.FormStyle;
import net.escendia.gui.model.gui.Screen;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;

public class Text extends ElementValuable<String> {


    private transient ArrayList<TextLine> textLines = new ArrayList<>();
    private transient ArrayList<TextLine> visibleTextLines = new ArrayList<>();

    private transient ArrayList<ArrayList<Char>> linesChar = new ArrayList<>();
    private transient ArrayList<GroupChar> groupChars = new ArrayList<>();

    public String text = "";

    /*
    Temp Variables
     */
    private transient boolean textUpdated;
    private transient double widthLastUpdate;
    private transient Font fontLastUpdate;
    private transient Char cursorLocation;
    private transient int startLineToShow = 0;
    private transient int linesCountToShow = Integer.MAX_VALUE;
    private transient boolean updateText = false;
    private transient String value;

    public Text(){
        super();
    }

    public Text(String text){
        super();
        this.setText(text);
    }

    public String getText() {
        return text;
    }

    public int getStartLineToShow() {
        return startLineToShow;
    }

    public double getCursorX(){
        if(cursorLocation != null)
            return textLines.get(cursorLocation.getLineIndex()).getxOffset()+cursorLocation.getCursorX();
        else
            return 0;
    }

    public double getCursorY(){
        if(cursorLocation != null)
            return cursorLocation.getCursorY();
        else
            return 0;
    }

    public boolean isTextUpdated() {
        return textUpdated;
    }

    public ArrayList<TextLine> getVisibleTextLines() {
        return visibleTextLines;
    }

    public ArrayList<TextLine> getTextLines() {
        return textLines;
    }

    /*
    ------- Cursor Methods ---------
     */

    public void setCursorLocation(double x, double y){
        double lineHeight = getForm().getFormStyle().getStringHeight();
        int lineIndex = (int) (y/lineHeight);
        int firstLineIndex = textLines.indexOf(visibleTextLines.get(0));

        if(lineIndex >= 0 && lineIndex - firstLineIndex <= linesCountToShow) {
            ArrayList<Char> charLine;
            String currentLine = "";

            if (lineIndex >= textLines.size())
                charLine = linesChar.get(textLines.size() - 1);
            else
                charLine = linesChar.get(lineIndex);

            for (Char c : charLine) {
                if(c.getIntValue() != 13 && c.getIntValue() != 10) {
                    currentLine = currentLine + c.getValue();

                    if (getForm().getFormStyle().getStringWidth(currentLine) >= x) {
                        cursorLocation = c;
                        return;
                    }
                }
            }

            if(charLine.size() != 0)
                cursorLocation = charLine.get(charLine.size() - 1);
            else
                cursorLocation = null;
        }
    }

    public void moveCursorUp(){
        int cursorLineBefore = getCursorLine();

        if(cursorLocation != null)
            setCursorLocation(getCursorX(), getCursorY()-5);

        if(cursorLineBefore != getCursorLine())
            if(getCursorLine() < startLineToShow)
                showLineBefore();
    }

    public void moveCursorDown(){
        int cursorLineBefore = getCursorLine();

        if(cursorLocation != null)
            setCursorLocation(getCursorX(), getCursorY()+getForm().getFormStyle().getStringHeight()+5);

        if(cursorLineBefore != getCursorLine())
            if(getCursorLine() > startLineToShow+linesCountToShow)
                showLineAfter();
    }

    public void moveCursorLeft(){
        int cursorLineBefore = getCursorLine();

        if(cursorLocation != null)
            cursorLocation = cursorLocation.getBefore();

        if(cursorLineBefore != getCursorLine())
            if(getCursorLine() < startLineToShow)
                showLineBefore();
    }

    public void moveCursorRight(){
        int cursorLineBefore = getCursorLine();

        if(cursorLocation != null && cursorLocation.getAfter() != null)
            cursorLocation = cursorLocation.getAfter();
        else if(cursorLocation == null)
            if(groupChars.size() != 0 && linesChar.get(0).size() != 0 && linesChar.get(0) != null && linesChar.get(0).get(0) != null)
                cursorLocation = linesChar.get(0).get(0);

        if(cursorLineBefore != getCursorLine())
            if(getCursorLine() > startLineToShow+linesCountToShow)
                showLineAfter();
    }

    public void deleteNextChar(){
        if(cursorLocation != null) {
            if (cursorLocation.getAfter() != null) {
                Char c = cursorLocation.getAfter();
                c.getCharGroup().remove(c);

                if (c.getCharGroup().size() == 0)
                    groupChars.remove(c);

                updateText = true;
            }
        }
        else if(groupChars.size() != 0){
            for(int i = 0; i < linesChar.size(); i++) {
                if (linesChar.get(i).size() != 0 && linesChar.get(i) != null && linesChar.get(i).get(0) != null) {
                    Char c = linesChar.get(i).get(0);

                    if (c != null) {
                        c.getCharGroup().remove(c);

                        if (c.getCharGroup().size() == 0)
                            groupChars.remove(c);

                        updateText = true;
                        return;
                    }
                }
            }
        }
    }

    public void deleteChar(){
        if(cursorLocation != null){
            Char c = cursorLocation;
            c.getCharGroup().remove(c);

            if(c.getCharGroup().size() == 0)
                groupChars.remove(c);

            cursorLocation = c.getBefore();
            updateText = true;
        }
    }

    public void addInput(char input){
        if(cursorLocation != null){
            GroupChar currentGroup = cursorLocation.getCharGroup();

            if(input > 32) {
                if (currentGroup.isWordGroup()) {
                    Char c = new Char(input, currentGroup);
                    c.setLineIndex(currentGroup.get(currentGroup.indexOf(cursorLocation)).getLineIndex());

                    currentGroup.add(currentGroup.indexOf(cursorLocation) + 1, c);
                    cursorLocation = c;
                    updateText = true;
                } else if (currentGroup.getIndex() + 1 != groupChars.size() && groupChars.get(currentGroup.getIndex() + 1).isWordGroup()) {
                    GroupChar nextGroup = groupChars.get(currentGroup.getIndex() + 1);
                    Char c = new Char(input, nextGroup);
                    c.setLineIndex(currentGroup.get(0).getLineIndex());

                    nextGroup.add(0, c);
                    cursorLocation = c;
                    updateText = true;
                }
                else{
                    GroupChar groupChar = new GroupChar();
                    groupChars.add(currentGroup.getIndex()+1, groupChar);
                    Char c = new Char(input, groupChar);
                    c.setLineIndex(currentGroup.get(currentGroup.size()-1).getLineIndex());

                    groupChar.add(c);
                    cursorLocation = c;
                    updateText = true;
                }
            }
            else{
                if(currentGroup.isWordGroup()){
                    if(cursorLocation.getAfter() != null && cursorLocation.getAfter().getCharGroup() == currentGroup){
                        int index = currentGroup.indexOf(cursorLocation);
                        GroupChar groupChar = new GroupChar();

                        for(int i = 0; i <= index; i++){
                            groupChar.add(currentGroup.get(i));
                            currentGroup.get(i).setCharGroup(groupChar);
                        }

                        while(index-- != -1)
                            currentGroup.remove(0);

                        groupChars.add(currentGroup.getIndex(), groupChar);

                        GroupChar groupCharCharAdded = new GroupChar();
                        groupCharCharAdded.setWordGroup(false);
                        groupChars.add(currentGroup.getIndex()+1, groupCharCharAdded);
                        Char c = new Char(input, groupCharCharAdded);
                        c.setLineIndex(currentGroup.get(currentGroup.size()-1).getLineIndex());

                        groupCharCharAdded.add(c);
                        cursorLocation = c;
                        updateText = true;
                    }
                    else {
                        GroupChar groupChar = new GroupChar();
                        groupChar.setWordGroup(false);
                        groupChars.add(currentGroup.getIndex()+1, groupChar);
                        Char c = new Char(input, groupChar);
                        c.setLineIndex(currentGroup.get(currentGroup.size()-1).getLineIndex());

                        groupChar.add(c);
                        cursorLocation = c;
                        updateText = true;
                    }
                }
                else{
                    GroupChar groupChar = new GroupChar();
                    groupChar.setWordGroup(false);
                    groupChars.add(currentGroup.getIndex()+1, groupChar);
                    Char c = new Char(input, groupChar);
                    c.setLineIndex(currentGroup.get(currentGroup.size()-1).getLineIndex());

                    groupChar.add(c);
                    cursorLocation = c;
                    updateText = true;
                }
            }
        }
        else if(groupChars.size() != 0 && linesChar.get(0).size() != 0 && linesChar.get(0) != null && linesChar.get(0).get(0) != null){
            GroupChar groupChar = linesChar.get(0).get(0).getCharGroup();

            if(groupChar.isWordGroup()) {
                Char c = new Char(input, groupChar);

                groupChar.add(0, c);
                cursorLocation = c;
                updateText = true;
            }
            else{
                groupChar = new GroupChar();
                groupChar.setWordGroup(input > 32);
                groupChar.setWordGroup(input > 32);
                groupChars.add(0, groupChar);
                Char c = new Char(input, groupChar);

                groupChar.add(c);
                cursorLocation = c;
                updateText = true;
            }
        }
        else
            setText(String.valueOf(input));
    }

    /*
    --------- Logic Methods --------
     */


    public void setText(String text){
        startLineToShow = 0;
        ArrayList<String> words = split(text);
        groupChars.clear();

        for(String word : words) {
            if(word.charAt(0) > 32) {
                GroupChar groupChar = new GroupChar();

                for (char c : word.toCharArray()) {
                    Char ch = new Char(c, groupChar);

                    cursorLocation = ch;

                    groupChar.add(ch);
                }

                groupChars.add(groupChar);
            }
            else{
                GroupChar groupChar = new GroupChar();
                groupChar.setWordGroup(false);
                Char ch = new Char(word.charAt(0), groupChar);

                cursorLocation = ch;

                groupChar.add(ch);

                groupChars.add(groupChar);
            }
        }
        updateText = true;
    }



    private ArrayList<String> split(String text){
        ArrayList<String> words = new ArrayList<String>();
        String word = "";

        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == 13 || text.charAt(i) == 10) {
                if(!word.equals("")) {
                    words.add(word);
                    word = "";
                }
                words.add((char) 10+"");
            }
            else if(text.charAt(i) == 32) {
                if(!word.equals("")) {
                    words.add(word);
                    word = "";
                }
                words.add(" ");
            }
            else
                word += text.charAt(i);

            if(i+1 == text.length() && text.charAt(i) != 32 && text.charAt(i) != 13)
                words.add(word);
        }

        return words;
    }

    @Override
    public void update() {
        if(!this.text.equalsIgnoreCase(""))this.setText(text);
        super.update();
        FormStyle formStyle = getForm().getFormStyle();

        textUpdated = false;

        if(textLines.size() == 0)
            textLines.add(new TextLine());

        if(widthLastUpdate != formStyle.getWidth() || updateText){
            widthLastUpdate = formStyle.getWidth();
            updateText = true;
        }

        if(fontLastUpdate != formStyle.getFont() || updateText){
            fontLastUpdate = formStyle.getFont();
            updateText = true;
        }

        updateGroupChars();

        if(updateText){
            textLines.clear();
            linesChar.clear();
            ArrayList<Char> chars = new ArrayList<>();
            int cursorLineBeforeUpdate = getCursorLine();

            updateLines();
            text = "";

            for(TextLine textLine : textLines)
                text += textLine.getLineText();

            for(ArrayList<Char> charsLine : linesChar)
                chars.addAll(charsLine);

            for(int i = 0; i < chars.size(); i++){
                Char c = chars.get(i);

                if(i == 0)
                    c.setBefore(null);
                else
                    c.setBefore(chars.get(i-1));

                if(i+1 == chars.size())
                    c.setAfter(null);
                else
                    c.setAfter(chars.get(i+1));
            }

            if(cursorLineBeforeUpdate != getCursorLine()){
                if(getCursorLine() < startLineToShow)
                    showLineBefore();
                else if(getCursorLine() > startLineToShow+linesCountToShow)
                    showLineAfter();
            }

            while(startLineToShow >= textLines.size() && startLineToShow != 0)
                startLineToShow--;

            updateVisibleLines();

            formStyle.setHeight(visibleTextLines.size()*formStyle.getStringHeight());

            updateText = false;
            textUpdated = true;
        }
    }



    private void updateGroupChars(){
        for(GroupChar groupChar : groupChars)
            groupChar.update(getForm().getFormStyle());
    }

    public int getCursorLine(){
        return cursorLocation != null?cursorLocation.getLineIndex():0;
    }

    private void updateLines() {
        FormStyle formStyle = getForm().getFormStyle();
        double lineHeight = formStyle.getStringHeight();
        String currentLine = "";
        int lineIndex = 0;
        ArrayList<Char> currentCharLine = new ArrayList<>();

        for(int g = 0; g < groupChars.size(); g++) {
            GroupChar groupChar = groupChars.get(g);
            groupChar.setIndex(g);
            if(formStyle.getWidth() < groupChar.getWidth() || groupChar.size() == 1){
                for (int i = 0; i < groupChar.size(); i++) {
                    if(groupChar.get(i).getIntValue() == 10 || groupChar.get(i).getIntValue() == 13){
                        linesChar.add(currentCharLine);
                        addLine(currentLine);
                        lineIndex++;
                        currentLine = "";
                        currentCharLine = new ArrayList<>();
                        currentCharLine.add(groupChar.get(i));
                        groupChar.get(i).setCursorY(textLines.size()*lineHeight);
                        groupChar.get(i).setCursorX(0);
                        groupChar.get(i).setLineIndex(lineIndex);
                    }
                    else if(formStyle.getStringWidth(currentLine + groupChar.get(i).getValue()) <= formStyle.getWidth()){
                        currentLine += groupChar.get(i).getValue();
                        currentCharLine.add(groupChar.get(i));
                        groupChar.get(i).setCursorY(textLines.size()*lineHeight);
                        groupChar.get(i).setCursorX(formStyle.getStringWidth(currentLine));
                        groupChar.get(i).setLineIndex(lineIndex);
                    }
                    else{
                        linesChar.add(currentCharLine);
                        addLine(currentLine);
                        lineIndex++;
                        currentLine = groupChar.get(i).getValue();
                        currentCharLine = new ArrayList<>();
                        currentCharLine.add(groupChar.get(i));
                        groupChar.get(i).setCursorY(textLines.size()*lineHeight);
                        groupChar.get(i).setCursorX(formStyle.getStringWidth(currentLine));
                        groupChar.get(i).setLineIndex(lineIndex);
                    }
                }
            }
            else if(formStyle.getStringWidth(currentLine)+groupChar.getWidth() <= formStyle.getWidth()){
                for (Char c : groupChar) {
                    currentLine += c.getValue();
                    currentCharLine.add(c);
                    c.setCursorY(textLines.size()*lineHeight);
                    c.setCursorX(formStyle.getStringWidth(currentLine));
                    c.setLineIndex(lineIndex);
                }
            }
            else{
                addLine(currentLine);
                linesChar.add(currentCharLine);
                lineIndex++;
                currentCharLine = new ArrayList<>();
                currentLine = "";

                for (Char c : groupChar) {
                    currentLine += c.getValue();
                    currentCharLine.add(c);
                    c.setCursorY(textLines.size()*lineHeight);
                    c.setCursorX(formStyle.getStringWidth(currentLine));
                    c.setLineIndex(lineIndex);
                }
            }
        }
        linesChar.add(currentCharLine);
        addLine(currentLine);
    }

    private void addLine(String lineString){
        textLines.add(new TextLine(lineString, getXOffset(getForm().getFormStyle().getStringWidth(lineString))));
    }

    private double getXOffset(double textWidth) {
        switch (getForm().getFormStyle().getAlignment()){
            case LEFT: return 0;
            case CENTER: return ((getForm().getFormStyle().getWidth()/getForm().getFormStyle().getFontScale() - textWidth)/2);
            case RIGHT: return (getForm().getFormStyle().getWidth()/getForm().getFormStyle().getFontScale() - textWidth);
        }

        return 0;
    }


    private double getYOffset(double stringHeight, int lineAmount) {


        double textHeight = stringHeight * lineAmount;

        switch (getForm().getFormStyle().getVerticalAlignment()){
            case TOP: return 0;
            case CENTER: return ((getForm().getFormStyle().getHeight()/getForm().getFormStyle().getFontScale() - textHeight)/2);
            case BOTTOM: return (getForm().getFormStyle().getHeight()/getForm().getFormStyle().getFontScale() - textHeight);
        }

        return 0;
    }


    public void showLineBefore() {
        if(startLineToShow != 0) {
            startLineToShow--;
            updateVisibleLines();
        }
    }

    public void showLineAfter() {
        if(startLineToShow != 0) {
            startLineToShow--;
            updateVisibleLines();
        }
    }

    private void updateVisibleLines(){
        visibleTextLines = new ArrayList<>();
        int nbLineToRender = startLineToShow+linesCountToShow >= textLines.size()? textLines.size()-1: startLineToShow+linesCountToShow;
        for(int i = startLineToShow; i <= nbLineToRender; i++)
            visibleTextLines.add(textLines.get(i));
    }

    @Override
    public void draw() {
        //InversionOfControl.get().build(EscendiaLogger.class).info("Draw() - " + getElementUUID() + " " + getText());
        super.draw();

        FormStyle formStyle = getForm().getFormStyle();
        ArrayList<TextLine> textLines = visibleTextLines;
        Font font = formStyle.getFont();
        Color fontColor = formStyle.getFontColor();

        double stringHeight = formStyle.getStringHeight()/formStyle.getFontScale();

        double yPos = formStyle.getYPosition()/formStyle.getFontScale() + getYOffset(formStyle.getStringHeight(), textLines.size());
        double xPos = formStyle.getXPosition()/formStyle.getFontScale();

        for(int i = 0; i < textLines.size(); i++) {
            TextLine textLine = textLines.get(i);
            font.drawString(textLine.getLineText(), yPos + stringHeight * i , xPos +  textLine.getxOffset(), formStyle.getFontScale(), fontColor);
        }
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
