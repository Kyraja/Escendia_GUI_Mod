package net.escendia.gui.model.network.in;

import com.google.gson.JsonObject;
import net.escendia.gui.controll.GUIService;
import net.escendia.gui.controll.PacketService;
import net.escendia.gui.model.GlobalScope;
import net.escendia.gui.model.components.Element;
import net.escendia.ioc.InversionOfControl;

public abstract class PacketElement extends PacketIn{


    public PacketElement(JsonObject jsonObject) {
        super(jsonObject);
    }

    public static class Add extends PacketElement{

        public Add(JsonObject jsonObject) {
            super(jsonObject);

            GUIService guiService = InversionOfControl.get().build(GUIService.class);
            PacketService packetService = InversionOfControl.get().build(PacketService.class);

            Element element = new Element().fromJson(jsonObject.getAsJsonObject(GlobalScope.ELEMENT).toString());
            guiService.getCurrentGUI().addElement(element);
            packetService.sendPacket(new net.escendia.gui.model.network.out.PacketElement.Added(element));
        }
    }

        public static class Remove extends  PacketElement{

        public Remove(JsonObject jsonObject) {
            super(jsonObject);
            GUIService guiService = InversionOfControl.get().build(GUIService.class);
            PacketService packetService = InversionOfControl.get().build(PacketService.class);
            Element element = new Element().fromJson(jsonObject.getAsJsonObject(GlobalScope.ELEMENT).toString());
            guiService.getCurrentGUI().remove(element);
            packetService.sendPacket(new net.escendia.gui.model.network.out.PacketElement.Added(element));
        }
    }

    public static class Update extends  PacketElement{

        public Update(JsonObject jsonObject) {
            super(jsonObject);
              Element element = new Element().fromJson(jsonObject.getAsJsonObject(GlobalScope.ELEMENT).toString());
              InversionOfControl.get().build(GUIService.class).getCurrentGUI().updateElement(element);
              InversionOfControl.get().build(PacketService.class).sendPacket(new net.escendia.gui.model.network.out.PacketElement.Updated(element));
        }
    }
}
