/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.HaberTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.HaberTrabajadorLiquidacionDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.HaberTrabajador;
import com.areatecnica.sigf_v1.entities.HaberTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.entities.Trabajador;
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
@Named(value = "haberTrabajadorLiquidacionController")
@ViewScoped
public class HaberTrabajadorLiquidacionController implements Serializable {

    private HaberTrabajadorDaoImpl haberTrabajadorDaoImpl;
    private HaberTrabajadorLiquidacionDaoImpl haberTrabajadorLiquidacionDaoImpl;
    private TrabajadorDaoImpl trabajadorDaoImpl;

    private HaberTrabajador haberTrabajador;
    private HaberTrabajadorLiquidacion selected;

    private List<HaberTrabajadorLiquidacion> items;
    private List<HaberTrabajador> haberItems;
    private List<Trabajador> trabajadorItems;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public HaberTrabajadorLiquidacionController() {
        this.haberTrabajadorDaoImpl = new HaberTrabajadorDaoImpl();
        this.haberItems = this.haberTrabajadorDaoImpl.findAll();

        this.trabajadorDaoImpl = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDaoImpl.findAll();

        this.haberTrabajadorLiquidacionDaoImpl = new HaberTrabajadorLiquidacionDaoImpl();
        this.items = this.haberTrabajadorLiquidacionDaoImpl.findWithLimit();

        this.selected = prepareCreate();

    }

    public HaberTrabajador getHaberTrabajador() {
        return haberTrabajador;
    }

    public void setHaberTrabajador(HaberTrabajador haberTrabajador) {
        this.haberTrabajador = haberTrabajador;
    }

    public HaberTrabajadorLiquidacion prepareCreate() {
        HaberTrabajadorLiquidacion newHaberTrabajadorLiquidacion;
        newHaberTrabajadorLiquidacion = new HaberTrabajadorLiquidacion();
        newHaberTrabajadorLiquidacion.setMonto(0);
        newHaberTrabajadorLiquidacion.setNumeroCuotas(0);
        newHaberTrabajadorLiquidacion.setFechaIngresoHaber(new Date());
        newHaberTrabajadorLiquidacion.setFechaInicioHaber(new Date());
        return newHaberTrabajadorLiquidacion;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngresoHaber(new Date());
                this.selected.setActivoHaberTrabajador(Boolean.TRUE);

                session.save(this.selected);
                tx.commit();
                this.items.add(0, selected);
                
                Date fecha = this.selected.getFechaInicioHaber();
                
                this.selected = null;
                this.selected = new HaberTrabajadorLiquidacion();
                this.selected.setMonto(0);
                this.selected.setNumeroCuotas(0);
                this.selected.setFechaInicioHaber(fecha);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:selected");
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
                System.err.println("NULL:selected");
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
                System.err.println("NULL:selected");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<HaberTrabajadorLiquidacion> getItems() {
        return items;
    }

    public void setItems(List<HaberTrabajadorLiquidacion> items) {
        this.items = items;
    }

    public HaberTrabajadorLiquidacion getSelected() {
        return selected;
    }

    public void setSelected(HaberTrabajadorLiquidacion selected) {
        this.selected = selected;
    }

    public List<HaberTrabajador> getHaberItems() {
        return haberItems;
    }

    public void setHaberItems(List<HaberTrabajador> haberItems) {
        this.haberItems = haberItems;
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }
}
