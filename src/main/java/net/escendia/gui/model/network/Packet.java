package net.escendia.gui.model.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.nio.charset.StandardCharsets;

public class Packet implements IMessage {

    private JsonObject jsonObject;

    public Packet(JsonObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public Packet(){}

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        jsonObject = new JsonParser().parse(new String(buf.array()).trim()).getAsJsonObject();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(jsonObject.toString().getBytes());
    }
}
