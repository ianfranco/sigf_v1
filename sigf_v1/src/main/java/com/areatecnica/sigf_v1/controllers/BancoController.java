/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.BancoDaoImpl;
import com.areatecnica.sigf_v1.entities.Banco;
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
@Named(value = "bancoController")
@ViewScoped
public class BancoController implements Serializable {

    private BancoDaoImpl bancoDao;
    private List<Banco> items;
    private Banco selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public BancoController() {        
        this.bancoDao = new BancoDaoImpl();
        this.items = this.bancoDao.findAll();
    }

    public List<Banco> getItems() {
        return items;
    }

    public void setItems(List<Banco> items) {
        this.items = items;
    }

    public Banco getSelected() {
        return selected;
    }

    public void setSelected(Banco selected) {
        this.selected = selected;
    }
    
     public Banco prepareCreate(ActionEvent event) {
        Banco newBanco;
        newBanco = new Banco();
        this.selected = newBanco;
        return newBanco;
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
                tx.rollback();
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
                System.err.println("SAVE:Banco");
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
