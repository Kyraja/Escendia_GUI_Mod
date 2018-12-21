package net.escendia.gui.model.network.in;

import com.google.gson.JsonObject;
import net.escendia.gui.controll.GUIService;
import net.escendia.gui.controll.PacketService;
import net.escendia.gui.model.GlobalScope;
import net.escendia.gui.model.gui.GeneralGUIData;
import net.escendia.ioc.InversionOfControl;

public abstract class PacketConnection extends PacketIn {

    public PacketConnection(JsonObject jsonObject) {
        super(jsonObject);
    }



    public static class Init extends PacketConnection{

        public Init(JsonObject jsonObject) {
            super(jsonObject);
              InversionOfControl.get().build(GUIService.class).initClient(new GeneralGUIData().fromJson(jsonObject.getAsJsonObject(GlobalScope.INITDATA).toString()));
              InversionOfControl.get().build(PacketService.class).sendPacket(new net.escendia.gui.model.network.out.PacketConnection.Init());
        }
    }

    public static class Close extends PacketConnection{

        public Close(JsonObject jsonObject) {
            super(jsonObject);
            //TODO IF CLOSE NEEDED
        }
    }
}
