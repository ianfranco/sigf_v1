/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.ComunaDaoImpl;
import com.areatecnica.sigf_v1.entities.Comuna;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "comunaController")
@ViewScoped
public class ComunaController implements Serializable {

    private ComunaDaoImpl comunaDao;
    private List<Comuna> items;
    private Comuna selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public ComunaController() {        
        this.comunaDao = new ComunaDaoImpl();
        this.items = this.comunaDao.findAll();
    }

    public List<Comuna> getItems() {
        return items;
    }

    public void setItems(List<Comuna> items) {
        this.items = items;
    }

    public Comuna getSelected() {
        return selected;
    }

    public void setSelected(Comuna selected) {
        this.selected = selected;
    }
    
     public Comuna prepareCreate(ActionEvent event) {
        Comuna newComuna;
        newComuna = new Comuna();
        this.selected = newComuna;
        return newComuna;
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
                System.err.println("NULL:Comuna");
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
                tx.rollback();
                System.err.println("NULL:Comuna");
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
