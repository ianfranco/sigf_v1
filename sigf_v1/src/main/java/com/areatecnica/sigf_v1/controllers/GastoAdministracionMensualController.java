/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.GastoAdministracionMensualDaoImpl;
import com.areatecnica.sigf_v1.entities.Departamento;
import com.areatecnica.sigf_v1.entities.GastoAdministracionMensual;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
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
@Named(value = "gastoAdministracionMensualController")
@ViewScoped
public class GastoAdministracionMensualController implements Serializable {

    private GastoAdministracionMensualDaoImpl gastoAdministracionMensualDaoImpl;
    private List<GastoAdministracionMensual> items;
    private GastoAdministracionMensual selected;
    private GastoAdministracionMensual rowSelected;
    private int mes;
    private int anio;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public GastoAdministracionMensualController() {
        this.gastoAdministracionMensualDaoImpl = new GastoAdministracionMensualDaoImpl();
        this.items = this.gastoAdministracionMensualDaoImpl.findAll();

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        
        this.selected = new GastoAdministracionMensual();
        this.selected.setFechaGastoAdministracion(new Date());
    }

    public List<GastoAdministracionMensual> getItems() {
        return items;
    }

    public void setItems(List<GastoAdministracionMensual> items) {
        this.items = items;
    }

    public GastoAdministracionMensual getSelected() {
        return selected;
    }

    public void setSelected(GastoAdministracionMensual selected) {
        this.selected = selected;
    }

    public GastoAdministracionMensual prepareCreate(ActionEvent event) {
        GastoAdministracionMensual newGasto;
        newGasto = new GastoAdministracionMensual();
        this.selected = newGasto;
        return newGasto;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                
                Calendar calendarAux = GregorianCalendar.getInstance();
                calendarAux.setTime(this.selected.getFechaGastoAdministracion());
                
                this.selected.setAnio(calendarAux.get(Calendar.YEAR));
                this.selected.setMes(calendarAux.get(Calendar.MONTH)+1);                
                this.selected.setFechaIngreso(new Date());
                
                
                session.saveOrUpdate(this.selected);
                tx.commit();
                Date fechaAux = this.selected.getFechaGastoAdministracion();
                Departamento deptoAux = this.selected.getDepartamento();
                
                this.items.add(0, selected);
                
                
                this.selected = null;
                this.selected = new GastoAdministracionMensual();
                this.selected.setDepartamento(deptoAux);
                this.selected.setFechaGastoAdministracion(fechaAux);
                JsfUtil.addSuccessMessage("Se ha registrado el Gasto");
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:GastoAdministracionMensual");
            }
        } else {

        }
    }

    public void save() {
        if (this.rowSelected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.saveOrUpdate(this.rowSelected);
                tx.commit();
                JsfUtil.addSuccessMessage("Se ha actualizado el Gasto");
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:GastoAdministracionMensual");
            }
        } else {

        }
    }

    public String getStringMonth() {
        switch (this.mes) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
        }
        return "";
    }

    public void init() {
        this.items = this.gastoAdministracionMensualDaoImpl.findAll();
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

                JsfUtil.addSuccessMessage("Se ha eliminado el Gasto");

                this.items.remove(this.rowSelected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Gasto");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
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

    public GastoAdministracionMensual getRowSelected() {
        return rowSelected;
    }

    public void setRowSelected(GastoAdministracionMensual rowSelected) {
        this.rowSelected = rowSelected;
    }
}
