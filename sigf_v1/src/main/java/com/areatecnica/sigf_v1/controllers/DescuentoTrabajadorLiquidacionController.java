/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorLiquidacionDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajador;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajadorLiquidacion;
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
@Named(value = "descuentoTrabajadorLiquidacionController")
@SessionScoped
public class DescuentoTrabajadorLiquidacionController implements Serializable {

    private DescuentoTrabajadorDaoImpl descuentoTrabajadorDao;
    private DescuentoTrabajadorLiquidacionDaoImpl descuentoTrabajadorLiquidacionDaoImpl;
    private TrabajadorDaoImpl trabajadorDaoImpl;

    private DescuentoTrabajador descuentoTrabajador;
    private DescuentoTrabajadorLiquidacion selected;

    private List<DescuentoTrabajadorLiquidacion> items;
    private List<DescuentoTrabajador> descuentosItems;
    private List<Trabajador> trabajadorItems;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public DescuentoTrabajadorLiquidacionController() {
        this.descuentoTrabajadorDao = new DescuentoTrabajadorDaoImpl();
        this.descuentosItems = this.descuentoTrabajadorDao.findAll();

        this.trabajadorDaoImpl = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDaoImpl.findAll();

        this.descuentoTrabajadorLiquidacionDaoImpl = new DescuentoTrabajadorLiquidacionDaoImpl();
        this.items = this.descuentoTrabajadorLiquidacionDaoImpl.findWithLimit();

        this.selected = prepareCreate();

    }

    public DescuentoTrabajador getDescuentoTrabajador() {
        return descuentoTrabajador;
    }

    public void setDescuentoTrabajador(DescuentoTrabajador descuentoTrabajador) {
        this.descuentoTrabajador = descuentoTrabajador;
    }

    public DescuentoTrabajadorLiquidacion prepareCreate() {
        DescuentoTrabajadorLiquidacion newDescuentoTrabajadorLiquidacion;
        newDescuentoTrabajadorLiquidacion = new DescuentoTrabajadorLiquidacion();
        newDescuentoTrabajadorLiquidacion.setMonto(0);
        newDescuentoTrabajadorLiquidacion.setNumeroCuotas(0);
        return newDescuentoTrabajadorLiquidacion;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngresoDescuento(new Date());
                this.selected.setActivoDescuentoTrabajador(Boolean.TRUE);

                session.save(this.selected);
                tx.commit();
                this.items.add(0, selected);
                
                Date fecha = this.selected.getFechaInicioDescuento();
                
                this.selected = null;
                this.selected = new DescuentoTrabajadorLiquidacion();
                this.selected.setMonto(0);
                this.selected.setNumeroCuotas(0);
                this.selected.setFechaInicioDescuento(fecha);
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

    public List<DescuentoTrabajadorLiquidacion> getItems() {
        return items;
    }

    public void setItems(List<DescuentoTrabajadorLiquidacion> items) {
        this.items = items;
    }

    public DescuentoTrabajadorLiquidacion getSelected() {
        return selected;
    }

    public void setSelected(DescuentoTrabajadorLiquidacion selected) {
        this.selected = selected;
    }

    public List<DescuentoTrabajador> getDescuentosItems() {
        return descuentosItems;
    }

    public void setDescuentosItems(List<DescuentoTrabajador> descuentosItems) {
        this.descuentosItems = descuentosItems;
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }
}
