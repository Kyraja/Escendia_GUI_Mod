package net.escendia.gui.model.form;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.escendia.gui.model.factories.FactoryElement;
import net.escendia.gui.model.factories.FormDeserializer;
import net.escendia.gui.model.form.style.FormStyle;
import net.escendia.gui.model.logger.EscendiaLogger;
import net.escendia.ioc.InversionOfControl;

public class Form implements FactoryElement<Form> {


    private FormStyle formStyle;
    private String formClass = getClass().toString();

    public Form(){
        this.formStyle = new FormStyle();
    }

    public Form(FormStyle formStyle){
        this.formStyle = formStyle;
    }

    public FormStyle getFormStyle() {
        return formStyle;
    }

    public void setFormStyle(FormStyle formStyle) {
        this.formStyle = formStyle;
    }

    public boolean isLocatedInside(double x, double y){return false;};

    public String getFormClass() {
        return formClass;
    }

    public void draw(){

    };



    @Override
    public JsonElement toJson() {
        return new GsonBuilder().create().toJsonTree(this);
    }

    @Override
    public Form fromJson(String jsonString) {
        return new GsonBuilder().registerTypeAdapter(Form.class, new FormDeserializer()).create().fromJson(jsonString, Form.class);
    }


}
