package net.escendia.gui.model.form.style.impl;

import net.escendia.gui.model.form.Form;
import net.escendia.gui.model.form.style.FormStyle;
import net.escendia.gui.model.gui.DrawUtils;
import net.escendia.ioc.InversionOfControl;

public class Polygon extends Form {


    private double minX = 0;
    private double maxX = 0;
    private double minY = 0;
    private double maxY = 0;
    private transient double positions[][] = new double[0][];


    public Polygon(){
        super();
    }

    @Override
    public boolean isLocatedInside(double x, double y) {
        if(minX <= x && maxX >= x && minY <= y && maxY >= y) {
            int intersect = 0;

            for (int i = 0; i < positions.length; i++)
                intersect += positionPerLine(x, y, positions[i], positions[(i + 1) % positions.length]) ? 1 : 0;

            return intersect % 2 == 1;
        }
        return false;
    }

    private boolean positionPerLine(double x, double y, double pos1[], double pos2[]){
        FormStyle formStyle = getFormStyle();
        double pos1X = pos1[0]+formStyle.getXPosition();
        double pos2X = pos2[0]+formStyle.getXPosition();
        double pos1Y = pos1[1]+formStyle.getYPosition();
        double pos2Y = pos2[1]+formStyle.getYPosition();

        double a = (pos2Y - pos1Y)/(pos2X - pos1X);
        double b = pos2Y - (a*pos2X);

        if(pos2Y < pos1Y){
            if(pos1X == pos2X)
                return pos1X <= x && (pos2Y+1 <= y && y <= pos1Y);
            else if(pos1X < pos2X)
                return !(y <= a*x+b) && (pos2Y+1 <= y && y <= pos1Y);
            else if(pos2X < pos1X)
                return !(y >= a*x+b) && (pos2Y+1 <= y && y <= pos1Y);
        }
        else if(pos1Y < pos2Y){
            if(pos1X == pos2X)
                return pos1X <= x && (pos1Y+1 <= y && y <= pos2Y);
            else if(pos1X < pos2X)
                return !(y >= a*x+b) && (pos1Y+1 <= y && y <= pos2Y);
            else if(pos2X < pos1X)
                return !(y <= a*x+b) && (pos1Y+1 <= y && y <= pos2Y);
        }

        return false;
    }

    public void setPositions(double[][] positions) {
        this.positions = positions;

        minX = Double.MIN_VALUE;
        maxX = Double.MAX_VALUE;
        minY = Double.MIN_VALUE;
        maxY = Double.MAX_VALUE;

        for (double position[]: positions){
            if(position[0] > maxX)
                maxX = position[0];
            else if(position[0] < minX)
                minX = position[0];

            if(position[0] > maxY)
                maxY = position[0];
            else if(position[0] < minY)
                minY = position[0];
        }
    }

    @Override
    public void draw() {
        FormStyle formStyle = getFormStyle();
        DrawUtils drawUtils = InversionOfControl.get().build(DrawUtils.class);
        drawUtils.fillCustomPolygon(formStyle.getXPosition(), formStyle.getYPosition(), positions, formStyle.getBackgroundColor());
    }
}
