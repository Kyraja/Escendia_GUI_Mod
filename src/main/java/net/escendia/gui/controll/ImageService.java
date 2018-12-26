package net.escendia.gui.controll;

import net.escendia.gui.model.GlobalScope;
import net.escendia.gui.model.images.*;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Singleton
public class ImageService extends FileService {

    private final HashMap<String, Image> images;
    private final ArrayList<ImageData> imagesData;
    private final EscendiaLogger logger;
    public ImageService() {
        super(new ArrayList<String>(Arrays.asList(new String[]{"image/gif", "image/jpeg", "image/png"})));
        logger = InversionOfControl.get().build(EscendiaLogger.class);
        images = new HashMap<>();
        imagesData = new ArrayList<>();
    }

    public void addImage(String name, Image image){
        images.put(name.toLowerCase(), image);
    }

    public void addImageData(ImageData imageData){
        this.imagesData.add(imageData);
    }

    public Image getImage(String name){
        if(imagesData.size()>0)generateImages();
        return images.get(name.toLowerCase());
    }

    public void generateImages(){
        for(ImageData imageData : imagesData) {
            try{
                if (imageData.getExtension().endsWith("jpg")) {
                    Texture texture = TextureLoader.getTexture("JPG", new ByteArrayInputStream(imageData.frames.get(0).bytes));
                    addImage(imageData.getName(), new StaticImage(texture));
                } else if (imageData.getExtension().endsWith("png")) {
                    Texture texture = TextureLoader.getTexture("PNG", new ByteArrayInputStream(imageData.frames.get(0).bytes));
                    addImage(imageData.getName(), new StaticImage(texture));
                } else if (imageData.getExtension().endsWith("gif")) {
                        AnimatedImage gifImage = new AnimatedImage();

                        for (int i = 0; i < imageData.frames.size(); i++) {
                            ImageData.Frame frame = imageData.frames.get(i);

                            Texture texture = TextureLoader.getTexture("GIF", new ByteArrayInputStream(frame.bytes));
                            gifImage.addFrame(new Frame(frame.time, texture));
                        }
                        addImage(imageData.getName(), gifImage);
                }
            }catch(Exception ex){
                logger.info(ex.getMessage());
            }
        }
        logger.debug("generateImages() " + images);
        imagesData.clear();
    }

    public void downloadImage(final String imageUrl, final String name){
        if(!urlLoaded.contains(imageUrl)) {
                    try {
                        InputStream is = getFile(imageUrl);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        int b;

                        while ((b = is.read()) != -1)
                            baos.write(b);

                        convertImage(name, baos.toByteArray());


                        baos.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    InversionOfControl.get().build(EscendiaLogger.class).info("Image " + name + " from " + imageUrl + " downloaded...");
                    urlLoaded.add(imageUrl);
                }
    }

    public void convertImage(String filename, byte[] bytes){
        String extension = "jpg";

        if(filename.contains("."))extension = filename.substring(filename.lastIndexOf(".") + 1);

        if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png")) {
            ImageData imageData = new ImageData(extension, filename);
            imageData.frames.add(new ImageData.Frame(0, bytes));

            InversionOfControl.get().build(ImageService.class).addImageData(imageData);

        } else if (extension.equalsIgnoreCase("gif")) {
            try {
                ImageData imageData = new ImageData(extension, filename);
                GifDecoder.GifImage gifImage = GifDecoder.read(bytes);

                for (int b = 0; b < gifImage.getFrameCount(); b++) {
                    BufferedImage bufferedImage = gifImage.getFrame(b);

                    ByteArrayOutputStream baosImage = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "GIF", baosImage);

                    imageData.frames.add(new ImageData.Frame(gifImage.getDelay(b) * 10, baosImage.toByteArray()));

                    baosImage.close();
                }

                InversionOfControl.get().build(ImageService.class).addImageData(imageData);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class ImageData{

        private final String extension;
        private final String name;
        private final ArrayList<Frame> frames;

        public ImageData(String extension, String name) {
            this.extension = extension;
            this.name = name;
            this.frames = new ArrayList<>();
        }

        public String getExtension() {
            return extension;
        }

        public String getName() {
            return name;
        }

        public ArrayList<Frame> getFrames() {
            return frames;
        }

        private static class Frame{

            private final long time;
            private final byte[] bytes;

            public Frame(long time, byte[] bytes) {
                this.time = time;
                this.bytes = bytes;
            }
        }

    }

}
