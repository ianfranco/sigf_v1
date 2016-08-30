/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.CajaCompensacionDaoImpl;
import com.areatecnica.sigf_v1.dao.MutualDaoImpl;
import com.areatecnica.sigf_v1.entities.Mutual;
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
public class MutualController implements Serializable {

    private MutualDaoImpl mutualDao;
    private List<Mutual> items;
    private Mutual selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public MutualController() {        
        this.mutualDao = new MutualDaoImpl();
        this.items = this.mutualDao.findAll();
    }

    public List<Mutual> getItems() {
        return items;
    }

    public void setItems(List<Mutual> items) {
        this.items = items;
    }

    public Mutual getSelected() {
        return selected;
    }

    public void setSelected(Mutual selected) {
        this.selected = selected;
    }
    
     public Mutual prepareCreate(ActionEvent event) {
        Mutual newCajaCompensacion;
        newCajaCompensacion = new Mutual();
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
