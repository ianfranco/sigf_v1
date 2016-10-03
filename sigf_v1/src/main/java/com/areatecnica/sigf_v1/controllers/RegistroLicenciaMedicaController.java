/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.FeriadoLegalDaoImpl;
import com.areatecnica.sigf_v1.dao.LicenciaMedicaDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.FeriadoLegal;
import com.areatecnica.sigf_v1.entities.LicenciaMedica;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "registroLicenciaMedicaController")
@SessionScoped
public class RegistroLicenciaMedicaController implements Serializable {

    private LicenciaMedicaDaoImpl licenciaMedicaDao;
    private TrabajadorDao trabajadorDao;

    private List<Trabajador> trabajadorItems;
    private List<LicenciaMedica> items;
    private Trabajador trabajador;
    private LicenciaMedica selected;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroLicenciaMedicaController() {
        this.trabajadorDao = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDao.findAll();

        this.licenciaMedicaDao = new LicenciaMedicaDaoImpl();
        this.items = this.licenciaMedicaDao.findWithLimit();

        this.selected = prepareCreate();
    }

    public List<LicenciaMedica> getItems() {
        return items;
    }

    public void setItems(List<LicenciaMedica> items) {
        this.items = items;
    }

    public LicenciaMedica getSelected() {
        return selected;
    }

    public void setSelected(LicenciaMedica selected) {
        this.selected = selected;
    }

    public LicenciaMedica prepareCreate() {
        LicenciaMedica newFeriadoLegal;

        newFeriadoLegal = new LicenciaMedica();        

        return newFeriadoLegal;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {

                if (diferenciaEnDias2(this.selected.getFechaHastaReposo(), this.selected.getFechaDesdeReposo())>0) {
                    this.selected.setTrabajador(trabajador);
                    this.selected.setFechaIngresoLicencia(new Date());
                    this.selected.setFechaRecepcionLicencia(new Date());
                    this.selected.setFechaEmisionLicencia(new Date());
                    session.save(this.selected);
                    tx.commit();

                    this.items.add(0, selected);
                    this.selected = null;
                    this.trabajador = null;
                    this.selected = prepareCreate();
                } else {
                    JsfUtil.addErrorMessage("Fechas mal ingresadas");
                }

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Licencia Médica");
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
                System.err.println("NULL:Licencia Médica");
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
                System.err.println("DELETE:Licencia Médica");
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

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public static int diferenciaEnDias2(Date fechaMayor, Date fechaMenor) {        
        long diferenciaEnms = fechaMayor.getTime() - fechaMenor.getTime();
        long dias = diferenciaEnms / (1000 * 60 * 60 * 24);
        return (int) dias;
    }

}
