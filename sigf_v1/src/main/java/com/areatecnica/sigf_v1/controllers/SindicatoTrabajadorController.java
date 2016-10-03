/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.SindicatoDaoImpl;
import com.areatecnica.sigf_v1.dao.SindicatoTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Sindicato;
import com.areatecnica.sigf_v1.entities.SindicatoTrabajador;
import com.areatecnica.sigf_v1.entities.Trabajador;
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
@Named(value = "sindicatoTrabajadorController")
@SessionScoped
public class SindicatoTrabajadorController implements Serializable {

    private SindicatoTrabajadorDaoImpl sindicatoTrabajadorDao;
    private SindicatoDaoImpl sindicatoDao;
    private TrabajadorDaoImpl trabajadorDaoImpl;
    private List<SindicatoTrabajador> items;
    private List<Sindicato> sindicatoItems;
    private List<Trabajador> trabajadorItems;
    private SindicatoTrabajador selected;
    private Trabajador trabajador;
    
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public SindicatoTrabajadorController() {        
        this.sindicatoTrabajadorDao = new SindicatoTrabajadorDaoImpl();
        this.items = this.sindicatoTrabajadorDao.findAll();
        
        this.sindicatoDao = new SindicatoDaoImpl();
        this.sindicatoItems = this.sindicatoDao.findAll();
    }

    public List<SindicatoTrabajador> getItems() {
        return items;
    }

    public void setItems(List<SindicatoTrabajador> items) {
        this.items = items;
    }

    public SindicatoTrabajador getSelected() {
        return selected;
    }

    public void setSelected(SindicatoTrabajador selected) {
        this.selected = selected;
    }
    
     public SindicatoTrabajador prepareCreate(ActionEvent event) {
        SindicatoTrabajador newSindicato;
        newSindicato = new SindicatoTrabajador();
        this.selected = newSindicato;
        
        this.trabajadorDaoImpl = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDaoImpl.findAll();
        
        return newSindicato;
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
                System.err.println("NULL:SindicatoTrabajador");
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
                System.err.println("NULL:SindicatoTrabajador");
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
                this.selected = null;
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:SindicatoTrabajador");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage){
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<Sindicato> getSindicatoItems() {
        return sindicatoItems;
    }

    public void setSindicatoItems(List<Sindicato> sindicatoItems) {
        this.sindicatoItems = sindicatoItems;
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
}
