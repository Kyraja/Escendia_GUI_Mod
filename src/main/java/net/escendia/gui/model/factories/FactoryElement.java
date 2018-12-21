package net.escendia.gui.model.factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface FactoryElement<T> {

    public JsonElement toJson();
    public T fromJson(String jsonString);


}
