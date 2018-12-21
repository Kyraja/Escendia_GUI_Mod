package net.escendia.gui.model.font;

import net.escendia.gui.model.gui.Screen;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.util.HashMap;

public class UnicodeFont extends Font {


    private final Screen screen;
    private final java.awt.Font font;
    private final HashMap<String, org.newdawn.slick.UnicodeFont[]> fonts;

    public UnicodeFont(Screen screen, java.awt.Font font) {
        this.screen = screen;
        this.font = font;
        this.fonts = new HashMap<>();
    }

    @Override
    public double getStringWidth(String text, int size, Color color) {
        org.newdawn.slick.UnicodeFont unicodeFonts[] = fonts.get(size+"-"+color.getRGB());

        if(unicodeFonts != null)
            return (double)unicodeFonts[screen.getScaleFactor()].getWidth(text)/(double)screen.getScaleFactor();
        else
            return 0;
    }

    @Override
    public double getStringHeight(int size, Color color) {
        org.newdawn.slick.UnicodeFont unicodeFonts[] = fonts.get(size+"-"+color.getRGB());

        if(unicodeFonts != null)
            return (double)unicodeFonts[screen.getScaleFactor()].getLineHeight()/(double)screen.getScaleFactor();
        else
            return 0;
    }

    @Override
    public void drawString(String text, double y, double x, double size, Color color) {
        org.newdawn.slick.UnicodeFont unicodeFonts[] = fonts.get(size+"-"+color.getRGB());

        if(unicodeFonts != null) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glScaled(1f / screen.getScaleFactor(), 1f / screen.getScaleFactor(), 1);
            unicodeFonts[screen.getScaleFactor()].drawString((float)(x * screen.getScaleFactor()), (float)(y * screen.getScaleFactor()), text);
            GL11.glScaled(1f * screen.getScaleFactor(), 1f * screen.getScaleFactor(), 1);
        }
    }

    public void generate(int size, Color color){
        if(fonts.get(size + "-" + color.getRGB()) == null) {
            org.newdawn.slick.UnicodeFont unicodeFonts[] = new org.newdawn.slick.UnicodeFont[5];

            for (int i = 1; i < 5; i++) {
                org.newdawn.slick.UnicodeFont unicodeFont = new org.newdawn.slick.UnicodeFont(font, size * i, false, false);

                unicodeFont.addAsciiGlyphs();
                unicodeFont.getEffects().add(new ColorEffect(color));
                unicodeFont.addGlyphs(200, 400);
                try {
                    unicodeFont.loadGlyphs();
                } catch (SlickException e) {
                    e.printStackTrace();
                }

                unicodeFonts[i] = unicodeFont;
            }

            fonts.put(size + "-" + color.getRGB(), unicodeFonts);
        }
    }
}
