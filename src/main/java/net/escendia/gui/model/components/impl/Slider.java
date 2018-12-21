package net.escendia.gui.model.components.impl;

import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.components.ElementValuable;
import net.escendia.gui.model.form.Form;

public class Slider extends ElementValuable<Double> {

    private double relativeButton;
    private Progressbar progressbar;
    private Element button;


    public Slider(){
        super();
    }

    public Slider(Form form, Progressbar progressbar, Element button){
        super(form);
        this.relativeButton = 0;
        this.progressbar = progressbar;
        this.addChildrenElement(progressbar);
        progressbar.getForm().getFormStyle().setWidth(form.getFormStyle().getWidth());
        progressbar.getForm().getFormStyle().setHeight(form.getFormStyle().getHeight());
        this.button = button;
    }

    public void setBarPercentage(double percentage){
        progressbar.setBarPercentage(percentage);
        onValueChange();
    }

    public double getBarPercentage() {
        return progressbar.getBarPercentage();
    }

    @Override
    public Double getValue() {
        return getBarPercentage();
    }

    @Override
    public void setValue(Double value) {
        setBarPercentage(value);
    }
}
