package net.escendia.gui.model.components.impl;

import net.escendia.gui.model.components.ElementValuable;
import net.escendia.gui.model.form.Form;
import net.escendia.gui.model.form.Rectangle;
import net.escendia.gui.model.form.style.impl.RectangleStandard;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;

public class Progressbar extends ElementValuable<Double> {

    public Form progressForm;
    public double barPercentage = 75;

    public Progressbar(){
        super();
        progressForm = new RectangleStandard();
    }

    public Form getProgressForm() {
        return progressForm;
    }

    public double getBarPercentage() {
        return barPercentage;
    }

    public void setBarPercentage(double barPercentage) {
        if(barPercentage>100)this.barPercentage = 100;
        this.barPercentage = barPercentage;
        onValueChange();
    }

    @Override
    public Double getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(Double value) {
        setBarPercentage(value);
    }

    public static class Horizontal extends Progressbar{
        public Horizontal(){
            super();
        }

        @Override
        public void setBarPercentage(double barPercentage) {
            if(getBarPercentage() != barPercentage) {
                super.setBarPercentage(barPercentage);
            }
        }

        @Override
        public void update() {
            getProgressForm().getFormStyle().setWidth((getForm().getFormStyle().getWidth()/100*barPercentage));
            super.update();

        }

        @Override
        public void draw() {
            InversionOfControl.get().build(EscendiaLogger.class).info("getWidth: " + getProgressForm().getFormStyle().getWidth());
            InversionOfControl.get().build(EscendiaLogger.class).info("getWidth: " + getBarPercentage());

            super.draw();
            getProgressForm().draw();
        }
    }

    public static class Vertical  extends Progressbar{
        public Vertical(){
            super();
        }

        @Override
        public void setBarPercentage(double barPercentage) {
            if(getBarPercentage() != barPercentage) {
                super.setBarPercentage(barPercentage);
            }
        }

        @Override
        public void update() {
            getProgressForm().getFormStyle().setHeight((getForm().getFormStyle().getHeight()/100*barPercentage));
            super.update();
        }

        @Override
        public void draw() {

            InversionOfControl.get().build(EscendiaLogger.class).info("getFormClass: " + progressForm.getFormClass());

            InversionOfControl.get().build(EscendiaLogger.class).info("getHeight: " + getProgressForm().getFormStyle().getHeight());
            InversionOfControl.get().build(EscendiaLogger.class).info("getBarPercentage: " + getBarPercentage());
            super.draw();
            InversionOfControl.get().build(EscendiaLogger.class).info("super.draw(): ");

            getProgressForm().draw();
        }
    }
}
