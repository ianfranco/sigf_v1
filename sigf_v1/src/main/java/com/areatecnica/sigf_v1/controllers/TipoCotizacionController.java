/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.MutualDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoCotizacionTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Mutual;
import com.areatecnica.sigf_v1.entities.TipoCotizacionTrabajador;
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
@Named(value = "tipoCotizacionTrabajadorController")
@SessionScoped
public class TipoCotizacionController implements Serializable {

    private TipoCotizacionTrabajadorDaoImpl tipoCotizacionDao;
    private List<TipoCotizacionTrabajador> items;
    private TipoCotizacionTrabajador selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public TipoCotizacionController() {        
        this.tipoCotizacionDao = new TipoCotizacionTrabajadorDaoImpl();
        this.items = this.tipoCotizacionDao.findAll();
    }

    public List<TipoCotizacionTrabajador> getItems() {
        return items;
    }

    public void setItems(List<TipoCotizacionTrabajador> items) {
        this.items = items;
    }

    public TipoCotizacionTrabajador getSelected() {
        return selected;
    }

    public void setSelected(TipoCotizacionTrabajador selected) {
        this.selected = selected;
    }
    
     public TipoCotizacionTrabajador prepareCreate(ActionEvent event) {
        TipoCotizacionTrabajador newTipoCotizacion;
        newTipoCotizacion = new TipoCotizacionTrabajador();
        this.selected = newTipoCotizacion;
        return newTipoCotizacion;
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
                System.err.println("SAVE:TipoCotizacion");
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
                System.err.println("SAVE:TipoCotizacion");
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
