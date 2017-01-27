/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ian Franco
 */
@Named(value = "indexController")
@SessionScoped
public class IndexController implements Serializable {

    private boolean registroGuias;
    private boolean servicios;
    private boolean tesoreria;
    private boolean remuneraciones;
    private boolean boletos;
    private boolean empresario;
    private boolean informes;

    /**
     * Creates a new instance of IndexController
     */
    public IndexController() {
        this.registroGuias = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("registroGuias");        
        this.servicios = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("servicios");
        this.tesoreria = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tesoreria");
        this.remuneraciones = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("remuneraciones");
        this.boletos = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("boletos");
        this.empresario = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("empresario");
        this.informes = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("informes");
        
    }

    public boolean isRegistroGuias() {
        return registroGuias;
    }

    public void setRegistroGuias(boolean registroGuias) {
        this.registroGuias = registroGuias;
    }

    public boolean isServicios() {
        return servicios;
    }

    public void setServicios(boolean servicios) {
        this.servicios = servicios;
    }

    public boolean isTesoreria() {
        return tesoreria;
    }

    public void setTesoreria(boolean tesoreria) {
        this.tesoreria = tesoreria;
    }

    public boolean isRemuneraciones() {
        return remuneraciones;
    }

    public void setRemuneraciones(boolean remuneraciones) {
        this.remuneraciones = remuneraciones;
    }

    public boolean isBoletos() {
        return boletos;
    }

    public void setBoletos(boolean boletos) {
        this.boletos = boletos;
    }

    public boolean isEmpresario() {
        return empresario;
    }

    public void setEmpresario(boolean empresario) {
        this.empresario = empresario;
    }

    public boolean isInformes() {
        return informes;
    }

    public void setInformes(boolean informes) {
        this.informes = informes;
    }

}
