/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AsignacionFamiliarDaoImpl;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajador;
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
@Named(value = "descuentoTrabajadorController")
@SessionScoped
public class DescuentoTrabajadorController implements Serializable {

    private DescuentoTrabajadorDaoImpl descuentoTrabajadorDao;
    private List<DescuentoTrabajador> items;
    private DescuentoTrabajador selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public DescuentoTrabajadorController() {        
        this.descuentoTrabajadorDao = new DescuentoTrabajadorDaoImpl();
        this.items = this.descuentoTrabajadorDao.findAll();
    }

    public List<DescuentoTrabajador> getItems() {
        return items;
    }

    public void setItems(List<DescuentoTrabajador> items) {
        this.items = items;
    }

    public DescuentoTrabajador getSelected() {
        return selected;
    }

    public void setSelected(DescuentoTrabajador selected) {
        this.selected = selected;
    }
    
     public DescuentoTrabajador prepareCreate(ActionEvent event) {
        DescuentoTrabajador newDescuentoTrabajador;
        newDescuentoTrabajador = new DescuentoTrabajador();
        this.selected = newDescuentoTrabajador;
        return newDescuentoTrabajador;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngreso(new Date());
                session.saveOrUpdate(this.selected);
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
