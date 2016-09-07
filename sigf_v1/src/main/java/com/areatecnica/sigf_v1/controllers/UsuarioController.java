/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.UsuarioDaoImpl;
import com.areatecnica.sigf_v1.entities.Usuario;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private UsuarioDaoImpl asignacionFamiliarDao;
    private List<Usuario> items;
    private Usuario selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public UsuarioController() {        
        this.asignacionFamiliarDao = new UsuarioDaoImpl();
        this.items = this.asignacionFamiliarDao.findAll();
    }

    public List<Usuario> getItems() {
        return items;
    }

    public void setItems(List<Usuario> items) {
        this.items = items;
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }
    
     public Usuario prepareCreate(ActionEvent event) {
        Usuario newUsuario;
        newUsuario = new Usuario();
        this.selected = newUsuario;
        return newUsuario;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:Usuario");
            }
        } else {

        }
    }

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.saveOrUpdate(this.selected);
                tx.commit();

            } catch (HibernateException e) {
                System.err.println("NULL:Usuario");
            }
        } else {

        }
    }
    
    public void resetParents(){
        
    }
    
    public void delete(){
        
    }

    public String getComponentMessages(String clientComponent, String defaultMessage){
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }
}
