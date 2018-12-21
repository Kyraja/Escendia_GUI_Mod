package net.escendia.gui.controll;

import net.escendia.gui.EscendiaGUIMod;
import net.escendia.gui.model.gui.Screen;
import net.escendia.gui.model.gui.GeneralGUIData;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.gui.model.network.out.PacketGUI;
import net.escendia.gui.view.EscendiaGui;
import net.escendia.ioc.Inject;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.Map;
import java.util.Stack;

@Singleton
@SideOnly(Side.CLIENT)
public class GUIService {



    @Inject
    private FontService fontRepository;

    @Inject
    private ImageService imageRepository;

    @Inject
    private Screen screen;

    @Inject
    private KeyBoardService keyBoardService;

    @Inject
    private MouseService mouseService;

    @Inject
    private EscendiaLogger logger;

    private EscendiaGUIMod mod;

    private Stack<EscendiaGui> guiStack;

    private EscendiaGui currentGUI;

    public GUIService(){}

    public GUIService(EscendiaGUIMod mod) {
        this.logger = InversionOfControl.get().build(EscendiaLogger.class);

        logger.info("[Escendia GUI Mod] GUIService creating...");
        this.mod = mod;
        this.guiStack = new Stack<>();
        this.fontRepository = InversionOfControl.get().build(FontService.class);
        this.imageRepository = InversionOfControl.get().build(ImageService.class);
        this.screen = InversionOfControl.get().build(Screen.class);
        this.keyBoardService = InversionOfControl.get().build(KeyBoardService.class);
        this.mouseService = InversionOfControl.get().build(MouseService.class);
        logger.info("[Escendia GUI Mod] GUIService created...");
    }


    public void initClient(GeneralGUIData generalGUIData) {
        logger.info(generalGUIData.toJson().toString());

        for(String url : generalGUIData.getFonts())
            fontRepository.downloadFont(url);

        for(Map.Entry pairs : generalGUIData.getImages().entrySet())
            imageRepository.downloadImage((String) pairs.getValue(),(String) pairs.getKey());

        imageRepository.generateImages();
    }




    public EscendiaGui getCurrentGUI() {
        return currentGUI;
    }

    /*
    --------------------- EVENT ON RENDER --------------------
     */
    @SubscribeEvent
    public void hideHealth(RenderGameOverlayEvent event) {

        if((event.getType().equals(RenderGameOverlayEvent.ElementType.HEALTH) || event.getType().equals(RenderGameOverlayEvent.ElementType.FOOD) && event.isCancelable())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderUpdate(RenderGameOverlayEvent.Post.Text event) {

        try{
            if (currentGUI != null) {
                    if (keyBoardService.getKeyOpenGui().isPressed()) {
                        currentGUI.setInteractMode(!currentGUI.getInteractMode());
                    }
                    screen.setScreen(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, event.getResolution().getScaleFactor());
                    if(currentGUI != null && currentGUI.getInteractMode()){
                        keyBoardService.update();
                        mouseService.update();
                    }else{
                        //Reset old Values if not interactmode
                        mouseService.resetComponentHoveredAndFocused();
                    }
                    if (keyBoardService.getKeyBoard().getKeyListener(Keyboard.KEY_ESCAPE).isPressed()) {
                        if (currentGUI == null || currentGUI.getFocusedElement()==null) {
                            closeGUI();
                            return;
                        }
                    }
                    if(currentGUI!=null){
                        currentGUI.update();
                        currentGUI.draw();
                    }
            }
        }catch (NoSuchMethodError ex){
            logger.error("NoSuchMethod...", ex);
        }

    }

    public void setCurrentGUI(EscendiaGui currentGUI) {
        //Need to register the gui if the gui is set
        NetworkRegistry.INSTANCE.registerGuiHandler(InversionOfControl.get().build(EscendiaGUIMod.class).mod, currentGUI);
        this.currentGUI = currentGUI;
        //currentGUI.openGUI();
    }

    /**
     * Remove current gui
     */
    public void closeGUI() {
        if (currentGUI != null) {
            currentGUI.setInteractMode(false);
            InversionOfControl.get().build(PacketService.class).sendPacket(new PacketGUI.Deleted(currentGUI.getGuiUUID()));
        }
        this.currentGUI = null;
    }

}
