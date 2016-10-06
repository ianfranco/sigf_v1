/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.TipoCargoDaoImpl;
import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
import com.areatecnica.sigf_v1.entities.TipoCargo;
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
@Named(value = "tipoCargoController")
@SessionScoped
public class TipoCargoController implements Serializable {

    private TipoCargoDaoImpl tipoCargo;
    private List<TipoCargo> items;
    private TipoCargo selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public TipoCargoController() {        
        this.tipoCargo = new TipoCargoDaoImpl();
        this.items = this.tipoCargo.findAll();
    }

    public List<TipoCargo> getItems() {
        return items;
    }

    public void setItems(List<TipoCargo> items) {
        this.items = items;
    }

    public TipoCargo getSelected() {
        return selected;
    }

    public void setSelected(TipoCargo selected) {
        this.selected = selected;
    }
    
     public TipoCargo prepareCreate(ActionEvent event) {
        TipoCargo newTipoCargo;
        newTipoCargo = new TipoCargo();
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
                System.err.println("NULL:TipoCargo");
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
                System.err.println("NULL:TipoCargo");
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

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:TipoCargo");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage){
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }
}
