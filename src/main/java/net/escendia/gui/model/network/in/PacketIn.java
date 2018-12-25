package net.escendia.gui.model.network.in;

import com.google.gson.JsonObject;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;

public abstract class PacketIn {

    protected final JsonObject jsonObject;

    public PacketIn(JsonObject jsonObject){
        this.jsonObject = jsonObject;
    }

}
