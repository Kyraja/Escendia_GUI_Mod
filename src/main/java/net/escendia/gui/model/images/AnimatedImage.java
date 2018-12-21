package net.escendia.gui.model.images;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;

public class AnimatedImage extends Image {

    private final ArrayList<Frame> frames;
    private long lastFrameUpdate = System.currentTimeMillis();
    private int frameIndex = 0;

    public AnimatedImage() {
        this.frames = new ArrayList<>();
    }

    public void addFrame(Frame frame){
        frames.add(frame);
    }

    @Override
    public Texture getTexture() {
        long time = System.currentTimeMillis();
        Frame frame = frames.get(frameIndex);

        if(frame.getTime()+lastFrameUpdate < time) {
            lastFrameUpdate = time;

            if (frameIndex + 1 < frames.size())
                frameIndex++;
            else
                frameIndex = 0;
        }

        return frames.get(frameIndex).getTexture();
    }
}
