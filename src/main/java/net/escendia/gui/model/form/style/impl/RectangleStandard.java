package net.escendia.gui.model.form.style.impl;

import net.escendia.gui.model.form.Rectangle;
import net.escendia.gui.model.form.style.*;
import net.escendia.gui.model.gui.DrawUtils;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;

public class RectangleStandard extends Rectangle {

    private final double borderTopPositions[][] = new double[4][2];
    private final double borderBottomPositions[][] = new double[4][2];
    private final double borderRightPositions[][] = new double[4][2];
    private final double borderLeftPositions[][] = new double[4][2];

    public RectangleStandard(){
        super();
    }

    public RectangleStandard(FormStyle formStyle){
        super(formStyle);
    }

    @Override
    public void draw() {
        FormStyle style = getFormStyle();
        Padding padding = style.getPadding();
        Border border = style.getBorder();

        borderTopPositions[0][0] = style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT)-border.getBorderByType(BorderType.LEFT);
        borderTopPositions[0][1] = style.getYPosition()-padding.getPaddingByType(PaddingType.TOP)-border.getBorderByType(BorderType.TOP);
        borderTopPositions[1][0] = style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT);
        borderTopPositions[1][1] = style.getYPosition()-padding.getPaddingByType(PaddingType.TOP);
        borderTopPositions[2][0] = style.getXPosition()+style.getWidth()+padding.getPaddingByType(PaddingType.RIGHT);
        borderTopPositions[2][1] = style.getYPosition()-padding.getPaddingByType(PaddingType.TOP);
        borderTopPositions[3][0] = style.getXPosition()+style.getWidth()+padding.getPaddingByType(PaddingType.RIGHT)+border.getBorderByType(BorderType.RIGHT);
        borderTopPositions[3][1] = style.getYPosition()-padding.getPaddingByType(PaddingType.TOP)-border.getBorderByType(BorderType.TOP);

        borderLeftPositions[0][0] = style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT)-border.getBorderByType(BorderType.LEFT);
        borderLeftPositions[0][1] = style.getYPosition()-padding.getPaddingByType(PaddingType.TOP)-border.getBorderByType(BorderType.TOP);
        borderLeftPositions[1][0] = style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT)-border.getBorderByType(BorderType.LEFT);
        borderLeftPositions[1][1] = style.getYPosition()+style.getHeight()+padding.getPaddingByType(PaddingType.BOTTOM)+border.getBorderByType(BorderType.BOTTOM);
        borderLeftPositions[2][0] = style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT);
        borderLeftPositions[2][1] = style.getYPosition()+style.getHeight()+padding.getPaddingByType(PaddingType.BOTTOM);
        borderLeftPositions[3][0] = style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT);
        borderLeftPositions[3][1] = style.getYPosition()-padding.getPaddingByType(PaddingType.TOP);

        borderRightPositions[0][0] = style.getXPosition()+style.getWidth()+padding.getPaddingByType(PaddingType.RIGHT)+border.getBorderByType(BorderType.RIGHT);
        borderRightPositions[0][1] = style.getYPosition()-padding.getPaddingByType(PaddingType.TOP)-border.getBorderByType(BorderType.TOP);
        borderRightPositions[1][0] = style.getXPosition()+style.getWidth()+padding.getPaddingByType(PaddingType.RIGHT);
        borderRightPositions[1][1] = style.getYPosition()-padding.getPaddingByType(PaddingType.TOP);
        borderRightPositions[2][0] = style.getXPosition()+style.getWidth()+padding.getPaddingByType(PaddingType.RIGHT);
        borderRightPositions[2][1] = style.getYPosition()+style.getHeight()+padding.getPaddingByType(PaddingType.BOTTOM);
        borderRightPositions[3][0] = style.getXPosition()+style.getWidth()+padding.getPaddingByType(PaddingType.RIGHT)+border.getBorderByType(BorderType.RIGHT);
        borderRightPositions[3][1] = style.getYPosition()+style.getHeight()+padding.getPaddingByType(PaddingType.BOTTOM)+border.getBorderByType(BorderType.BOTTOM);

        borderBottomPositions[0][0] = style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT);
        borderBottomPositions[0][1] = style.getYPosition()+style.getHeight()+padding.getPaddingByType(PaddingType.BOTTOM);
        borderBottomPositions[1][0] = style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT)-border.getBorderByType(BorderType.LEFT);
        borderBottomPositions[1][1] = style.getYPosition()+style.getHeight()+padding.getPaddingByType(PaddingType.BOTTOM)+border.getBorderByType(BorderType.BOTTOM);
        borderBottomPositions[2][0] = style.getXPosition()+style.getWidth()+padding.getPaddingByType(PaddingType.RIGHT)+border.getBorderByType(BorderType.RIGHT);
        borderBottomPositions[2][1] = style.getYPosition()+style.getHeight()+padding.getPaddingByType(PaddingType.BOTTOM)+border.getBorderByType(BorderType.BOTTOM);
        borderBottomPositions[3][0] = style.getXPosition()+style.getWidth()+padding.getPaddingByType(PaddingType.RIGHT);
        borderBottomPositions[3][1] = style.getYPosition()+style.getHeight()+padding.getPaddingByType(PaddingType.BOTTOM);

        DrawUtils drawUtils = InversionOfControl.get().build(DrawUtils.class);
        drawUtils.fillCustomPolygon(0,0, borderTopPositions, border.getBorderColorByType(BorderType.TOP));
        drawUtils.fillCustomPolygon(0,0, borderLeftPositions, border.getBorderColorByType(BorderType.LEFT));
        drawUtils.fillCustomPolygon(0,0, borderRightPositions, border.getBorderColorByType(BorderType.RIGHT));
        drawUtils.fillCustomPolygon(0,0, borderBottomPositions, border.getBorderColorByType(BorderType.BOTTOM));
        drawUtils.fillRectangle(style.getXPosition()-padding.getPaddingByType(PaddingType.LEFT),
                style.getYPosition()-padding.getPaddingByType(PaddingType.TOP),
                style.getWidth()+padding.getPaddingByType(PaddingType.LEFT)+padding.getPaddingByType(PaddingType.RIGHT),
                style.getHeight()+padding.getPaddingByType(PaddingType.TOP)+padding.getPaddingByType(PaddingType.BOTTOM),
                style.getBackgroundColor());
    }
}
