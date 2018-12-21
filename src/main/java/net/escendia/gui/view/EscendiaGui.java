package net.escendia.gui.view;

import net.escendia.gui.model.components.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.util.HashMap;
import java.util.UUID;

/**
 * EscendiaGui the open a gui to work with
 */
public class EscendiaGui extends GuiScreen implements IGuiHandler {

    private final static int MCGUI_ID = -1000;
    private final UUID guiUUID;
    private final Object escendiaguimod;
    private boolean status;
    private HashMap<UUID, Element> elementList = new HashMap<>();
    private boolean interactMode;
    private Element hoveredElement;
    private Element focusedElement;


    public EscendiaGui(Object escendiaguimod){
        super();
        this.escendiaguimod = escendiaguimod;
        this.guiUUID =  UUID.fromString("00000000-0000-0000-0000-000000000000");
        this.status = false;
        this.interactMode = false;
        this.elementList = new HashMap<>();
    }


    /*
    -------------- Getter Methods ---------------
     */

    public GuiScreen getGUI(){
        return this;
    }

    public UUID getGuiUUID() {
        return guiUUID;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID==MCGUI_ID)return this;
        else return null;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    public HashMap<UUID, Element> getElementList() {
        return elementList;
    }

    public boolean isInteractMode() {
        return interactMode;
    }


    public Element getHoverElement() {
        return hoveredElement;
    }

    public Element getFocusedElement() {
        return this.focusedElement;
    }
    /*
    -------------- Setter Methods ---------------
     */


    public void addElement(Element element) {
        elementList.put(element.getElementUUID(), element);
    }

    public void remove(Element element){
        if(elementList.get(element)!=null){
            elementList.remove(element);
        }
        element.remove();
    }

    public void openGUI(){
        Minecraft.getMinecraft().player.openGui(escendiaguimod, MCGUI_ID, Minecraft.getMinecraft().player.world, (int)Minecraft.getMinecraft().player.serverPosX, (int)Minecraft.getMinecraft().player.serverPosY, (int)Minecraft.getMinecraft().player.serverPosZ);
    }
    
    public void closeGUI(){
        this.hoveredElement = null;
        this.focusedElement = null;
        Minecraft.getMinecraft().player.closeScreen();
    }


    public void setInteractMode(boolean interactMode) {
        this.interactMode = interactMode;
        if(interactMode){
            openGUI();
        }else{
            closeGUI();
        }
    }

    public boolean getInteractMode() {
        return this.interactMode;
    }

    /**
     * Set the hovered Element is set by the {@link net.escendia.gui.controll.MouseService}
     * @param hoveredElement
     */
    public void setHoveredElement(Element hoveredElement){
        this.hoveredElement = hoveredElement;
    }

    /**
     * Method to set the focused Element
     * Will be set in the {@link net.escendia.gui.controll.MouseService}
     * @param focusedElement
     */
    public void setFocusedElement(Element focusedElement) {
        this.focusedElement = focusedElement;
    }
    /*
        ------------- Update Methods ------------
         */
    public void draw(){
        for(Element element : getElementList().values()){
            element.draw();
        }
    }

    public void update() {
        for(Element element : getElementList().values()){
            element.update();
        }
    }


    /**
     * Search for a element by {@link UUID} from the gui and returns a {@link Element}
     * @param elementUUID
     * @return
     */
    public Element getElement(UUID elementUUID) {

        for(UUID element : elementList.keySet()){
            if(element == elementUUID){
                return elementList.get(element);
            }else{
                Element childElement = elementList.get(element).getChildrenElement(elementUUID);
                if(childElement!=null)return childElement;
            }
        }
        return null;
    }

    /**
     * Remove old Element and adds the new one
     * Is called from the {@link net.escendia.gui.model.network.in.PacketElement} (Update)
     * @param element
     * @return
     */
    public boolean updateElement(Element element) {
        UUID searchElement = element.getElementUUID();

        for(UUID elementUUID : elementList.keySet()){
            if(elementUUID == searchElement) {
                elementList.remove(elementUUID);
                elementList.put(searchElement, element);
                return true;
            }else{
                Element childElement = elementList.get(elementUUID).getChildrenElement(elementUUID);
                if(childElement!=null){
                    elementList.get(elementUUID).removeChildrenElement(childElement);
                    return true;
                }
            }
        }
        elementList.put(searchElement, element);
        return false;
    }
}
