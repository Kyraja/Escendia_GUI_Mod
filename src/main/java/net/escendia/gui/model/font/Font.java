package net.escendia.gui.model.font;

import java.awt.*;

public class Font {
    public Font(){}

    public double getStringWidth(String text, int size, Color color){return 0;};
    public double getStringHeight(int size, Color color){return 0;};
    public void drawString(String text, double y, double x, double scalefactor, Color color){};
}
