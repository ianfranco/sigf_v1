package com.areatecnica.sigf_v1.converters;

import com.areatecnica.sigf_v1.dao.AbstractDao;
import com.areatecnica.sigf_v1.entities.AnticipoTrabajador;
import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "anticipoTrabajadorConverter")
public class AnticipoTrabajadorConverter implements Converter {

    @Inject
    private AbstractDao dao;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.dao.find(getKey(value));
    }

    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Integer value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof AnticipoTrabajador) {
            AnticipoTrabajador o = (AnticipoTrabajador) object;
            return getStringKey(o.getIdAnticipoTrabajador());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AnticipoTrabajador.class.getName()});
            return null;
        }
    }

}
