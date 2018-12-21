package net.escendia.gui.model.font;

import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;
import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class MinecraftNormalFont extends net.escendia.gui.model.font.Font {

    @Override
    public double getStringWidth(String text, int size, Color color) {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    @Override
    public double getStringHeight(int size, Color color) {
        return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    }

    @Override
    public void drawString(String text, double y, double x, double size, Color color) {
        GL11.glPushMatrix();
        GL11.glScalef((float)size, (float)size, (float)size);
        Minecraft.getMinecraft().fontRenderer.drawString(text, (int)x, (int)y, color.getRGB());
        GL11.glPopMatrix();
    }
}