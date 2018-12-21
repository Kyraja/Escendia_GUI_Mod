package net.escendia.gui.model.form.style;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.escendia.gui.controll.FontService;
import net.escendia.gui.controll.GUIService;
import net.escendia.gui.controll.ImageService;
import net.escendia.gui.model.factories.FactoryElement;
import net.escendia.gui.model.font.Font;
import net.escendia.gui.model.gui.Screen;
import net.escendia.gui.model.images.Image;
import net.escendia.ioc.InversionOfControl;

import java.awt.*;

public class FormStyle implements FactoryElement<FormStyle> {

    private Margin margin = new Margin();
    private Border border = new Border();
    private Padding padding = new Padding();
    private double xPosition = 0;
    private double yPosition = 0;
    private double xPositionPercentage = 0;
    private double yPositionPercentage = 0;

    private double xOffset = 0;
    private double yOffset = 0;

    private double heightPercentage = 0;
    private double widthPercentage = 0;

    private double height = 0;
    private double width = 0;


    //---- Color -----
    private int backgroundColorRed = 119;
    private int backgroundColorGreen = 136;
    private int backgroundColorBlue = 153;
    private int backgroundColorAlpha = 255;

    private String backgroundImage;

    //-- For Text Elements --
    private int fontSize = 1;

    private double fontScale = 1.0;


    private int fontColorRed = 0;
    private int fontColorGreen= 0;
    private int fontColorBlue= 0;
    private int fontColorAlpha= 0;

    //private Font font;
    private String font = "minecraft normal";

    private Alignment alignment = Alignment.LEFT;
    private Alignment verticalAlignment = Alignment.TOP;

    private int cursorColorRed = 0;
    private int cursorColorGreen = 0;
    private int cursorColorBlue = 0;
    private int cursorColorAlpha = 0;

    public FormStyle(){
    }

    public FormStyle(double xPosition, double yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    /*
    -------- Getter Methods --------
     */

    public Margin getMargin() {
        return margin;
    }

    public Border getBorder() {
        return border;
    }

    public Padding getPadding() {
        return padding;
    }

    public double getXPosition() {
        double xPositionTemp = this.xPosition;
        if(xPositionPercentage!=0)xPositionTemp = InversionOfControl.get().build(Screen.class).getWidth()/100*xPositionPercentage/InversionOfControl.get().build(Screen.class).getScaleFactor();;
        xPositionTemp += xOffset;
        return xPositionTemp;
    }

    public double getYPosition() {
        double yPositionTemp = this.yPosition;
        if(yPositionPercentage!=0)yPositionTemp = InversionOfControl.get().build(Screen.class).getHeight()/100*yPositionPercentage/InversionOfControl.get().build(Screen.class).getScaleFactor();
        yPositionTemp += yOffset;
        return yPositionTemp;
    }

    public double getHeight() {
        double heightTemp = this.height;
        if(heightPercentage!=0)heightTemp = InversionOfControl.get().build(Screen.class).getHeight()/100*heightPercentage/InversionOfControl.get().build(Screen.class).getScaleFactor();
        return heightTemp;
    }

    public double getWidth() {
        double widthTemp = this.width;
        if(widthPercentage!=0)widthTemp = InversionOfControl.get().build(Screen.class).getWidth()/100*widthPercentage/InversionOfControl.get().build(Screen.class).getScaleFactor();;
        return widthTemp;
    }

    public Color getBackgroundColor() {
        return new Color(backgroundColorRed,backgroundColorGreen,backgroundColorBlue,backgroundColorAlpha);
    }

    public Image getBackgroundImage() {
        return InversionOfControl.get().build(ImageService.class).getImage(backgroundImage);
    }

    public Font getFont() {
        return font!=""?InversionOfControl.get().build(FontService.class).getFont(font):InversionOfControl.get().build(FontService.class).getFont("minecraft normal");
    }

    public int getFontSize() {
        return fontSize;
    }

    public Color getFontColor() {
        return new Color(fontColorRed,fontColorGreen,fontColorBlue,fontColorAlpha);
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Color getCursorColor() {
        return new Color(cursorColorRed,cursorColorGreen,cursorColorBlue,cursorColorAlpha);
    }

    /**
     * Vertical alignment for text
     * @return
     */
    public Alignment getVerticalAlignment() {
        return verticalAlignment;
    }

    /*
    -------- Setter Methods --------
     */

    public void setMargin(Margin margin) {
        this.margin = margin;
    }

    public void setBorder(Border border) {
        this.border = border;
    }

    public void setPadding(Padding padding) {
        this.padding = padding;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setBackgroundColor(int r, int g, int b, int a){
        this.backgroundColorRed = r;
        this.backgroundColorGreen = g;
        this.backgroundColorBlue = b;
        this.backgroundColorAlpha = a;
    }

    public void setBackgroundColor(Color backgroundColor) {
        setBackgroundColor(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), backgroundColor.getAlpha());
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontColor(int r, int g, int b, int a){
        this.fontColorRed = r;
        this.fontColorGreen = g;
        this.fontColorBlue = b;
        this.fontColorAlpha = a;
    }
    public void setFontColor(Color fontColor) {

        setBackgroundColor(fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue(), fontColor.getAlpha());
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setCursorColor(int r, int g, int b, int a){
        this.cursorColorRed = r;
        this.cursorColorGreen = g;
        this.cursorColorBlue = b;
        this.cursorColorAlpha = a;
    }

    public void setCursorColor(Color cursorColor){
        setBackgroundColor(cursorColor.getRed(), cursorColor.getGreen(), cursorColor.getBlue(), cursorColor.getAlpha());
    }

    /**
     * Set the font scale
     * @param fontScale
     */
    public void setFontScale(double fontScale) {
        this.fontScale = fontScale;
    }

    /**
     * Set the vertical alignment for texts
     * @param verticalAlignment
     */
    public void setVerticalAlignment(Alignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    /*
            ----- Logic Methods -------
             */
    public double getStringWidth(String string) {
        return getFont().getStringWidth(string, getFontSize(), getFontColor());
    }
    public Double getStringHeight() {
        return getFont().getStringHeight(getFontSize(), getFontColor());
    }


    @Override
    public JsonElement toJson() {
        return new GsonBuilder().create().toJsonTree(this);
    }

    @Override
    public FormStyle fromJson(String jsonString) {
        return new GsonBuilder().create().fromJson(jsonString, this.getClass());
    }

    /**
     * Return font scale to draw strings
     * @return
     */
    public double getFontScale() {
        return this.fontScale;
    }
}
