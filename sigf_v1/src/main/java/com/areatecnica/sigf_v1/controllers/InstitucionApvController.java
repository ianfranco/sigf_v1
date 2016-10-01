/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.InstitucionAPVDaoImpl;
import com.areatecnica.sigf_v1.entities.InstitucionApv;
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
@Named(value = "institucionApvController")
@ViewScoped
public class InstitucionApvController implements Serializable {

    private InstitucionAPVDaoImpl institucionApvDao;
    private List<InstitucionApv> items;
    private InstitucionApv selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public InstitucionApvController() {        
        this.institucionApvDao = new InstitucionAPVDaoImpl();
        this.items = this.institucionApvDao.findAll();
    }

    public List<InstitucionApv> getItems() {
        return items;
    }

    public void setItems(List<InstitucionApv> items) {
        this.items = items;
    }

    public InstitucionApv getSelected() {
        return selected;
    }

    public void setSelected(InstitucionApv selected) {
        this.selected = selected;
    }
    
     public InstitucionApv prepareCreate(ActionEvent event) {
        InstitucionApv newInstitucionAPV;
        newInstitucionAPV = new InstitucionApv();
        this.selected = newInstitucionAPV;
        return newInstitucionAPV;
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
                System.err.println("SAVE:Apv");
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
                System.err.println("SAVE:Apv");
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
