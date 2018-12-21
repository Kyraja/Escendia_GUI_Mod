package net.escendia.gui.controll;

import net.escendia.gui.model.font.Font;
import net.escendia.gui.model.font.MinecraftNormalFont;
import net.escendia.gui.model.font.MinecraftShadowFont;
import net.escendia.gui.model.font.UnicodeFont;
import net.escendia.gui.model.gui.Screen;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Singleton
public class FontService extends FileService {


    private final HashMap<String, Font> fonts = new HashMap<String, Font>();
    private final ArrayList<FontData> fontDatas;

    public FontService() {
        super(new ArrayList<String>(Arrays.asList(new String[]{"application/zip", "application/x-zip"})));
        this.fontDatas = new ArrayList<>();
        addFont("minecraft normal", new MinecraftNormalFont());
        addFont("minecraft shadow", new MinecraftShadowFont());
    }

    public void generateFonts(Screen screen){
        for(FontData fontData : fontDatas){
            Font font = getFont(fontData.name);

            if(font != null && font instanceof UnicodeFont)
                ((UnicodeFont) font).generate(fontData.size, fontData.color);
            else{
                UnicodeFont unicodeFont = new UnicodeFont(screen, java.awt.Font.decode(fontData.name));
                unicodeFont.generate(fontData.size, fontData.color);
                addFont(fontData.name, unicodeFont);
            }
        }

        fontDatas.clear();
    }

    public void addFontToGenerate(String name, Color color, int size){
        fontDatas.add(new FontData(name, color, size));
    }

    public void addFont(String name, Font font){
        fonts.put(name.toLowerCase(), font);
    }

    public Font getFont(String name){
        Font font = fonts.get(name.toLowerCase());
        if(font==null) return fonts.get("minecraft normal");
            else return font;
    }

    public void downloadFont(final String fontUrl){
        if(!urlLoaded.contains(fontUrl)) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        InputStream is = getFile(fontUrl);
                        ZipInputStream zis = new ZipInputStream(is);

                        ZipEntry entry;

                        while ((entry = zis.getNextEntry()) != null) {
                            String fileName = entry.getName().toLowerCase();

                            if (fileName.endsWith(".ttf") || fileName.endsWith(".otf")) {
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                int i;

                                while ((i = zis.read()) != -1)
                                    baos.write(i);

                                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                                java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new ByteArrayInputStream(baos.toByteArray()));
                                ge.registerFont(font);
                            }
                        }
                        is.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    InversionOfControl.get().build(EscendiaLogger.class).info("Front from" + fontUrl + " downloaded...");

                    urlLoaded.add(fontUrl);
                }
            }).start();
        }
    }

    public HashMap<String, Font> getFonts() {
        return fonts;
    }

    private class FontData{

        private String name;
        private Color color;
        private int size;

        public FontData(String name, Color color, int size) {
            this.name = name;
            this.color = color;
            this.size = size;
        }
    }
}
