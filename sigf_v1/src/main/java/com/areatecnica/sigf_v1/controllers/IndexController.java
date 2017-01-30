/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.LogDaoImpl;
import com.areatecnica.sigf_v1.entities.Log;
import com.areatecnica.sigf_v1.entities.Usuario;
import com.areatecnica.sigf_v1.util.MyUtil;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

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
    private String usuario;
    private Usuario user;
    private List<Log> registroGuiasItems;
    private List<Log> servicioItems;
    private List<Log> tesoreriaItems;
    private List<Log> remuneracionesItems;
    private List<Log> boletosItems;
    private List<Log> empresariosItems;
    private List<Log> informesItems;
    private LogDaoImpl logDaoImpl;
    
    
    /**
     * Creates a new instance of IndexController
     */
    public IndexController() {

    }

    @PostConstruct
    private void init() {
        try {
            this.registroGuias = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("registroGuias");
            this.servicios = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("servicios");
            this.tesoreria = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tesoreria");
            this.remuneraciones = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("remuneraciones");
            this.boletos = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("boletos");
            this.empresario = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("empresario");
            this.informes = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("informes");
            this.usuario = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            
            this.logDaoImpl = new LogDaoImpl();
            
            this.registroGuiasItems = this.logDaoImpl.findByIdPrivilegioShort(2);
            this.servicioItems = this.logDaoImpl.findByIdPrivilegioShort(1);
            this.tesoreriaItems = this.logDaoImpl.findByIdPrivilegioShort(3);
            this.remuneracionesItems = this.logDaoImpl.findByIdPrivilegioShort(7);
            this.boletosItems = this.logDaoImpl.findByIdPrivilegioShort(6);
            this.empresariosItems = this.logDaoImpl.findByIdPrivilegioShort(5);
            this.informesItems = this.logDaoImpl.findByIdPrivilegioShort(4);
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Su sessión ha terminado");
            logout();
        }
    }

    public void logout() {
        System.err.println("Si llega al logout");
        String path = MyUtil.basePathLogin() + "views/webapp/login.xhtml";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
        context.addCallbackParam("loggedIn", false);
        context.addCallbackParam("loggetOut", true);
        context.addCallbackParam("path", path);
        
        try {
            facesContext.getExternalContext().redirect(path);
            //externalContext.redirect("http://stackoverflow.com");
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<Log> getRegistroGuiasItems() {
        return registroGuiasItems;
    }

    public void setRegistroGuiasItems(List<Log> registroGuiasItems) {
        this.registroGuiasItems = registroGuiasItems;
    }

    public List<Log> getServicioItems() {
        return servicioItems;
    }

    public void setServicioItems(List<Log> servicioItems) {
        this.servicioItems = servicioItems;
    }

    public List<Log> getTesoreriaItems() {
        return tesoreriaItems;
    }

    public void setTesoreriaItems(List<Log> tesoreriaItems) {
        this.tesoreriaItems = tesoreriaItems;
    }

    public List<Log> getRemuneracionesItems() {
        return remuneracionesItems;
    }

    public void setRemuneracionesItems(List<Log> remuneracionesItems) {
        this.remuneracionesItems = remuneracionesItems;
    }

    public List<Log> getBoletosItems() {
        return boletosItems;
    }

    public void setBoletosItems(List<Log> boletosItems) {
        this.boletosItems = boletosItems;
    }

    public List<Log> getEmpresariosItems() {
        return empresariosItems;
    }

    public void setEmpresariosItems(List<Log> empresariosItems) {
        this.empresariosItems = empresariosItems;
    }

    public List<Log> getInformesItems() {
        return informesItems;
    }

    public void setInformesItems(List<Log> informesItems) {
        this.informesItems = informesItems;
    }

}
