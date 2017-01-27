/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.UsuarioDao;
import com.areatecnica.sigf_v1.dao.UsuarioDaoImpl;
import com.areatecnica.sigf_v1.entities.Privilegio;
import com.areatecnica.sigf_v1.entities.Rol;
import com.areatecnica.sigf_v1.entities.RolPrivilegio;
import com.areatecnica.sigf_v1.entities.Usuario;
import com.areatecnica.sigf_v1.util.MyUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private List<RolPrivilegio> itemsRP;
    private String nombreTerminal;

    private boolean registroGuias = false;
    private boolean servicios = false;
    private boolean tesoreria = false;
    private boolean remuneraciones = false;
    private boolean boletos = false;
    private boolean empresario = false;
    private boolean informes = false;

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
        
        String path = "";
        try {
            this.usuario = this.usuarioDao.login(this.usuario);

            if (!this.usuario.getActivo()) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "CUENTA DESACTIVADA", "Favor comunicarse con la administración");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido\n", this.usuario.getNombres());
                this.rol = this.usuario.getRol();
                loggedIn = true;

                this.itemsRP = new ArrayList<>(this.rol.getRolPrivilegios());
                System.err.println("CANTIDAD DE PRIVILEGIOS: " + this.itemsRP.size());
                for (RolPrivilegio p : this.itemsRP) {
                    System.err.println("ID DEL PRIVILEGIO:" + p.getPrivilegio().getIdPrivilegio());
                    switch (p.getPrivilegio().getIdPrivilegio()) {
                        case 1:
                            System.err.println("P:SERVICIOS");
                            this.servicios = true;
                            break;
                        case 2:
                            System.err.println("P:REGISTRO GUÍAS");
                            this.registroGuias = true;
                            break;
                        case 3:
                            System.err.println("P:TESORERÍA");
                            this.tesoreria = true;
                            break;
                        case 4:
                            System.err.println("P:INFORMES");
                            this.informes = true;
                            break;
                        case 5:
                            System.err.println("P:EMPRESARIOS");
                            this.empresario = true;
                            break;
                        case 6:
                            System.err.println("P:BOLETOS");
                            this.boletos = true;
                            break;
                        case 7:
                            System.err.println("P:REMUNERACIONES");
                            this.remuneraciones = true;
                            break;
                        default:
                            break;
                    }
                }

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", " " + this.usuario.getNombres() + " " + this.usuario.getApellidoPaterno());
                path = MyUtil.basePathLogin() + "views/webapp/index.xhtml";
            }

        } catch (NullPointerException ex) {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Usuario y/o Password incorrectos");
            if (this.usuario == null) {
                this.usuario = new Usuario();
            }
        }

        
        

            //message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido\n", this.usuario.getNombres());
            /*switch (this.rol.getIdRol()) {
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
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido\n", this.usuario.getNombres());
                    path = MyUtil.basePathLogin() + "views/indexServicio.xhtml";
                    break;
                default:
                    loggedIn = false;
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "USUARIO SIN ACCESO");
                    break;
            }*/
        /*} else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Usuario y/o Password incorrectos");
            if (this.usuario == null) {
                this.usuario = new Usuario();
            }
        }*/

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("registroGuias", registroGuias);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tesoreria", tesoreria);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("servicios", servicios);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("remuneraciones", remuneraciones);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boletos", boletos);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("empresario", empresario);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("informes", informes);

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
        context.addCallbackParam("path", path);
        context.addCallbackParam("registroGuias", registroGuias);
        context.addCallbackParam("servicios", servicios);
        context.addCallbackParam("tesoreria", tesoreria);
        context.addCallbackParam("boletos", boletos);
        context.addCallbackParam("remuneraciones", remuneraciones);
        context.addCallbackParam("empresario", empresario);
        context.addCallbackParam("informes", informes);
    }

    public String getNombreTerminal() {
        return nombreTerminal;
    }

    public void logout() {
        System.err.println("Si llega al logout");
        String path = MyUtil.basePathLogin() + "views/webapp/login.xhtml";
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

    public List<RolPrivilegio> getItemsRP() {
        return itemsRP;
    }

    public void setItemsRP(List<RolPrivilegio> itemsRP) {
        this.itemsRP = itemsRP;
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
