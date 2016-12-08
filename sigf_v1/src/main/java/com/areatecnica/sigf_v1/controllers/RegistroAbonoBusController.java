/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AbonoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoAbonoDaoImpl;
import com.areatecnica.sigf_v1.entities.AbonoBus;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.TipoAbono;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "registroAbonoBusController")
@ViewScoped
public class RegistroAbonoBusController implements Serializable {

    private TipoAbonoDaoImpl tipoAbonoDao;
    private AbonoBusDaoImpl abonoBusDaoImpl;
    private BusDaoImpl busDaoImpl;
   
    private AbonoBus selected;

    private List<TipoAbono> tipoAbonoItems;
    private List<AbonoBus> items;
    private Bus bus;
    private List<Bus> busItems;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroAbonoBusController() {
        this.tipoAbonoDao = new TipoAbonoDaoImpl();        
        this.tipoAbonoItems = this.tipoAbonoDao.findAll();
        
        this.busDaoImpl = new BusDaoImpl();
        this.busItems = this.busDaoImpl.findAll();

        this.abonoBusDaoImpl = new AbonoBusDaoImpl();
        this.items = this.abonoBusDaoImpl.findAll();

        this.selected = prepareCreate();

    }

    public AbonoBus prepareCreate() {        
        AbonoBus newCargoBus;
        newCargoBus = new AbonoBus();
        newCargoBus.setFechaInicioAbonoBus(new Date());
        newCargoBus.setMontoAbonoBus(0);
        newCargoBus.setNumeroCuotasAbonoBus(0);        
        return newCargoBus;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                Date fechaAux = this.selected.getFechaInicioAbonoBus();
                TipoAbono abonoAux = this.selected.getTipoAbono();
                
                this.selected.setFechaIngresoAbonoBus(new Date());
                this.selected.setActivoAbonoBus(Boolean.TRUE);
                this.selected.setNumeroCuotasAbonoBus(1);
                this.selected.setBus(bus);

                session.save(this.selected);
                tx.commit();
                this.items.add(0, selected);
                                                
                
                this.selected = new AbonoBus();
                this.bus = null;
                this.selected.setTipoAbono(abonoAux);
                this.selected.setMontoAbonoBus(0);
                this.selected.setNumeroCuotasAbonoBus(0);
                this.selected.setFechaInicioAbonoBus(fechaAux);
                
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
                tx.rollback();
                System.err.println("NULL:CargoBus");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
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

    public List<Bus> getBusItems() {
        return busItems;
    }

    public void setBusItems(List<Bus> trabajadorItems) {
        this.busItems = trabajadorItems;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public List<TipoAbono> getTipoAbonoItems() {
        return tipoAbonoItems;
    }

    public void setTipoAbonoItems(List<TipoAbono> tipoAbonoItems) {
        this.tipoAbonoItems = tipoAbonoItems;
    }
}
