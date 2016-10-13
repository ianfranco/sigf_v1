/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.CargoBusDaoImpl;
import com.areatecnica.sigf_v1.entities.CargoBus;
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
@Named(value = "cargoBusController")
@ViewScoped
public class CargoBusController implements Serializable {

    private List<CargoBus> selector; 
    
    private CargoBusDaoImpl cargoBusDao;
    private List<CargoBus> items;
    private CargoBus selected;
    private int elcargo;
    private int suma;
    
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public CargoBusController() {        
        this.cargoBusDao = new CargoBusDaoImpl();
        this.selector = this.cargoBusDao.findGROUP();
        this.suma = 0;
        
    }

    public List<CargoBus> getItems() {
        return items;
    }

    public void setItems(List<CargoBus> items) {
        this.items = items;
    }

    public CargoBus getSelected() {
        return selected;
    }

    public void setSelected(CargoBus selected) {
        this.selected = selected;
    }
    
     public CargoBus prepareCreate(ActionEvent event) {
        CargoBus newAsignacionFamiliar;
        newAsignacionFamiliar = new CargoBus();
        this.selected = newAsignacionFamiliar;
        return newAsignacionFamiliar;
    }
     
    public void init(){
        suma = 0;
        if(elcargo>0){
            this.items = this.cargoBusDao.findByCargo(elcargo);
            
            for(CargoBus c:this.items){
                suma += c.getMontoCargoBus();
            }
            
        }
        
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.save(this.selected);
                tx.commit();
                this.items.add(selected);

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
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
                System.err.println("NULL:CargoBus");
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
                this.items.add(selected);

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage){
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<CargoBus> getSelector() {
        return selector;
    }

    public void setSelector(List<CargoBus> selector) {
        this.selector = selector;
    }

    public int getElcargo() {
        return elcargo;
    }

    public void setElcargo(int elcargo) {
        this.elcargo = elcargo;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }
}
