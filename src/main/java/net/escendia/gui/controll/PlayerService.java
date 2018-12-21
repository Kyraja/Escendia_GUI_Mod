package net.escendia.gui.controll;

import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.gui.model.network.out.PacketConnection;
import net.escendia.ioc.Inject;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Singleton
public class PlayerService {

    @Inject
    public PacketService packetService;

    @Inject
    private EscendiaLogger logger;

    public PlayerService(){
        logger = InversionOfControl.get().build(EscendiaLogger.class);
        logger.info("PlayerService creating...");
        packetService = InversionOfControl.get().build(PacketService.class);
        logger.info("PlayerService created...");
    }


    /*
    ------------------------- EVENTS -------------------------
     */
    @SubscribeEvent
    public void playerConnectToServer(FMLNetworkEvent.ClientConnectedToServerEvent event){
        logger.info("connection to Server... ");
        logger.info("sending Initial Connection Package... ");
        new Thread(new Runnable() {

            @Override
            public void run() {
                //Sleep the thread to prevent the error by server connect.
                try {
                    Thread.sleep(1000);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                packetService.startClient();
                packetService.sendPacket(new PacketConnection.Init());
            }
        }).start();
        logger.info("[Escendia GUI Mod] send Initial Connection send... ");
    }

    @SubscribeEvent
    public void playerDisconnectFromServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event){
        logger.info("disconnect from server... ");
        logger.info("sending disconnect package.. ");
        new Thread(new Runnable() {

            @Override
            public void run() {
                InversionOfControl.get().build(GUIService.class).closeGUI();
                packetService.sendPacket(new PacketConnection.Close());
            }
        }).start();
        logger.info("send disconnect package... ");
    }

}
