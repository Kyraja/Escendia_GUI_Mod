package net.escendia.gui.model.components;

import net.escendia.gui.model.form.Form;

public class ElementValuable<T> extends Element {


    public T getValue(){return null;}
    public void setValue(T value){}

    public ElementValuable() {
        super();
    }

    public ElementValuable(Form form) {
        super(form);
    }

}
