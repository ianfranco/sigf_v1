package com.areatecnica.sigf_v1.converters;


import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorLiquidacionDaoImpl;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajadorLiquidacion;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "descuentoTrabajadorLiquidacionConverter")
public class DescuentoTrabajadorLiquidacionConverter implements Converter {

    @Inject
    private DescuentoTrabajadorLiquidacionDaoImpl dao;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        this.dao = new DescuentoTrabajadorLiquidacionDaoImpl();
        
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
        if (object instanceof DescuentoTrabajadorLiquidacion) {
            DescuentoTrabajadorLiquidacion o = (DescuentoTrabajadorLiquidacion) object;
            return getStringKey(o.getIdDescuentoTrabajadorLiquidacion());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DescuentoTrabajadorLiquidacion.class.getName()});
            return null;
        }
    }

}
