/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AbonoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoAbonoDaoImpl;
import com.areatecnica.sigf_v1.entities.AbonoBus;
import com.areatecnica.sigf_v1.entities.TipoAbono;
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
@Named(value = "totalAbonoBusController")
@ViewScoped
public class TotalAbonoBusController implements Serializable {

    private List<AbonoBus> selector;

    private AbonoBusDaoImpl abonoBusDao;
    private TipoAbonoDaoImpl tipoAbonoDaoImpl;
    private List<AbonoBus> items;
    private List<TipoAbono> tipoAbonoItems;
    private AbonoBus selected;
    private AbonoBus rowSelected;
    private int elcargo;
    private int suma;
    private int mes;
    private int anio;
    private Date fecha;
    private TipoAbono abono;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public TotalAbonoBusController() {
        this.abonoBusDao = new AbonoBusDaoImpl();
        this.selector = this.abonoBusDao.findGROUP();
        this.suma = 0;

        this.tipoAbonoDaoImpl = new TipoAbonoDaoImpl();
        this.tipoAbonoItems = this.tipoAbonoDaoImpl.findAll();

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        
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
        AbonoBus newAbonoBus;
        newAbonoBus = new AbonoBus();
        this.selected = newAbonoBus;
        return newAbonoBus;
    }

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
        } catch (ParseException p) {
        }

        suma = 0;
        this.items = this.abonoBusDao.findByAbonoAndDate(abono, fecha);
        System.err.println("tamaño abonos:"+this.items.size());
        for (AbonoBus c : this.items) {
            suma += c.getMontoAbonoBus();
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
        if (this.rowSelected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.update(this.rowSelected);
                tx.commit();

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:AbonoBus");
            }
        } else {

        }
    }

    public void resetParents() {

    }

    public void delete() {
        if (this.rowSelected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.delete(this.rowSelected);
                tx.commit();
                JsfUtil.addSuccessMessage("Se ha eliminado el abono:"+this.rowSelected+" del Bus N°:"+this.rowSelected.getBus());
                this.items.remove(this.rowSelected);
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

   

    public AbonoBus getRowSelected() {
        return rowSelected;
    }

    public void setRowSelected(AbonoBus rowSelected) {
        this.rowSelected = rowSelected;
    }

    public List<TipoAbono> getTipoAbonoItems() {
        return tipoAbonoItems;
    }

    public void setTipoAbonoItems(List<TipoAbono> tipoAbonoItems) {
        this.tipoAbonoItems = tipoAbonoItems;
    }

    public TipoAbono getAbono() {
        return abono;
    }

    public void setAbono(TipoAbono abono) {
        this.abono = abono;
    }
}
