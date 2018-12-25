package net.escendia.gui.model.components;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.escendia.gui.controll.KeyBoardService;
import net.escendia.gui.controll.MouseService;
import net.escendia.gui.controll.PacketService;
import net.escendia.gui.model.factories.ElementDeserializer;
import net.escendia.gui.model.factories.FactoryElement;
import net.escendia.gui.model.factories.FormDeserializer;
import net.escendia.gui.model.form.Form;
import net.escendia.gui.model.form.style.impl.RectangleStandard;
import net.escendia.gui.model.gui.KeyBoard;
import net.escendia.gui.model.gui.Mouse;
import net.escendia.gui.model.gui.Visibility;
import net.escendia.gui.model.listeners.*;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.gui.model.network.out.PacketEvents;
import net.escendia.ioc.InversionOfControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Element implements FactoryElement<Element> {

    private UUID elementUUID = UUID.randomUUID();
    private HashMap<UUID, Element> childrenElements = new HashMap<>();
    private Visibility visibility = Visibility.VISIBLE;
    private Form form = new RectangleStandard();
    private ElementStatus elementStatus = ElementStatus.NORMAL;
    private Mouse.CursorType cursorType = Mouse.CursorType.NORMAL;

    private String elementClass;

    private transient boolean mouseOverLastUpdate = false;
    private transient ArrayList<OnClickListener> onClickListenerList = new ArrayList<>();
    private transient ArrayList<OnCopyListener> onCopyListenerList = new ArrayList<>();
    private transient ArrayList<OnDoubleClickListener> onDoubleClickListenerList = new ArrayList<>();
    private transient ArrayList<OnFocusListener> onFocusListenerList = new ArrayList<>();
    private transient ArrayList<OnInputListener> onInputListenerList = new ArrayList<>();
    private transient ArrayList<OnKeyPressedListener> onKeyPressedListenerList = new ArrayList<>();
    private transient ArrayList<OnMouseButtonDownListener> onMouseButtonDownListenerList = new ArrayList<>();
    private transient ArrayList<OnMouseButtonUpListener> onMouseButtonUpListenerList = new ArrayList<>();
    private transient ArrayList<OnMouseEnterListener> onMouseEnterListenerList = new ArrayList<>();
    private transient ArrayList<OnMouseLeaveListener> onMouseLeaveListenerList = new ArrayList<>();
    private transient ArrayList<OnPasteListener> onPasteListenerList = new ArrayList<>();
    private transient ArrayList<OnRemoveListener> onRemoveListener = new ArrayList<>();
    private transient ArrayList<OnValueChangeListener> onValueChangeListenerList = new ArrayList<>();
    private transient ArrayList<OnHoverListener> onHoverListenerList = new ArrayList<>();

    public Element(){
    }


    public Element(UUID elementUUID){
        this.elementUUID = elementUUID;
    }

    public Element(Form form){
        this.form = form;
    }

    public Element(UUID elementUUID, Form form){
        this.elementUUID = elementUUID;
        this.form = form;
    }

    /*
    ---------- Getter Methods --------------
     */
    public UUID getElementUUID() {
        return elementUUID;
    }

    public HashMap<UUID, Element> getChildrenElements() {
        return childrenElements;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public Form getForm(){
        return form;
    }
    public ElementStatus getElementStatus() {
        return this.elementStatus;
    }

    public Mouse.CursorType getCursorType() {
        return cursorType;
    }

    public Element getChildrenElement(UUID elementUUID) {
        for(UUID element : childrenElements.keySet()){
            if(element == elementUUID){
                return childrenElements.get(element);
            }else{
                Element childElement = childrenElements.get(element).getChildrenElement(elementUUID);
                if(childElement!=null)return childElement;
            }
        }
        return null;
    }

    public String getElementClassAsString() {
        return elementClass;
    }

    /*
    ---------- Setter Methods --------------
    */

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public void setElementStatus(ElementStatus elementStatus) {
        this.elementStatus = elementStatus;
        if(this.elementStatus==ElementStatus.ACTIVE)this.onFocus();
        else if(this.elementStatus==ElementStatus.HOVER)this.onHover();
    }

    public void setCursorType(Mouse.CursorType cursorType) {
        this.cursorType = cursorType;
    }

    public void setForm(Form form){this.form = form;}

    /*
    ---------- Add Methods --------------
    */

    /**
     * Adds a child Element and set an remove Listener
     * @param element
     */
    public void addChildrenElement(Element element){
        this.childrenElements.put(element.getElementUUID(), element);
        this.addOnRemoveListener(new OnRemoveListener() {
            @Override
            public void onRemove(Element element) {
                element.remove();
            }
        });
    }

    /**
     * Remove old Element and adds the new one
     * Is called from the {@link net.escendia.gui.model.network.in.PacketElement} (Update)
     * It wont Trigger the "RemoveListener"
     * @param element
     * @return
     */
    public void updateChildrenElement(Element element) {
        UUID searchElement = element.getElementUUID();

        for(UUID elementUUID : childrenElements.keySet()){
            if(elementUUID.equals(searchElement)) {
                childrenElements.remove(elementUUID);
                childrenElements.put(searchElement, element);
                return;
            }else{
                childrenElements.get(elementUUID).updateChildrenElement(element);
            }
        }
    }

    /**
     * Removes a child Element
     * @param element
     */
    public void removeChildrenElement(Element element) {
        this.childrenElements.get(element.getElementUUID()).remove();
        this.childrenElements.remove(element.getElementUUID());
    }

    public void addOnBlurListener(OnHoverListener onHoverListener){
        this.onHoverListenerList.add(onHoverListener);
    }

    public void addOnClickListener(OnClickListener onClickListener) {
        this.onClickListenerList.add(onClickListener);
    }

    public void addOnCopyListener(OnCopyListener onCopyListener) {
        this.onCopyListenerList.add(onCopyListener);
    }

    public void addOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener){
        this.onDoubleClickListenerList.add(onDoubleClickListener);
    }

    public void addOnFocusListener(OnFocusListener onFocusListener){
        this.onFocusListenerList.add(onFocusListener);
    }

    public void addOnInputListener(OnInputListener onInputListener) {
        this.onInputListenerList.add(onInputListener);
    }

    public void addOnKeyPressedListener(OnKeyPressedListener onKeyPressedListener) {
        this.onKeyPressedListenerList.add(onKeyPressedListener);
    }

    public void addOnMouseButtonDownListener(OnMouseButtonDownListener onMouseButtonDownListener){
        this.onMouseButtonDownListenerList.add(onMouseButtonDownListener);
    }

    public void addOnMouseButtonUpListener(OnMouseButtonUpListener onMouseButtonUpListener){
        this.onMouseButtonUpListenerList.add(onMouseButtonUpListener);
    }

    public void addOnMouseEnterListener(OnMouseEnterListener onMouseEnterListener){
        this.onMouseEnterListenerList.add(onMouseEnterListener);
    }

    public void addOnMouseLeaveListener(OnMouseLeaveListener onMouseLeaveListener){
        this.onMouseLeaveListenerList.add(onMouseLeaveListener);
    }

    public void addOnPasteListener(OnPasteListener onPasteListener) {
        this.onPasteListenerList.add(onPasteListener);
    }

    public void addOnRemoveListener(OnRemoveListener onRemoveListener) {
        this.onRemoveListener.add(onRemoveListener);
    }

    public void addOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListenerList.add(onValueChangeListener);
    }


    /*
    ---------- Update Methods --------------
    */

    private void updateKeyBoard(){
        KeyBoard keyBoard = InversionOfControl.get().build(KeyBoardService.class).getKeyBoard();
        if(keyBoard!=null){
            if(keyBoard.getInput()!=0){
                switch (keyBoard.getInput()){
                    case 3: onCopy(); break;
                    case 22: onPaste(); break;
                    default: onInput(keyBoard.getInput());
                }
            }
            if(keyBoard.isKeyPressed())onKeyPressed(keyBoard);
        }
    }




    private void updateMouse(){
        Mouse mouse = InversionOfControl.get().build(MouseService.class).getMouse();
        if(mouse.isClick())onClick();
        if(mouse.isDoubleClick())onDoubleClick();

        if(mouse.isLeftPressed())onMouseButtonDown(mouse, Mouse.Button.LEFT);
        else if(mouse.isLeftPressedPrevious())onMouseButtonUp(mouse, Mouse.Button.LEFT);

        if(mouse.isRightPressed())onMouseButtonDown(mouse, Mouse.Button.RIGHT);
        else if(mouse.isRightPressedPrevious())onMouseButtonUp(mouse, Mouse.Button.RIGHT);

        if(mouse.isMiddlePressed())onMouseButtonDown(mouse, Mouse.Button.MIDDLE);
        else if(mouse.isMiddlePressedPrevious())onMouseButtonUp(mouse, Mouse.Button.MIDDLE);

    }

    private void updateMouseOver(){
        boolean mouseOver = elementStatus == ElementStatus.HOVER || elementStatus == ElementStatus.ACTIVE;

        if(mouseOver && !mouseOverLastUpdate)onMouseEnter();
        else if(!mouseOver && mouseOverLastUpdate)onMouseLeave();

        mouseOverLastUpdate = mouseOver;
    }

    public void remove(){
        for(OnRemoveListener listener : onRemoveListener){
            listener.onRemove(this);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnRemove(this));
    }

    //----- Listener Methods ----

    public void onCopy(){
        for(OnCopyListener onCopyListener : onCopyListenerList){
            onCopyListener.onCopy(this);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnCopy(this));
    }

    public void onPaste(){
        for(OnPasteListener onPasteListener : onPasteListenerList){
            onPasteListener.onPaste(this);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnPaste(this));

    }

    private void onInput(char input) {
        for(OnInputListener onInputListener : onInputListenerList){
            onInputListener.onInputListener(this, input);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnInput(this, input));
    }

    private void onKeyPressed(KeyBoard keyBoard) {
        for(OnKeyPressedListener onKeyPressedListener : onKeyPressedListenerList){
            onKeyPressedListener.onKeyPressed(this, keyBoard);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnKeyPressed(this, keyBoard));

    }

    private void onClick() {
        for(OnClickListener onClickListener : onClickListenerList){
            onClickListener.onClick(this);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnClick(this));

    }

    private void onDoubleClick() {
        for(OnDoubleClickListener onDoubleClickListener : onDoubleClickListenerList){
            onDoubleClickListener.onDoubleClick(this);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnDoubleClick(this));

    }

    private void onMouseButtonDown(Mouse mouse, Mouse.Button button) {
        for(OnMouseButtonDownListener onMouseButtonDownListener : onMouseButtonDownListenerList){
            onMouseButtonDownListener.onMouseButtonDown(this,mouse,button);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnMouseButtonDown(this,mouse,button));

    }

    private void onMouseButtonUp(Mouse mouse, Mouse.Button button) {
        for(OnMouseButtonUpListener onMouseButtonUpListener : onMouseButtonUpListenerList){
            onMouseButtonUpListener.onMouseButtonUp(this,mouse,button);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnMouseButtonUp(this,mouse,button));

    }

    private void onMouseEnter() {
        for(OnMouseEnterListener onMouseEnterListener : onMouseEnterListenerList){
            onMouseEnterListener.onMouseEnter(this);
        }
        //InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnMouseEnter(this));

    }

    private void onMouseLeave() {
        for(OnMouseLeaveListener onMouseLeaveListener : onMouseLeaveListenerList){
            onMouseLeaveListener.onMouseLeave(this);
        }
        //InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnMouseLeave(this));
    }

    public void onValueChange(){
        for(OnValueChangeListener onValueChangeListener : onValueChangeListenerList){
            onValueChangeListener.onValueChange(this);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnValueChange(this));
    }

    public void onFocus(){
        for(OnFocusListener onFocusListener : onFocusListenerList){
            onFocusListener.onFocus(this);
        }
        InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnFocus(this));
    }

    public void onHover(){
        for(OnHoverListener onHoverListener : onHoverListenerList){
            onHoverListener.onBlur(this);
        }
        //InversionOfControl.get().build(PacketService.class).sendPacket(new PacketEvents.Post.OnHover(this));
    }


    //------ Update Methods ----

    public void update() {
        updateKeyBoard();
        updateMouse();
        updateMouseOver();
        for(Element element : getChildrenElements().values()){
            element.update();
        }
    }

    public void draw(){
        if(getVisibility()==Visibility.VISIBLE){
            form.draw();
            for(Element element : getChildrenElements().values()){
                element.draw();
            }
        }

    }

    @Override
    public JsonElement toJson() {
        return new GsonBuilder()
                .create().toJsonTree(this);
    }

    @Override
    public Element fromJson(String jsonString) {
        return new GsonBuilder()
                .registerTypeAdapter(Element.class, new ElementDeserializer())
                .registerTypeAdapter(Form.class, new FormDeserializer())
                .create().fromJson(jsonString, Element.class);
    }


}
