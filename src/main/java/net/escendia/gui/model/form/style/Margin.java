package net.escendia.gui.model.form.style;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.escendia.gui.model.factories.FactoryElement;

public class Margin implements FactoryElement<Margin> {

    private double top;
    private double left;
    private double right;
    private double bottom;

    public Margin(double top, double left, double right, double bottom){
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

    public Margin() {
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

    public void setMarginByType(MarginType margintype, double value){
        switch (margintype) {
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

    public double getMarginByType(MarginType marginType){
        switch (marginType) {
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

    @Override
    public JsonElement toJson() {
        return new GsonBuilder().create().toJsonTree(this);
    }

    @Override
    public Margin fromJson(String jsonString) {
        return new GsonBuilder().create().fromJson(jsonString, this.getClass());
    }
}
