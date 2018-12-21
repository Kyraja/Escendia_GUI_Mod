package net.escendia.gui.model.components.impl;

import net.escendia.gui.model.components.Element;
import net.escendia.gui.model.components.ElementValuable;
import net.escendia.gui.model.form.Form;
import net.escendia.gui.model.listeners.OnClickListener;

public class CheckBox extends ElementValuable<Boolean> {

    private boolean value = false;
    private Form onTrue;
    private Form onFalse;

    public CheckBox(){
        super();
        this.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(Element element) {
                setStatus(value);
            }
        });
    }

    public CheckBox(Form onTrue, Form onFalse) {
        super(onTrue);
        this.onTrue = onTrue;
        this.onFalse = onFalse;
        this.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(Element element) {
                setStatus(value);
            }
        });
    }

    public void setStatus(boolean value){
        this.value = value;
        if(this.value){
            setForm(onTrue);
        }else{
            setForm(onFalse);
        }
        onValueChange();
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
