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
@Named(value = "registroSaldoAnteriorController")
@ViewScoped
public class RegistroSaldoAnteriorController implements Serializable {

    private DescuentoTrabajadorDaoImpl descuentoTrabajadorDao;
    private DescuentoTrabajadorLiquidacionDaoImpl descuentoTrabajadorLiquidacionDaoImpl;
    private TrabajadorDaoImpl trabajadorDaoImpl;
   
    private DescuentoTrabajadorLiquidacion selected;

    private List<DescuentoTrabajadorLiquidacion> items;
    private DescuentoTrabajador saldoAnterior;
    private List<Trabajador> trabajadorItems;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroSaldoAnteriorController() {
        this.descuentoTrabajadorDao = new DescuentoTrabajadorDaoImpl();
        this.saldoAnterior = this.descuentoTrabajadorDao.findById(4);

        this.trabajadorDaoImpl = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDaoImpl.findAll();

        this.descuentoTrabajadorLiquidacionDaoImpl = new DescuentoTrabajadorLiquidacionDaoImpl();
        this.items = this.descuentoTrabajadorLiquidacionDaoImpl.findSaldoAnteriorWithLimit();

        this.selected = prepareCreate();

    }

    public DescuentoTrabajadorLiquidacion prepareCreate() {
        
        DescuentoTrabajadorLiquidacion newDescuentoTrabajadorLiquidacion;
        newDescuentoTrabajadorLiquidacion = new DescuentoTrabajadorLiquidacion();
        newDescuentoTrabajadorLiquidacion.setMonto(0);
        newDescuentoTrabajadorLiquidacion.setNumeroCuotas(0);
        newDescuentoTrabajadorLiquidacion.setIdRelacionLaboral(1771);
        return newDescuentoTrabajadorLiquidacion;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngresoDescuento(new Date());
                this.selected.setActivoDescuentoTrabajador(Boolean.TRUE);
                this.selected.setNumeroCuotas(1);
                this.selected.setDescuentoTrabajador(saldoAnterior);

                session.save(this.selected);
                tx.commit();
                this.items.add(0, selected);
                
                Date fecha = this.selected.getFechaInicioDescuento();
                JsfUtil.addSuccessMessage("Se ha registrado un Saldo Anterior de: "+this.selected.getMonto()+" al Trabajador:"+this.selected.getTrabajador());
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

    public DescuentoTrabajador getDescuentosItems() {
        return saldoAnterior;
    }

    public void setDescuentosItems(DescuentoTrabajador saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }
}
