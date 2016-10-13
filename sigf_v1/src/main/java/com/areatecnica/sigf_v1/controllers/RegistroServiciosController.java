/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.ServicioDao;
import com.areatecnica.sigf_v1.dao.ServicioDaoImpl;
import com.areatecnica.sigf_v1.entities.Servicio;
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
 * @author Ian Franco
 */
@Named(value = "registroServiciosController")
@ViewScoped
public class RegistroServiciosController implements Serializable {

    private List<Servicio> items;
    private ServicioDao servicioDao;
    private Servicio selected;

    /**
     * Creates a new instance of RegistroTerminalesController
     */
    public RegistroServiciosController() {

        this.servicioDao = new ServicioDaoImpl();
        this.items = this.servicioDao.findAll();
    }

    public Servicio prepareCreate(ActionEvent event) {
        Servicio newServicio;
        try {
            newServicio = new Servicio();
            this.selected = newServicio;

            return newServicio;
        } catch (Exception ex) {

        }
        return null;
    }

    public String delete() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            try {

                session.delete(this.selected);
                tx.commit();
                
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            }
        }
        return null;        
    }

    public void saveNew() {

        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            try {

                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(this.selected);
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            }
        } else {
            System.err.println("ES NULO EL VALOR SELECTED!!!");
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
                e.printStackTrace();
                tx.rollback();
            }
        }        
    }

    public List<Servicio> getItems() {
        return items;
    }

    public void setItems(List<Servicio> items) {
        this.items = items;
    }

    public Servicio getSelected() {
        return selected;
    }

    public void setSelected(Servicio selected) {
        this.selected = selected;
    }

}
