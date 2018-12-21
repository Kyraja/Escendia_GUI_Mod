package net.escendia.gui.model.form.style.impl;

import net.escendia.gui.model.form.Form;
import net.escendia.gui.model.form.style.FormStyle;
import net.escendia.gui.model.gui.DrawUtils;
import net.escendia.ioc.InversionOfControl;

public class Eclipse extends Form {

    public Eclipse(){
        super();
    }

    @Override
    public boolean isLocatedInside(double x, double y) {
        FormStyle formStyle = getFormStyle();

        double yAxis = ((y-formStyle.getYPosition())/formStyle.getHeight());
        double xAxis = ((x-formStyle.getXPosition())/formStyle.getWidth());

        return xAxis*xAxis + yAxis*yAxis <= 1;
    }

    @Override
    public void draw() {
        FormStyle formStyle = getFormStyle();
        DrawUtils drawUtils = InversionOfControl.get().build(DrawUtils.class);
        drawUtils.fillOval(formStyle.getXPosition(), formStyle.getYPosition(), formStyle.getWidth(), formStyle.getHeight(), formStyle.getBackgroundColor());
    }
}
