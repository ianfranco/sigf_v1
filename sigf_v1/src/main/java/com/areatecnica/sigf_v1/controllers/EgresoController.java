/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EgresoDaoImpl;
import com.areatecnica.sigf_v1.entities.Egreso;
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
@Named(value = "egresoController")
@SessionScoped
public class EgresoController implements Serializable {

    private EgresoDaoImpl egresoDao;
    private List<Egreso> items;
    private Egreso selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public EgresoController() {        
        this.egresoDao = new EgresoDaoImpl();
        this.items = this.egresoDao.findAll();
    }

    public List<Egreso> getItems() {
        return items;
    }

    public void setItems(List<Egreso> items) {
        this.items = items;
    }

    public Egreso getSelected() {
        return selected;
    }

    public void setSelected(Egreso selected) {
        this.selected = selected;
    }
    
     public Egreso prepareCreate(ActionEvent event) {
        Egreso newEgreso;
        newEgreso = new Egreso();
        this.selected = newEgreso;
        return newEgreso;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(selected);
                this.selected = null;
            } catch (HibernateException e) {
                System.err.println("NULL:Egreso");
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
                System.err.println("NULL:Egreso");
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
