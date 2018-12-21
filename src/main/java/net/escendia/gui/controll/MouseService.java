package net.escendia.gui.controll;

import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.components.ElementStatus;
import net.escendia.gui.model.gui.Mouse;
import net.escendia.gui.model.gui.Visibility;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.gui.view.EscendiaGui;
import net.escendia.ioc.Inject;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import org.lwjgl.input.Keyboard;

@Singleton
public class MouseService {

    @Inject
    public Mouse mouse;

    @Inject
    public EscendiaLogger logger;

    public MouseService() {
        this.mouse = InversionOfControl.get().build(Mouse.class);
        this.logger = InversionOfControl.get().build(EscendiaLogger.class);
    }

    public void update() {
        mouse.update();

        if(!mouse.isGrabbed()){
            setHoveredElement();
            setFocusedElementAndKeyBoard();
        }else{
            resetComponentHoveredAndFocused();
        }
    }


    public Mouse getMouse() {
        return this.mouse;
    }

    private void setHoveredElement() {
        //Select Components etc and write it back
        GUIService guiService = InversionOfControl.get().build(GUIService.class);
        EscendiaGui escendiaGui = guiService.getCurrentGUI();
        if(escendiaGui!=null){
            //Set current Hovered Element
            if(escendiaGui.getHoverElement()!=null){
                escendiaGui.getHoverElement().setElementStatus(ElementStatus.NORMAL);
                escendiaGui.setHoveredElement(null);
            }

            for(Element element : escendiaGui.getElementList().values()){
                findHoveredElement(element);
            }

            if(escendiaGui.getHoverElement()!=null){
                if(mouse.isLeftPressed() || mouse.isRightPressed()){
                    escendiaGui.getHoverElement().setElementStatus(ElementStatus.ACTIVE);
                    escendiaGui.setFocusedElement(escendiaGui.getHoverElement());
                }else{
                    escendiaGui.getHoverElement().setElementStatus(ElementStatus.HOVER);
                }
                mouse.setCurrentCursor(escendiaGui.getHoverElement().getCursorType());
            }else{
                mouse.setCurrentCursor(Mouse.CursorType.HAND);
            }
        }
    }

    private void setFocusedElementAndKeyBoard(){
        KeyBoardService keyBoardService = InversionOfControl.get().build(KeyBoardService.class);
        GUIService guiService = InversionOfControl.get().build(GUIService.class);
        EscendiaGui escendiaGui = guiService.getCurrentGUI();
        Element focusedElement = guiService.getCurrentGUI().getFocusedElement();
        Element hoveredElement = guiService.getCurrentGUI().getHoverElement();

        if(escendiaGui != null && keyBoardService.getKeyBoard().getKeyListener(Keyboard.KEY_ESCAPE).isPressed() && keyBoardService.getUpdateMode()){
            guiService.getCurrentGUI().setFocusedElement(null);
            keyBoardService.setUpdateMode(false);
        }
        else if(escendiaGui != null && (mouse.isLeftPressed() || mouse.isMiddlePressed() || mouse.isRightPressed()) && !(mouse.isMiddlePressedPrevious() || mouse.isLeftPressedPrevious() || mouse.isRightPressedPrevious())){
            if(hoveredElement != focusedElement){
                if(focusedElement != null) {
                    escendiaGui.setFocusedElement(null);
                    keyBoardService.setUpdateMode(false);
                }

                if(hoveredElement != null){
                    escendiaGui.setHoveredElement(focusedElement);
                    keyBoardService.setUpdateMode(true);
                }
                else
                    escendiaGui.setFocusedElement(null);
            }
        }
    }

    public void resetComponentHoveredAndFocused(){
        GUIService guiService = InversionOfControl.get().build(GUIService.class);
        EscendiaGui escendiaGui = guiService.getCurrentGUI();
        if(escendiaGui != null && escendiaGui.getHoverElement() != null) {

            escendiaGui.getHoverElement().setElementStatus(ElementStatus.NORMAL);
            escendiaGui.setHoveredElement(null);
            mouse.setCurrentCursor(Mouse.CursorType.NORMAL);
        }

        if(escendiaGui != null && escendiaGui.getFocusedElement() != null) {
            escendiaGui.setFocusedElement(null);
            InversionOfControl.get().build(KeyBoardService.class).setUpdateMode(false);
        }
    }

    private void findHoveredElement(Element element){
        if(element.getVisibility()==Visibility.VISIBLE){
            if(element.getForm().isLocatedInside(mouse.getMouseXPosition(), mouse.getMouseYPosition())){
                InversionOfControl.get().build(GUIService.class).getCurrentGUI().setHoveredElement(element);
            }
            if(element.getForm()!=null){
                for(Element childElements : element.getChildrenElements().values()){
                    findHoveredElement(childElements);
                }
            }
        }
    }

}
