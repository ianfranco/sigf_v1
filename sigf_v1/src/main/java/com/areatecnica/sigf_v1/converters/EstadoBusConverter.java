package com.areatecnica.sigf_v1.converters;


import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EstadoBusDaoImpl;
import com.areatecnica.sigf_v1.entities.EstadoBus;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "estadoBusConverter")
public class EstadoBusConverter implements Converter {
   
    private EstadoBusDaoImpl estadoBusDaoImpl;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        
        this.estadoBusDaoImpl = new EstadoBusDaoImpl();
        
        return this.estadoBusDaoImpl.findById(getKey(value));
    }

    int getKey(String value) {
        int key;
        key = Integer.parseInt(value);
        return key;
    }

    String getStringKey(int value) {
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
        if (object instanceof EstadoBus) {
            EstadoBus o = (EstadoBus) object;
            return getStringKey(o.getIdEstadoBus());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EstadoBus.class.getName()});
            return null;
        }
    }

}
