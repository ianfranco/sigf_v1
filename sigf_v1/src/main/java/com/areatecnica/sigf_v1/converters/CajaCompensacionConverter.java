package com.areatecnica.sigf_v1.converters;


import com.areatecnica.sigf_v1.dao.AbstractDao;
import com.areatecnica.sigf_v1.entities.CajaCompensacion;
import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.CajaCompensacionDaoImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "cajaCompensacionConverter")
public class CajaCompensacionConverter implements Converter {

    @Inject
    private CajaCompensacionDaoImpl dao;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        dao = new CajaCompensacionDaoImpl();
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
        if (object instanceof CajaCompensacion) {
            CajaCompensacion o = (CajaCompensacion) object;
            return getStringKey(o.getIdCajaCompensacion());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CajaCompensacion.class.getName()});
            return null;
        }
    }

}
