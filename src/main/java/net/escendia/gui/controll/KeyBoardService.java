package net.escendia.gui.controll;


import net.escendia.gui.model.gui.KeyBoard;
import net.escendia.ioc.Inject;
import net.escendia.ioc.InversionOfControl;
import net.escendia.ioc.Singleton;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

@Singleton
public class KeyBoardService {

    private final KeyBinding KEY_OPEN_GUI = new KeyBinding("Open Gui to interact with the components.", Keyboard.KEY_N, "Minecraft GUI");
    private boolean updateMode = false;

    @Inject
    public KeyBoard keyBoard;

    public KeyBoardService(){
        ClientRegistry.registerKeyBinding(KEY_OPEN_GUI);
        keyBoard = InversionOfControl.get().build(KeyBoard.class);
    }

    public KeyBinding getKeyOpenGui() {
        return KEY_OPEN_GUI;
    }

    public KeyBoard getKeyBoard() {
        return keyBoard;
    }

    public void update() {
        if(updateMode)keyBoard.update();
    }


    public void setUpdateMode(boolean updateMode) {
        this.updateMode = updateMode;
    }

    public boolean getUpdateMode() {
        return this.updateMode;
    }
}
