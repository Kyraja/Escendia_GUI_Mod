package net.escendia.gui.model.listeners;

import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.gui.Mouse;

public interface OnMouseButtonUpListener {
    void onMouseButtonUp(Element element, Mouse mouse, Mouse.Button button);
}
