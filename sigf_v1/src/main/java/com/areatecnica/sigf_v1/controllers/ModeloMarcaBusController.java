/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.ModeloMarcaBusDaoImpl;
import com.areatecnica.sigf_v1.entities.ModeloMarcaBus;
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
@Named(value = "modeloMarcaBusController")
@ViewScoped
public class ModeloMarcaBusController implements Serializable {

    private ModeloMarcaBusDaoImpl modeloMarcaBus;
    private List<ModeloMarcaBus> items;
    private ModeloMarcaBus selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public ModeloMarcaBusController() {        
        this.modeloMarcaBus = new ModeloMarcaBusDaoImpl();
        this.items = this.modeloMarcaBus.findAll();
    }

    public List<ModeloMarcaBus> getItems() {
        return items;
    }

    public void setItems(List<ModeloMarcaBus> items) {
        this.items = items;
    }

    public ModeloMarcaBus getSelected() {
        return selected;
    }

    public void setSelected(ModeloMarcaBus selected) {
        this.selected = selected;
    }
    
     public ModeloMarcaBus prepareCreate(ActionEvent event) {
        ModeloMarcaBus newModeloMarca;
        newModeloMarca = new ModeloMarcaBus();
        this.selected = newModeloMarca;
        return newModeloMarca;
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
                System.err.println("NULL:ModeloMarcaBus");
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
                tx.rollback();
                System.err.println("NULL:ModeloMarcaBus");
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
