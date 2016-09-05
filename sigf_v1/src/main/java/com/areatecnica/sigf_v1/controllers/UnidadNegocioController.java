/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AsignacionFamiliarDaoImpl;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDaoImpl;
import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
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
@Named(value = "unidadNegocioController")
@SessionScoped
public class UnidadNegocioController implements Serializable {

    private UnidadNegocioDaoImpl unidadNegocioDao;
    private List<UnidadNegocio> items;
    private UnidadNegocio selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public UnidadNegocioController() {        
        this.unidadNegocioDao = new UnidadNegocioDaoImpl();
        this.items = this.unidadNegocioDao.findAll();
    }

    public List<UnidadNegocio> getItems() {
        return items;
    }

    public void setItems(List<UnidadNegocio> items) {
        this.items = items;
    }

    public UnidadNegocio getSelected() {
        return selected;
    }

    public void setSelected(UnidadNegocio selected) {
        this.selected = selected;
    }
    
     public UnidadNegocio prepareCreate(ActionEvent event) {
        UnidadNegocio newUnidadNegocio;
        newUnidadNegocio = new UnidadNegocio();
        this.selected = newUnidadNegocio;
        return newUnidadNegocio;
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
                System.err.println("NULL:UnidadNegocio");
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
                System.err.println("NULL:UnidadNegocio");
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
