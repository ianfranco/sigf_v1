/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.util;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author ianfr
 */
@Named(value = "idleMonitorView")
@SessionScoped
public class IdleMonitorView implements Serializable {

    /**
     * Creates a new instance of IdleMonitorView
     */
    public IdleMonitorView() {
    }
    
    public void onIdle() {
        /*FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        "Sin actividad", "Su sesión se cerrará pronto"));*/
        JsfUtil.addErrorMessage("Sin actividad, su sesión se cerrará pronto");
    }
 
    public void onActive() {
        /*FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Ha regresado", "Se ha reactivado su sesión"));*/
        JsfUtil.addSuccessMessage("Se ha reactivado su sesión");
    }
    
}
