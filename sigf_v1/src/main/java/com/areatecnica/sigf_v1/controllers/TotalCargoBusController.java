/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.CargoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoCargoDaoImpl;
import com.areatecnica.sigf_v1.entities.CargoBus;
import com.areatecnica.sigf_v1.entities.TipoCargo;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@Named(value = "totalCargoBusController")
@ViewScoped
public class TotalCargoBusController implements Serializable {

    private List<CargoBus> selector;

    private CargoBusDaoImpl cargoBusDao;
    private TipoCargoDaoImpl tipoCargoDaoImpl;
    private List<CargoBus> items;
    private List<TipoCargo> tiposCargos;
    private CargoBus selected;
    private int elcargo;
    private int suma;
    private int mes;
    private int anio;
    private Date fecha;
    private TipoCargo cargo;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public TotalCargoBusController() {
        this.cargoBusDao = new CargoBusDaoImpl();
        this.selector = this.cargoBusDao.findGROUP();
        this.suma = 0;

        this.tipoCargoDaoImpl = new TipoCargoDaoImpl();
        this.tiposCargos = this.tipoCargoDaoImpl.findAll();

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);

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

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
        } catch (ParseException p) {
        }

        suma = 0;
        this.items = this.cargoBusDao.findByCargoAndDate(cargo, fecha);
        System.err.println("tamaño cargos:"+this.items.size());
        for (CargoBus c : this.items) {
            suma += c.getMontoCargoBus();
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

    public void resetParents() {

    }

    public void delete() {
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

    public String getComponentMessages(String clientComponent, String defaultMessage) {
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

    public List<TipoCargo> getTiposCargos() {
        return tiposCargos;
    }

    public void setTiposCargos(List<TipoCargo> tiposCargos) {
        this.tiposCargos = tiposCargos;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public TipoCargo getCargo() {
        return cargo;
    }

    public void setCargo(TipoCargo cargo) {
        this.cargo = cargo;
    }
}
