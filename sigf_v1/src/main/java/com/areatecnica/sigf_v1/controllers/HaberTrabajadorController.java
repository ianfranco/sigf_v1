/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.HaberTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.HaberTrabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "haberTrabajadorController")
@SessionScoped
public class HaberTrabajadorController implements Serializable {

    private HaberTrabajadorDaoImpl haberTrabajadorDao;
    private List<HaberTrabajador> items;
    private HaberTrabajador selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public HaberTrabajadorController() {        
        this.haberTrabajadorDao = new HaberTrabajadorDaoImpl();
        this.items = this.haberTrabajadorDao.findAll();
    }

    public List<HaberTrabajador> getItems() {
        return items;
    }

    public void setItems(List<HaberTrabajador> items) {
        this.items = items;
    }

    public HaberTrabajador getSelected() {
        return selected;
    }

    public void setSelected(HaberTrabajador selected) {
        this.selected = selected;
    }
    
     public HaberTrabajador prepareCreate(ActionEvent event) {
        HaberTrabajador newHaberTrabajador;
        newHaberTrabajador = new HaberTrabajador();
        this.selected = newHaberTrabajador;
        return newHaberTrabajador;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngreso(new Date());
                session.save(this.selected);
                tx.commit();
                this.items.add(selected);

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:HaberTrabajador");
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
                System.err.println("NULL:HaberTrabajador");
            }
        } else {

        }
    }
    
    public void resetParents(){
        
    }
    
    public void delete(){
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.delete(this.selected);
                tx.commit();
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:HaberTrabajador");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage){
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }
}
