/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.FlotaDaoImpl;
import com.areatecnica.sigf_v1.entities.Flota;
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
@Named(value = "flotaController")
@SessionScoped
public class FlotaController implements Serializable {

    private FlotaDaoImpl flotaDao;
    private List<Flota> items;
    private Flota selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public FlotaController() {        
        this.flotaDao = new FlotaDaoImpl();
        this.items = this.flotaDao.findAll();
    }

    public List<Flota> getItems() {
        return items;
    }

    public void setItems(List<Flota> items) {
        this.items = items;
    }

    public Flota getSelected() {
        return selected;
    }

    public void setSelected(Flota selected) {
        this.selected = selected;
    }
    
     public Flota prepareCreate(ActionEvent event) {
        Flota newFlota;
        newFlota = new Flota();
        this.selected = newFlota;
        return newFlota;
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
                System.err.println("NULL:Flota");
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
                System.err.println("NULL:Flota");
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
