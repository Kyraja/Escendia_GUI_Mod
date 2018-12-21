package net.escendia.gui.model.form.style;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.escendia.gui.model.factories.FactoryElement;

import java.awt.*;


public class Border implements FactoryElement<Border> {


    private double top;
    private double left;
    private double right;
    private double bottom;

    private int topColorRed = 0;
    private int topColorGreen = 0;
    private int topColorBlue = 0;
    private int topColorAlpha = 0;

    private int leftColorRed = 0;
    private int leftColorGreen = 0;
    private int leftColorBlue = 0;
    private int leftColorAlpha = 0;

    private int rightColorRed = 0;
    private int rightColorGreen = 0;
    private int rightColorBlue = 0;
    private int rightColorAlpha = 0;

    private int bottomColorRed = 0;
    private int bottomColorGreen = 0;
    private int bottomColorBlue = 0;
    private int bottomColorAlpha = 0;

    public Border(){
        this.top = 0;
        this.left = 0;
        this.right = 0;
        this.bottom = 0;
    }

    private double getTop() {
        return top;
    }

    private double getLeft() {
        return left;
    }

    private double getRight() {
        return right;
    }

    private double getBottom() {
        return bottom;
    }

    private Color getTopColor() {
        return new Color(topColorRed, topColorGreen, topColorBlue, topColorAlpha);
    }

    private Color getLeftColor() {
        return new Color(leftColorRed, leftColorGreen, leftColorBlue, leftColorAlpha);

    }

    private Color getRightColor() {
        return new Color(rightColorRed, rightColorGreen, rightColorBlue, rightColorAlpha);

    }

    private Color getBottomColor() {
        return new Color(bottomColorRed, bottomColorGreen, bottomColorBlue, bottomColorAlpha);

    }

    private void setTop(double top) {
        this.top = top;
    }

    private void setLeft(double left) {
        this.left = left;
    }

    private void setRight(double right) {
        this.right = right;
    }

    private void setBottom(double bottom) {
        this.bottom = bottom;
    }

    private void setTopColor(Color topColor) {
        this.topColorRed = topColor.getRed();
        this.topColorGreen = topColor.getGreen();
        this.topColorBlue = topColor.getBlue();
        this.topColorAlpha = topColor.getAlpha();

    }

    private void setLeftColor(Color leftColor) {
        this.leftColorRed = leftColor.getRed();
        this.leftColorGreen = leftColor.getGreen();
        this.leftColorBlue = leftColor.getBlue();
        this.leftColorAlpha = leftColor.getAlpha();
    }

    private void setRightColor(Color rightColor) {
        this.rightColorRed = rightColor.getRed();
        this.rightColorGreen = rightColor.getGreen();
        this.rightColorBlue = rightColor.getBlue();
        this.rightColorAlpha = rightColor.getAlpha();
    }

    private void setBottomColor(Color bottomColor) {
        this.bottomColorRed = bottomColor.getRed();
        this.bottomColorGreen = bottomColor.getGreen();
        this.bottomColorBlue = bottomColor.getBlue();
        this.bottomColorAlpha = bottomColor.getAlpha();
    }


    public void setBorderByType(BorderType borderType, double value){
        switch (borderType) {
            case LEFT:
                setLeft(value);
                break;
            case RIGHT:
                setRight(value);
                break;
            case TOP:
                setTop(value);
                break;
            case BOTTOM:
                setBottom(value);
                break;
            case ALL:
                setLeft(value);
                setRight(value);
                setTop(value);
                setBottom(value);
                break;
        }
    }


    public void setBorderColorByType(BorderType borderType, Color color){
        switch (borderType) {
            case LEFT:
                setLeftColor(color);
                break;
            case RIGHT:
                setRightColor(color);
                break;
            case TOP:
                setTopColor(color);
                break;
            case BOTTOM:
                setBottomColor(color);
                break;
            case ALL:
                setLeftColor(color);
                setRightColor(color);
                setTopColor(color);
                setBottomColor(color);
                break;
        }
    }

    public double getBorderByType(BorderType borderType) {
        switch (borderType){
            case LEFT:
                return getLeft();
            case RIGHT:
                return getRight();
            case TOP:
                return getTop();
            case BOTTOM:
                return getBottom();
        }
        return 0;
    }

    public Color getBorderColorByType(BorderType borderType){
        switch (borderType) {
            case LEFT:
                return getLeftColor();
            case RIGHT:
                return getRightColor();
            case TOP:
                return getTopColor();
            case BOTTOM:
                return getBottomColor();
        }
        return new Color(0,0,0,255);
    }

    @Override
    public JsonElement toJson() {
        return new GsonBuilder().create().toJsonTree(this);
    }

    @Override
    public Border fromJson(String jsonString) {
        return new GsonBuilder().create().fromJson(jsonString, this.getClass());
    }
}
