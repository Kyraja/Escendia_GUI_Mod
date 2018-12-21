package net.escendia.gui.model.listeners;

import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.gui.KeyBoard;

public interface OnKeyPressedListener {
    void onKeyPressed(Element element, KeyBoard keyboard);
}
