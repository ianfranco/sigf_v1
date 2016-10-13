/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.SindicatoDaoImpl;
import com.areatecnica.sigf_v1.entities.Sindicato;
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
@Named(value = "sindicatoController")
@ViewScoped
public class SindicatoController implements Serializable {

    private SindicatoDaoImpl sindicatoDao;
    private List<Sindicato> items;
    private Sindicato selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public SindicatoController() {        
        this.sindicatoDao = new SindicatoDaoImpl();
        this.items = this.sindicatoDao.findAll();
    }

    public List<Sindicato> getItems() {
        return items;
    }

    public void setItems(List<Sindicato> items) {
        this.items = items;
    }

    public Sindicato getSelected() {
        return selected;
    }

    public void setSelected(Sindicato selected) {
        this.selected = selected;
    }
    
     public Sindicato prepareCreate(ActionEvent event) {
        Sindicato newSindicato;
        newSindicato = new Sindicato();
        this.selected = newSindicato;
        return newSindicato;
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
                System.err.println("NULL:Sindicato");
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
                System.err.println("NULL:Sindicato");
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
