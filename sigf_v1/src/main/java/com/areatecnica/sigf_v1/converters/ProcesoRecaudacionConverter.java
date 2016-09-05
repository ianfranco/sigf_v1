package com.areatecnica.sigf_v1.converters;


import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.ProcesoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "procesoRecaudacionConverter")
public class ProcesoRecaudacionConverter implements Converter {

    @Inject
    private ProcesoRecaudacionDaoImpl dao;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        
        this.dao = new ProcesoRecaudacionDaoImpl();
        
        return this.dao.findById(getKey(value));
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
        if (object instanceof ProcesoRecaudacion) {
            ProcesoRecaudacion o = (ProcesoRecaudacion) object;
            return getStringKey(o.getIdProcesoRecaudacion());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProcesoRecaudacion.class.getName()});
            return null;
        }
    }

}
