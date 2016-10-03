/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.DiaTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoDiaTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.DiaTrabajador;
import com.areatecnica.sigf_v1.entities.TipoDiaTrabajador;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "registroDiaTrabajadorController")
@SessionScoped
public class RegistroDiaTrabajadorController implements Serializable {

    private DiaTrabajadorDaoImpl diaTrabajadorDao;
    private TrabajadorDao trabajadorDao;
    private TipoDiaTrabajadorDaoImpl tipoDiaTrabajadorDao;

    private List<DiaTrabajador> items;
    private List<TipoDiaTrabajador> tipoDiaItems;
    private List<Trabajador> trabajadorItems;
    private DiaTrabajador selected;
    private TipoDiaTrabajador tipoDiaTrabajador;
    private Trabajador trabajador;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroDiaTrabajadorController() {
        this.trabajadorDao = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDao.findAll();

        this.tipoDiaTrabajadorDao = new TipoDiaTrabajadorDaoImpl();
        this.tipoDiaItems = this.tipoDiaTrabajadorDao.findAll();

        this.diaTrabajadorDao = new DiaTrabajadorDaoImpl();
        this.items = this.diaTrabajadorDao.findWithLimit();

        this.selected = prepareCreate();
    }

    public List<DiaTrabajador> getItems() {
        return items;
    }

    public void setItems(List<DiaTrabajador> items) {
        this.items = items;
    }

    public DiaTrabajador getSelected() {
        return selected;
    }

    public void setSelected(DiaTrabajador selected) {
        this.selected = selected;
    }

    public DiaTrabajador prepareCreate() {
        DiaTrabajador newDiaTrabajador;
        newDiaTrabajador = new DiaTrabajador();

        return newDiaTrabajador;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setTipoDiaTrabajador(tipoDiaTrabajador);
                this.selected.setTrabajador(trabajador);
                session.save(this.selected);
                tx.commit();
                this.items.add(0, selected);
                this.selected = null;
                this.trabajador = null;
                this.selected = prepareCreate();

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:DiaTrabajador");
                JsfUtil.addErrorMessage(e.getMessage());
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
                System.err.println("NULL:DiaTrabajador");
            }
        } else {

        }
    }

    public void resetParents() {

    }

    public void delete() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.delete(this.selected);
                tx.commit();
                
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                System.err.println("DELETE:Egreso");
                tx.rollback();
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public List<TipoDiaTrabajador> getTipoDiaItems() {
        return tipoDiaItems;
    }

    public void setTipoDiaItems(List<TipoDiaTrabajador> tipoDiaItems) {
        this.tipoDiaItems = tipoDiaItems;
    }

    public TipoDiaTrabajador getTipoDiaTrabajador() {
        return tipoDiaTrabajador;
    }

    public void setTipoDiaTrabajador(TipoDiaTrabajador tipoDiaTrabajador) {
        this.tipoDiaTrabajador = tipoDiaTrabajador;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
}
