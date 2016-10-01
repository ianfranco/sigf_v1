/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.CajaCompensacionDaoImpl;
import com.areatecnica.sigf_v1.entities.CajaCompensacion;
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
@Named(value = "cajaCompensacionController")
@SessionScoped
public class CajaCompensacionController implements Serializable {

    private CajaCompensacionDaoImpl cajaCompensacionDao;
    private List<CajaCompensacion> items;
    private CajaCompensacion selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public CajaCompensacionController() {        
        this.cajaCompensacionDao = new CajaCompensacionDaoImpl();
        this.items = this.cajaCompensacionDao.findAll();
    }

    public List<CajaCompensacion> getItems() {
        return items;
    }

    public void setItems(List<CajaCompensacion> items) {
        this.items = items;
    }

    public CajaCompensacion getSelected() {
        return selected;
    }

    public void setSelected(CajaCompensacion selected) {
        this.selected = selected;
    }
    
     public CajaCompensacion prepareCreate(ActionEvent event) {
        CajaCompensacion newCajaCompensacion;
        newCajaCompensacion = new CajaCompensacion();
        this.selected = newCajaCompensacion;
        return newCajaCompensacion;
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
                System.err.println("SAVE:Caja");
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
                System.err.println("SAVE:Caja");
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
