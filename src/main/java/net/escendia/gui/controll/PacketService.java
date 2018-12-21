package net.escendia.gui.controll;

import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandler;
import net.escendia.gui.model.GlobalScope;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.gui.model.network.Packet;
import net.escendia.gui.model.network.UserConnection;
import net.escendia.gui.model.network.in.PacketConnection;
import net.escendia.gui.model.network.in.PacketElement;
import net.escendia.gui.model.network.in.PacketGUI;
import net.escendia.gui.model.network.out.PacketOut;
import net.escendia.ioc.Inject;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

@Singleton
public class PacketService{

    @Inject
    public EscendiaLogger logger;

    private UserConnection userConnection;


    public PacketService(){
        logger = InversionOfControl.get().build(EscendiaLogger.class);
        logger.info("PacketService creating...");
        logger.info("PacketService created...");
    }


    public void startClient() {
        try {
            logger.info("Try to create Connection to: " + Minecraft.getMinecraft().getCurrentServerData().serverIP);
            userConnection = new UserConnection(new Socket(Minecraft.getMinecraft().getCurrentServerData().serverIP, 20000));
            userConnection.start();
            logger.info("Socket connected? " + userConnection.getSocket().isConnected());
            logger.info("Client started connection...");
        } catch (IOException e) {
            logger.error("Error creating connection..", e);
        }
    }


    public void sendPacket(PacketOut packet){
        try{
            logger.info("sendPacket() ID: " + packet.getID());
            logger.info("sendPacket() Body: " + packet.getJsonObject().toString());
            userConnection.send(packet);
            logger.info("sendPacket() sended packet: " + packet.getID());
        }catch(Exception ex){
            logger.error("Error while sending a packet.", ex);
        }
    }

    public void receivePacket(JsonObject packet) {
        try{
            int id =packet.get(GlobalScope.PACKET_ID).getAsInt();
            JsonObject content = packet.getAsJsonObject(GlobalScope.BODY);
            logger.info("receivePacket() ID: " + id);
            logger.info("receivePacket() Body: " + content.toString());
            switch (id){
                case GlobalScope.PACKET_SERVER_CONNECTION_INIT: new PacketConnection.Init(content);break;
                case GlobalScope.PACKET_SERVER_CONNECTION_CLOSE: new PacketConnection.Close(content);break;
                case GlobalScope.PACKET_SERVER_GUI_CREATE: new PacketGUI.Create(content);break;
                case GlobalScope.PACKET_SERVER_GUI_DELETE: new PacketGUI.Delete(content);break;
                case GlobalScope.PACKET_SERVER_COMPONENT_ADD: new PacketElement.Add(content);break;
                case GlobalScope.PACKET_SERVER_COMPONENT_REMOVE: new PacketElement.Remove(content);break;
                case GlobalScope.PACKET_SERVER_COMPONENT_UPDATE: new PacketElement.Update(content);break;
            }
            logger.info("receivePacket() finish handling packet: " + id);
        }catch (Exception ex){
            logger.error("Error while receive Packets", ex);
        }

    }

}
