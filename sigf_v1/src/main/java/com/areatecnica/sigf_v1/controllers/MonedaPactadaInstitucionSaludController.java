/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.MonedaPactadaInstitucionSaludImpl;
import com.areatecnica.sigf_v1.entities.CajaCompensacion;
import com.areatecnica.sigf_v1.entities.MonedaPactadaInstitucionSalud;
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
@Named(value = "monedaPactadaInstitucionSaludController")
@SessionScoped
public class MonedaPactadaInstitucionSaludController implements Serializable {

    private MonedaPactadaInstitucionSaludImpl monedaPactada;
    private List<MonedaPactadaInstitucionSalud> items;
    private MonedaPactadaInstitucionSalud selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public MonedaPactadaInstitucionSaludController() {        
        this.monedaPactada = new MonedaPactadaInstitucionSaludImpl();
        this.items = this.monedaPactada.findAll();
    }

    public List<MonedaPactadaInstitucionSalud> getItems() {
        return items;
    }

    public void setItems(List<MonedaPactadaInstitucionSalud> items) {
        this.items = items;
    }

    public MonedaPactadaInstitucionSalud getSelected() {
        return selected;
    }

    public void setSelected(MonedaPactadaInstitucionSalud selected) {
        this.selected = selected;
    }
    
     public MonedaPactadaInstitucionSalud prepareCreate(ActionEvent event) {
        MonedaPactadaInstitucionSalud newCajaCompensacion;
        newCajaCompensacion = new MonedaPactadaInstitucionSalud();
        this.selected = newCajaCompensacion;
        return newCajaCompensacion;
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
                System.err.println("NULL:InstitucionPrevision");
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
                System.err.println("SAVE:Moneda");
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
