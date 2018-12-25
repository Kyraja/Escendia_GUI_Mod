package net.escendia.gui;

import net.escendia.gui.controll.*;
import net.escendia.gui.model.GlobalScope;
import net.escendia.gui.model.factories.ModelFactory;
import net.escendia.gui.model.gui.DrawUtils;
import net.escendia.gui.model.gui.KeyBoard;
import net.escendia.gui.model.gui.Mouse;
import net.escendia.gui.model.gui.Screen;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.gui.model.logger.Level;
import net.escendia.ioc.Inject;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Singleton
@Mod(acceptedMinecraftVersions = "[1.12,)", modid = "escendiaguimod")
public class EscendiaGUIMod {

    @Inject
    private EscendiaLogger logger;

    public EscendiaGUIMod mod;
    private static Integer[] clickedTimes;

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {

        InversionOfControl.get().registerInterfaceImplementation(FileService.class, new FileService());

        this.logger = new EscendiaLogger("Escendia GUI Mod", Level.TRACE);
        logger.info("Escendia GUI Mod loading...");
        mod = this;
        InversionOfControl.get().registerInterfaceImplementation(EscendiaLogger.class, logger);
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        InversionOfControl.get().registerDependency(this);
        InversionOfControl.get().registerInterfaceImplementation(Minecraft.class, FMLClientHandler.instance().getClient());
        InversionOfControl.get().registerDependency(new ModelFactory());
        InversionOfControl.get().registerDependency(new GlobalScope());
        InversionOfControl.get().registerInterfaceImplementation(Screen.class, new Screen());
        InversionOfControl.get().registerDependency(new DrawUtils());
        InversionOfControl.get().registerInterfaceImplementation(KeyBoard.class, new KeyBoard());
        InversionOfControl.get().registerInterfaceImplementation(Mouse.class, new Mouse());


        InversionOfControl.get().registerInterfaceImplementation(ImageService.class, new ImageService());

        InversionOfControl.get().registerInterfaceImplementation(FontService.class, new FontService());

        InversionOfControl.get().registerInterfaceImplementation(PacketService.class, new PacketService());

        MinecraftForge.EVENT_BUS.register(InversionOfControl.get().build(PacketService.class));


        InversionOfControl.get().registerInterfaceImplementation(PlayerService.class, new PlayerService());

        MinecraftForge.EVENT_BUS.register(InversionOfControl.get().build(PlayerService.class));


        InversionOfControl.get().registerInterfaceImplementation(KeyBoardService.class, new KeyBoardService());

        MinecraftForge.EVENT_BUS.register(InversionOfControl.get().build(KeyBoardService.class));

        InversionOfControl.get().registerInterfaceImplementation(MouseService.class, new MouseService());

        MinecraftForge.EVENT_BUS.register(InversionOfControl.get().build(MouseService.class));


        InversionOfControl.get().registerInterfaceImplementation(GUIService.class, new GUIService(this));

        MinecraftForge.EVENT_BUS.register(InversionOfControl.get().build(GUIService.class));

        FMLCommonHandler.instance().bus().register(InversionOfControl.get().build(GUIService.class));

        logger.info("Escendia GUI Mod loaded...");
    }

    @Mod.EventHandler
    public void loadPost(FMLPostInitializationEvent event){

    }

    /**
     *
     * Ablauf des GUI Mods:
     *
     * --> Laden aller Services
     * --> DONE: 1. FMLNetworkEvent.ClientConnectedToServerEvent //CLIENT AN SERVER// PacketConnection.InitiateConnection()
     * --> DONE: 2. SERVER AN CLIENT // PacketConnection.InitClient() - Laden von Fonts und Images
     * --> DONE: 3. CLIENT AN SERVER // PacketConnection.InitiatedClient() - Senden an Client für Info
     * --> DONE: 4. SERVER AN CLIENT // PacketConnection.OpenGUI() - Öffnen eines GUIs
     * --> DONE: 5. CLIENT AN SERVER // PacketGUI.OpenedGUI() -
     * --> TODO: 6. SERVER AN CLIENT // PacketGUIComponent.XXX() - Methoden für Components (Add, Remove, Attribute, etc.)
     * --> TODO: 7. CLIENT AN SERVER // PacketGUIEvent.XXX() - Eventhandling von Components
     * --> DONE: 8. SERVER AN CLIENT // PacketGUI.CloseGUI() - GUI schließen
     * --> DONE: 9. CLIENT AN SERVER // PacketGUI.ClosedGUI() - GUI geschlossen
     *
     *
     * Sonstiges DOING:
     * --> DONE: Components - CLIENT
     * --> DONE: Components - SERVER (String/JSON Object)
     * --> TODO: Eventhandling - SERVER
     * --> TODO: Eventhandling - CLIENT
     * --> DONE: Shapes - CLIENT
     * --> DONE: Shapes - SERVER
     * --> DONE: Modelfactory - CLIENT (JsonObject)
     * --> DONE: Modefactory - SERVER (JSON)
     */

}
