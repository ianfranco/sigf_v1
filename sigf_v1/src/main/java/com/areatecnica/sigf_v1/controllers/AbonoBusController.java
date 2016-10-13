/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AbonoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.CargoBusDaoImpl;
import com.areatecnica.sigf_v1.entities.AbonoBus;
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
@Named(value = "abonoBusController")
@ViewScoped
public class AbonoBusController implements Serializable {

    private List<AbonoBus> selector; 
    
    private AbonoBusDaoImpl abonoBusDao;
    private List<AbonoBus> items;
    private AbonoBus selected;
    private int elcargo;
    private int suma;
    
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public AbonoBusController() {        
        this.abonoBusDao = new AbonoBusDaoImpl();
        this.selector = this.abonoBusDao.findGROUP();
        this.suma = 0;
        
    }

    public List<AbonoBus> getItems() {
        return items;
    }

    public void setItems(List<AbonoBus> items) {
        this.items = items;
    }

    public AbonoBus getSelected() {
        return selected;
    }

    public void setSelected(AbonoBus selected) {
        this.selected = selected;
    }
    
     public AbonoBus prepareCreate(ActionEvent event) {
        AbonoBus newAbono;
        newAbono = new AbonoBus();
        this.selected = newAbono;
        return newAbono;
    }
     
    public void init(){
        suma = 0;
        if(elcargo>0){
            this.items = this.abonoBusDao.findByCargo(elcargo);
            
            for(AbonoBus c:this.items){
                suma += c.getMontoAbonoBus();
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
                System.err.println("NULL:AbonoBus");
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

    public List<AbonoBus> getSelector() {
        return selector;
    }

    public void setSelector(List<AbonoBus> selector) {
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
