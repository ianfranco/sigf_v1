/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.TipoContratoDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.TipoContrato;
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
@Named(value = "tipoContratoController")
@SessionScoped
public class TipoContratoController implements Serializable {

    private TipoContratoDaoImpl tipoTrabajador;
    private List<TipoContrato> items;
    private TipoContrato selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public TipoContratoController() {        
        this.tipoTrabajador = new TipoContratoDaoImpl();
        this.items = this.tipoTrabajador.findAll();
    }

    public List<TipoContrato> getItems() {
        return items;
    }

    public void setItems(List<TipoContrato> items) {
        this.items = items;
    }

    public TipoContrato getSelected() {
        return selected;
    }

    public void setSelected(TipoContrato selected) {
        this.selected = selected;
    }
    
     public TipoContrato prepareCreate(ActionEvent event) {
        TipoContrato newTipoContrato;
        newTipoContrato = new TipoContrato();
        this.selected = newTipoContrato;
        return newTipoContrato;
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
                tx.rollback();
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
                System.err.println("NULL:TipoTrabajador");
                tx.rollback();
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
