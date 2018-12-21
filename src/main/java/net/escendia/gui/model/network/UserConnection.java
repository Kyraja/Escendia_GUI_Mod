package net.escendia.gui.model.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.escendia.gui.controll.PacketService;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.gui.model.network.out.PacketOut;
import net.escendia.ioc.InversionOfControl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class UserConnection extends Thread {

        private EscendiaLogger logger;
        private Socket socket;
        private BufferedReader in;
        private DataOutputStream out;

    public UserConnection(Socket socket) {
            logger = InversionOfControl.get().build(EscendiaLogger.class);
            this.socket = socket;
            try {
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                logger.error("PlayerJoined - error creating Connection....", e);

            }

            logger.info("PlayerJoined - created Connection....");
    }

    public void send(PacketOut packetOut){
        try {
            out.writeBytes(packetOut.toJson().toString() + "\n");
        } catch (Exception e) {
            logger.error("Error while sending packets...", e);
            //remove();
        }
    }


    @Override
    public void run() {
        try{

            JsonParser parser = new JsonParser();

            while(true){
                String message = in.readLine();
                JsonObject jsonObject = parser.parse(message).getAsJsonObject();

                InversionOfControl.get().build(PacketService.class).receivePacket(jsonObject);

            }
        }catch(Exception e){
            logger.error("Error while running the intput read...", e);
            //remove();
        }
    }

    public Socket getSocket() {
        return this.socket;
    }
}
