/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.UsuarioDao;
import com.areatecnica.sigf_v1.dao.UsuarioDaoImpl;
import com.areatecnica.sigf_v1.entities.Rol;
import com.areatecnica.sigf_v1.entities.Usuario;
import com.areatecnica.sigf_v1.util.MyUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ian Franco
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private Usuario usuario;
    
    private UsuarioDao usuarioDao;
    private Rol rol;
    private String nombreTerminal;

    /**
     * Creates a new instance of loginBean
     */
    public LoginBean() {
        this.usuarioDao = new UsuarioDaoImpl();
        if (this.usuario == null) {
            this.usuario = new Usuario();
            this.rol = new Rol();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;

        this.usuario = this.usuarioDao.login(this.usuario);
        String path = "";
        if (this.usuario != null) {

            this.rol = this.usuario.getRol();
            
            switch (this.rol.getIdRol()) {
                case 1:
                    loggedIn = true;
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", " " + this.usuario.getNombres() + " " + this.usuario.getApellidoPaterno());
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido\n" + this.usuario.getRol().getNombreRol(), this.usuario.getNombres());
                    path = MyUtil.basePathLogin() + "views/indexBoletos.xhtml";
                    break;
                case 2:
                    this.nombreTerminal = this.usuario.getTerminal().getNombreTerminal();
                    
                    loggedIn = true;
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", " " + this.usuario.getNombres() + " " + this.usuario.getApellidoPaterno());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("NombreTerminal", " " + this.usuario.getTerminal().getNombreTerminal());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idTerminal", this.usuario.getTerminal().getIdTerminal());
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido\n" + this.usuario.getRol().getNombreRol(), this.usuario.getNombres());
                    path = MyUtil.basePathLogin() + "views/indexInspector.xhtml";
                    break;
                case 3:
                    loggedIn = true;
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", " " + this.usuario.getNombres() + " " + this.usuario.getApellidoPaterno());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("NombreTerminal", " " + this.usuario.getTerminal().getNombreTerminal());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idTerminal", this.usuario.getTerminal().getIdTerminal());
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido\n" , this.usuario.getNombres());
                    path = MyUtil.basePathLogin() + "views/indexServicio.xhtml";
                    break;
                default:
                    loggedIn = false;
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "USUARIO SIN ACCESO");
                    break;
            }

        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Usuario y/o Password incorrectos");
            if (this.usuario == null) {
                this.usuario = new Usuario();
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
        context.addCallbackParam("path", path);
        
    }

    public String getNombreTerminal() {
        return nombreTerminal;
    }

    public void logout() {
        System.err.println("Si llega al logout");
        String path = MyUtil.basePathLogin() + "login.xhtml";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();

        context.addCallbackParam("loggetOut", true);
        context.addCallbackParam("path", path);
    }

    public String getUserName() {
        RequestContext context = RequestContext.getCurrentInstance();
        String nombre = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return nombre;
    }
}
