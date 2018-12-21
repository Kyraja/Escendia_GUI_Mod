package net.escendia.gui.model.network.in;

import com.google.gson.JsonObject;
import net.escendia.gui.controll.GUIService;
import net.escendia.gui.controll.PacketService;
import net.escendia.gui.model.GlobalScope;
import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.factories.ModelFactory;
import net.escendia.gui.model.listeners.*;
import net.escendia.gui.view.EscendiaGui;
import net.escendia.ioc.InversionOfControl;

import java.util.UUID;

public class PacketEvents extends PacketIn {

    public PacketEvents(JsonObject jsonObject) {
        super(jsonObject);
    }

}
