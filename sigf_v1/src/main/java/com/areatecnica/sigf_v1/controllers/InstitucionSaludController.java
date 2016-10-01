/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.InstitucionSaludDaoImpl;
import com.areatecnica.sigf_v1.entities.InstitucionSalud;
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
@Named(value = "institucionSaludController")
@ViewScoped
public class InstitucionSaludController implements Serializable {

    private InstitucionSaludDaoImpl institucionSaludDao;
    private List<InstitucionSalud> items;
    private InstitucionSalud selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public InstitucionSaludController() {        
        this.institucionSaludDao = new InstitucionSaludDaoImpl();
        this.items = this.institucionSaludDao.findAll();
    }

    public List<InstitucionSalud> getItems() {
        return items;
    }

    public void setItems(List<InstitucionSalud> items) {
        this.items = items;
    }

    public InstitucionSalud getSelected() {
        return selected;
    }

    public void setSelected(InstitucionSalud selected) {
        this.selected = selected;
    }
    
     public InstitucionSalud prepareCreate(ActionEvent event) {
        InstitucionSalud newInstitucionSalud;
        newInstitucionSalud = new InstitucionSalud();
        this.selected = newInstitucionSalud;
        return newInstitucionSalud;
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
                System.err.println("SAVE:Salud");
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
                System.err.println("SAVE:Salud");
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
