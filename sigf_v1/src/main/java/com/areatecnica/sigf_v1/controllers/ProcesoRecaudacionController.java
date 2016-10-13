/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.ProcesoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
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
@Named(value = "procesoRecaudacionController")
@ViewScoped
public class ProcesoRecaudacionController implements Serializable {

    private ProcesoRecaudacionDaoImpl procesoRecaudacionDao;
    private List<ProcesoRecaudacion> items;
    private ProcesoRecaudacion selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public ProcesoRecaudacionController() {        
        this.procesoRecaudacionDao = new ProcesoRecaudacionDaoImpl();
        this.items = this.procesoRecaudacionDao.findAll();
    }

    public List<ProcesoRecaudacion> getItems() {
        return items;
    }

    public void setItems(List<ProcesoRecaudacion> items) {
        this.items = items;
    }

    public ProcesoRecaudacion getSelected() {
        return selected;
    }

    public void setSelected(ProcesoRecaudacion selected) {
        this.selected = selected;
    }
    
     public ProcesoRecaudacion prepareCreate(ActionEvent event) {
        ProcesoRecaudacion newProcesoRecaudacion;
        newProcesoRecaudacion = new ProcesoRecaudacion();
        this.selected = newProcesoRecaudacion;
        return newProcesoRecaudacion;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                
                this.selected.setActivo(Boolean.TRUE);                
                
                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(selected);
                this.selected = null;
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:ProcesoRecaudacion");
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
                System.err.println("NULL:ProcesoRecaudacion");
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
