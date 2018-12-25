package net.escendia.gui.model.gui;

import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;

@Singleton
public class Screen {


    private int scaleFactor;
    private int height;
    private int width;

    public Screen() {
        this.scaleFactor = 1;
        this.height = 0;
        this.width = 0;
    }

    public int getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(int scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setScreen(int width, int height, int scaleFactor) {
        this.width = width;
        this.height = height;
        this.scaleFactor = scaleFactor;
    }
}
