package net.escendia.gui.model.form;

import net.escendia.gui.model.form.style.*;

public class Rectangle extends Form {

    public Rectangle(){
        super();
    }

    public Rectangle(FormStyle formStyle){
        super(formStyle);
    }

    @Override
    public boolean isLocatedInside(double xPosition, double yPosition) {
        FormStyle style = getFormStyle();
        Border border = style.getBorder();
        Padding padding = style.getPadding();

        double minXPosition = style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT)-border.getBorderByType(BorderType.LEFT);
        double maxXPosition = style.getXPosition()+style.getWidth()+padding.getPaddingByType(PaddingType.RIGHT)-border.getBorderByType(BorderType.RIGHT);
        double minYPosition = style.getYPosition()-padding.getPaddingByType(PaddingType.TOP)-border.getBorderByType(BorderType.TOP);
        double maxYPosition = style.getYPosition()+style.getHeight()+padding.getPaddingByType(PaddingType.BOTTOM)-border.getBorderByType(BorderType.BOTTOM);

        return minXPosition <= xPosition && maxXPosition >= xPosition && minYPosition <= yPosition && maxYPosition >= yPosition;
    }

}
