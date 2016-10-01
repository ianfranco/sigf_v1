/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AsignacionFamiliarDaoImpl;
import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
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
@Named(value = "asignacionFamiliarController")
@SessionScoped
public class AsignacionFamiliarController implements Serializable {

    private AsignacionFamiliarDaoImpl asignacionFamiliarDao;
    private List<AsignacionFamiliar> items;
    private AsignacionFamiliar selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public AsignacionFamiliarController() {        
        this.asignacionFamiliarDao = new AsignacionFamiliarDaoImpl();
        this.items = this.asignacionFamiliarDao.findAll();
    }

    public List<AsignacionFamiliar> getItems() {
        return items;
    }

    public void setItems(List<AsignacionFamiliar> items) {
        this.items = items;
    }

    public AsignacionFamiliar getSelected() {
        return selected;
    }

    public void setSelected(AsignacionFamiliar selected) {
        this.selected = selected;
    }
    
     public AsignacionFamiliar prepareCreate(ActionEvent event) {
        AsignacionFamiliar newAsignacionFamiliar;
        newAsignacionFamiliar = new AsignacionFamiliar();
        this.selected = newAsignacionFamiliar;
        return newAsignacionFamiliar;
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
                tx.rollback();
                System.err.println("NULL:AsignacionFamiliar");
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
                System.err.println("NULL:AsignacionFamiliar");
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
