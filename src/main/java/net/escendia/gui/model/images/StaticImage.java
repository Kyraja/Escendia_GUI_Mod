package net.escendia.gui.model.images;

import org.newdawn.slick.opengl.Texture;

public class StaticImage extends Image {

    private final Texture texture;

    public StaticImage(Texture texture) {
        this.texture = texture;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
