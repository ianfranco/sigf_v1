/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.DepartamentoDaoImpl;
import com.areatecnica.sigf_v1.entities.Departamento;
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
@Named(value = "departamentoController")
@ViewScoped
public class DepartamentoController implements Serializable {

    private DepartamentoDaoImpl departamentoDaoImpl;
    private List<Departamento> items;
    private Departamento selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public DepartamentoController() {        
        this.departamentoDaoImpl = new DepartamentoDaoImpl();
        this.items = this.departamentoDaoImpl.findAll();
    }

    public List<Departamento> getItems() {
        return items;
    }

    public void setItems(List<Departamento> items) {
        this.items = items;
    }

    public Departamento getSelected() {
        return selected;
    }

    public void setSelected(Departamento selected) {
        this.selected = selected;
    }
    
     public Departamento prepareCreate(ActionEvent event) {
        Departamento newDepartamento;
        newDepartamento = new Departamento();
        this.selected = newDepartamento;
        return newDepartamento;
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
                System.err.println("NULL:Departamento");
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
                System.err.println("NULL:Departamento");
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
