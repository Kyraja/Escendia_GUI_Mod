package net.escendia.gui.model.factories;

import net.escendia.gui.model.listeners.*;

public class ModelFactory {

//    public static Element elementFromJson(String jsonObject) {
//
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Element.class, new ElementDeserializer())
//                .registerTypeAdapter(Form.class, new FormDeserializer())
//                .create();
//
//        return gson.fromJson(jsonObject, Element.class);
//    }
//
//        public static Form getFormByClass(String className, String jsonStyle) {
//        String st = RectangleStandard.class.toString();
//        if (className.equalsIgnoreCase(RectangleStandard.class.toString())) return new RectangleStandard().fromJson(jsonStyle);
//        else if (className.equalsIgnoreCase(RectangleImage.class.toString())) return new RectangleImage().fromJson(jsonStyle);
//        else if (className.equalsIgnoreCase(Polygon.class.toString())) return new Polygon().fromJson(jsonStyle);
//        else if (className.equalsIgnoreCase(Eclipse.class.toString())) return new Eclipse().fromJson(jsonStyle);
//        return null;
//    }
//
    public static Class eventFromString(String eventName){
        if(eventName==OnHoverListener.class.toString())return OnHoverListener.class;
        else if(eventName==OnHoverListener.class.toString())return OnClickListener.class;
        else if(eventName==OnCopyListener.class.toString())return OnCopyListener.class;
        else if(eventName==OnDoubleClickListener.class.toString())return OnDoubleClickListener.class;
        else if(eventName==OnFocusListener.class.toString())return OnFocusListener.class;
        else if(eventName==OnInputListener.class.toString())return OnInputListener.class;
        else if(eventName==OnKeyPressedListener.class.toString())return OnKeyPressedListener.class;
        else if(eventName==OnMouseButtonDownListener.class.toString())return OnMouseButtonDownListener.class;
        else if(eventName==OnMouseButtonUpListener.class.toString())return OnMouseButtonUpListener.class;
        else if(eventName==OnMouseEnterListener.class.toString())return OnMouseEnterListener.class;
        else if(eventName==OnMouseLeaveListener.class.toString())return OnMouseLeaveListener.class;
        else if(eventName==OnPasteListener.class.toString())return OnPasteListener.class;
        else if(eventName==OnRemoveListener.class.toString())return OnRemoveListener.class;
        else if(eventName==OnValueChangeListener.class.toString())return OnValueChangeListener.class;
        return null;
    }


}
