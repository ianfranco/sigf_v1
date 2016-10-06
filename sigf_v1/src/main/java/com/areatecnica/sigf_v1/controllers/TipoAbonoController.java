/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.TipoAbonoDaoImpl;
import com.areatecnica.sigf_v1.entities.TipoAbono;
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
@Named(value = "tipoAbonoController")
@SessionScoped
public class TipoAbonoController implements Serializable {

    private TipoAbonoDaoImpl tipoCargo;
    private List<TipoAbono> items;
    private TipoAbono selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public TipoAbonoController() {        
        this.tipoCargo = new TipoAbonoDaoImpl();
        this.items = this.tipoCargo.findAll();
    }

    public List<TipoAbono> getItems() {
        return items;
    }

    public void setItems(List<TipoAbono> items) {
        this.items = items;
    }

    public TipoAbono getSelected() {
        return selected;
    }

    public void setSelected(TipoAbono selected) {
        this.selected = selected;
    }
    
     public TipoAbono prepareCreate(ActionEvent event) {
        TipoAbono newTipoCargo;
        newTipoCargo = new TipoAbono();
        this.selected = newTipoCargo;
        return newTipoCargo;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.save(this.selected);
                tx.commit();
                this.items.add(selected);

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:TipoAbono");
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
                tx.rollback();
                System.err.println("NULL:TipoAbono");
            }
        } else {

        }
    }
    
    public void resetParents(){
        
    }
    
    public void delete(){
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.delete(this.selected);
                tx.commit();
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:TipoAbono");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage){
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }
}
