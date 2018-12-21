package net.escendia.gui.model.gui;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.escendia.gui.model.factories.FactoryElement;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneralGUIData implements FactoryElement<GeneralGUIData> {

    public ArrayList<String> fonts = new ArrayList<>();
    public HashMap<String, String> images = new HashMap<>();

    public HashMap<String, String> getImages() {
        return images;
    }

    public ArrayList<String> getFonts() {
        return fonts;
    }

    public void setImages(HashMap<String, String> images) {
        this.images = images;
    }

    public void setFonts(ArrayList<String> fonts) {
        this.fonts = fonts;
    }

    public GeneralGUIData(){
        //TODO setfonts etc
    }

    @Override
    public JsonElement toJson() {
        return new GsonBuilder().create().toJsonTree(this);
    }

    @Override
    public GeneralGUIData fromJson(String jsonString) {
        return new GsonBuilder().create().fromJson(jsonString, this.getClass());
    }
}
