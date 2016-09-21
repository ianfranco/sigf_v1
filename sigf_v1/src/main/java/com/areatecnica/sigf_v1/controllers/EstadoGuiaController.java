/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EstadoGuiaDaoImpl;
import com.areatecnica.sigf_v1.entities.EstadoGuia;
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
@Named(value = "estadoGuiaController")
@ViewScoped
public class EstadoGuiaController implements Serializable {

    private EstadoGuiaDaoImpl estadoGuiaDao;
    private List<EstadoGuia> items;
    private EstadoGuia selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public EstadoGuiaController() {        
        this.estadoGuiaDao = new EstadoGuiaDaoImpl();
        this.items = this.estadoGuiaDao.findAll();
    }

    public List<EstadoGuia> getItems() {
        return items;
    }

    public void setItems(List<EstadoGuia> items) {
        this.items = items;
    }

    public EstadoGuia getSelected() {
        return selected;
    }

    public void setSelected(EstadoGuia selected) {
        this.selected = selected;
    }
    
     public EstadoGuia prepareCreate(ActionEvent event) {
        EstadoGuia newEstadoGuia;
        newEstadoGuia = new EstadoGuia();
        this.selected = newEstadoGuia;
        return newEstadoGuia;
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
                System.err.println("NULL:EstadoGuia");
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
                System.err.println("NULL:EstadoGuia");
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
