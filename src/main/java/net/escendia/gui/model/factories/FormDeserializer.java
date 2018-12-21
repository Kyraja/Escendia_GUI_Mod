package net.escendia.gui.model.factories;

import com.google.gson.*;
import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.components.impl.*;
import net.escendia.gui.model.form.Form;
import net.escendia.gui.model.form.style.impl.Eclipse;
import net.escendia.gui.model.form.style.impl.Polygon;
import net.escendia.gui.model.form.style.impl.RectangleImage;
import net.escendia.gui.model.form.style.impl.RectangleStandard;

import java.lang.reflect.Type;

public class FormDeserializer implements JsonDeserializer<Form> {
    @Override
    public Form deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement type = jsonObject.get("formClass");
        if (type != null) {
            switch (type.getAsString()) {
                case "class net.escendia.gui.model.form.style.impl.RectangleStandard":
                    return context.deserialize(jsonObject,
                            RectangleStandard.class);
                case "class net.escendia.gui.model.form.style.impl.RectangleImage":
                    return context.deserialize(jsonObject,
                            RectangleImage.class);
                case "class net.escendia.gui.model.form.style.impl.Polygon":
                    return context.deserialize(jsonObject,
                            Polygon.class);
                case "class net.escendia.gui.model.form.style.impl.Eclipse":
                    return context.deserialize(jsonObject,
                            Eclipse.class);

            }
        }
        return null;
    }
}
