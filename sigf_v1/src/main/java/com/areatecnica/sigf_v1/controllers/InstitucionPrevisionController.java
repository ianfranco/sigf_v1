/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.InstitucionPrevisionDaoImpl;
import com.areatecnica.sigf_v1.entities.InstitucionPrevision;
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
@Named(value = "institucionPrevisionController")
@SessionScoped
public class InstitucionPrevisionController implements Serializable {

    private InstitucionPrevisionDaoImpl institucionPrevisionDao;
    private List<InstitucionPrevision> items;
    private InstitucionPrevision selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public InstitucionPrevisionController() {        
        this.institucionPrevisionDao = new InstitucionPrevisionDaoImpl();
        this.items = this.institucionPrevisionDao.findAll();
    }

    public List<InstitucionPrevision> getItems() {
        return items;
    }

    public void setItems(List<InstitucionPrevision> items) {
        this.items = items;
    }

    public InstitucionPrevision getSelected() {
        return selected;
    }

    public void setSelected(InstitucionPrevision selected) {
        this.selected = selected;
    }
    
     public InstitucionPrevision prepareCreate(ActionEvent event) {
        InstitucionPrevision newInstitucionPrevision;
        newInstitucionPrevision = new InstitucionPrevision();
        this.selected = newInstitucionPrevision;
        return newInstitucionPrevision;
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
                System.err.println("NULL:InstitucionPrevision");
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
                this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:Trabajador");
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
