/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.IntervalosAdministracionDaoImpl;
import com.areatecnica.sigf_v1.entities.IntervalosAdministracion;
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
@Named(value = "intervalosAdministracionController")
@ViewScoped
public class IntervalosAdministracionController implements Serializable {

    private IntervalosAdministracionDaoImpl asignacionFamiliarDao;
    private List<IntervalosAdministracion> items;
    private IntervalosAdministracion selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public IntervalosAdministracionController() {        
        this.asignacionFamiliarDao = new IntervalosAdministracionDaoImpl();
        this.items = this.asignacionFamiliarDao.findAll();
    }

    public List<IntervalosAdministracion> getItems() {
        return items;
    }

    public void setItems(List<IntervalosAdministracion> items) {
        this.items = items;
    }

    public IntervalosAdministracion getSelected() {
        return selected;
    }

    public void setSelected(IntervalosAdministracion selected) {
        this.selected = selected;
    }
    
     public IntervalosAdministracion prepareCreate(ActionEvent event) {
        IntervalosAdministracion newIntervalo;
        newIntervalo = new IntervalosAdministracion();
        this.selected = newIntervalo;
        return newIntervalo;
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
                System.err.println("NULL:IntervalosAdministracion");
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
                System.err.println("NULL:IntervalosAdministracion");
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
