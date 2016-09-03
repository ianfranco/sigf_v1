/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AsignacionFamiliarDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
import com.areatecnica.sigf_v1.entities.TipoTrabajador;
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
@Named(value = "tipoTrabajadorController")
@SessionScoped
public class TipoTrabajadorController implements Serializable {

    private TipoTrabajadorDaoImpl tipoTrabajador;
    private List<TipoTrabajador> items;
    private TipoTrabajador selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public TipoTrabajadorController() {        
        this.tipoTrabajador = new TipoTrabajadorDaoImpl();
        this.items = this.tipoTrabajador.findAll();
    }

    public List<TipoTrabajador> getItems() {
        return items;
    }

    public void setItems(List<TipoTrabajador> items) {
        this.items = items;
    }

    public TipoTrabajador getSelected() {
        return selected;
    }

    public void setSelected(TipoTrabajador selected) {
        this.selected = selected;
    }
    
     public TipoTrabajador prepareCreate(ActionEvent event) {
        TipoTrabajador newTipoTrabajador;
        newTipoTrabajador = new TipoTrabajador();
        this.selected = newTipoTrabajador;
        return newTipoTrabajador;
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
                System.err.println("NULL:TipoTrabajador");
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
                this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:TipoTrabajador");
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
