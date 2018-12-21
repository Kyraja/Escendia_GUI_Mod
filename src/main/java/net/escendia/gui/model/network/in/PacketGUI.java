package net.escendia.gui.model.network.in;

import com.google.gson.JsonObject;
import net.escendia.gui.EscendiaGUIMod;
import net.escendia.gui.controll.GUIService;
import net.escendia.gui.controll.PacketService;
import net.escendia.gui.view.EscendiaGui;
import net.escendia.ioc.InversionOfControl;

public class PacketGUI extends PacketIn {

    public PacketGUI(JsonObject jsonObject) {
        super(jsonObject);
    }

    public static class Create extends PacketGUI{

        public Create(JsonObject jsonObject) {
            super(jsonObject);
            InversionOfControl.get().build(GUIService.class).closeGUI();
            EscendiaGui escendiaGui = new EscendiaGui(InversionOfControl.get().build(EscendiaGUIMod.class).mod);
            InversionOfControl.get().build(GUIService.class).setCurrentGUI(escendiaGui);
            InversionOfControl.get().build(PacketService.class).sendPacket(new net.escendia.gui.model.network.out.PacketGUI.Created(escendiaGui.getGuiUUID()));
        }
    }

    public static class Delete extends PacketGUI{

        public Delete(JsonObject jsonObject) {
            super(jsonObject);
            EscendiaGui escendiaGui = InversionOfControl.get().build(GUIService.class).getCurrentGUI();
            if(escendiaGui!=null){
                InversionOfControl.get().build(GUIService.class).closeGUI();
                InversionOfControl.get().build(PacketService.class).sendPacket(new net.escendia.gui.model.network.out.PacketGUI.Deleted(escendiaGui.getGuiUUID()));
            }
        }
    }

}
