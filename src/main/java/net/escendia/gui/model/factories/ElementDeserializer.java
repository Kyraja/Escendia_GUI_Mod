package net.escendia.gui.model.factories;

import com.google.gson.*;
import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.components.impl.*;

import java.lang.reflect.Type;

public class ElementDeserializer implements JsonDeserializer<Element> {
    @Override
    public Element deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement type = jsonObject.get("elementClass");
        if (type != null) {
            switch (type.getAsString()) {
                case "class net.escendia.gui.model.components.impl.Text":
                    return context.deserialize(jsonObject,
                            Text.class);
                case "class net.escendia.gui.model.components.impl.Div":
                    return context.deserialize(jsonObject,
                            Div.class);
                    case "class net.escendia.gui.model.components.impl.CheckBox":
                    return context.deserialize(jsonObject,
                            CheckBox.class);
                    case "class net.escendia.gui.model.components.impl.TextArea":
                    return context.deserialize(jsonObject,
                            TextArea.class);
                    case "class net.escendia.gui.model.components.impl.Paragraph":
                    return context.deserialize(jsonObject,
                            Paragraph.class);
                    case "class net.escendia.gui.model.components.impl.Progressbar$Vertical":
                    return context.deserialize(jsonObject,
                            Progressbar.Vertical.class);
                    case "class net.escendia.gui.model.components.impl.Progressbar$Horizontal":
                    return context.deserialize(jsonObject,
                            Progressbar.Horizontal.class);
                    case "class net.escendia.gui.model.components.impl.Slider":
                    return context.deserialize(jsonObject,
                            Slider.class);
            }
        }

        return null;
    }
}
