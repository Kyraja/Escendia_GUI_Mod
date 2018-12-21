package net.escendia.gui.model.images;

import org.newdawn.slick.opengl.Texture;

public class Frame {

    private final long time;
    private final Texture texture;

    public Frame(long time, Texture texture) {
        this.time = time;
        this.texture = texture;
    }

    public long getTime() {
        return time;
    }

    public Texture getTexture() {
        return texture;
    }
}
