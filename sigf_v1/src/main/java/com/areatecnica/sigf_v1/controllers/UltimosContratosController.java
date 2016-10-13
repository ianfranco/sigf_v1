/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EmpresaDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoContratoDaoImpl;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.TipoContrato;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "ultimoContratosController")
@ViewScoped
public class UltimosContratosController implements Serializable {

    private RelacionLaboralDaoImpl relacionLaboralDaoImpl;
    private EmpresaDaoImpl empresaDao;
    private TipoContratoDaoImpl tipoContratoDaoImpl;
    private List<RelacionLaboral> contratos;
    private List<Empresa> empresaItems;
    private List<TipoContrato> tiposContratosItems;
    private RelacionLaboral selected;
    private Date fecha;
    private int mes;
    private int anio;
    private String header;

    public UltimosContratosController() {
        this.relacionLaboralDaoImpl = new RelacionLaboralDaoImpl();
        this.contratos = new ArrayList<>();
        
        this.empresaDao = new EmpresaDaoImpl();
        this.empresaItems = this.empresaDao.findAllCleanOrderById();

        this.tipoContratoDaoImpl = new TipoContratoDaoImpl();
        this.tiposContratosItems = this.tipoContratoDaoImpl.findAll();

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
    }

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
            this.header = "Mes:" + getStringMonth() + " Año:" + anio;

            this.contratos = this.relacionLaboralDaoImpl.findByDate(fecha);

            System.err.println("PRINTING HEADER" + this.header);
        } catch (ParseException p) {

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

    /*public RelacionLaboral prepareCreate(ActionEvent event) {
        RelacionLaboral newRelacionLaboral;
        newRelacionLaboral = new RelacionLaboral();
        this.selected = newRelacionLaboral;
        return newRelacionLaboral;
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
                System.err.println("SAVE:Relacion Laboral");
                tx.rollback();
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
                session.merge(this.selected);
                tx.commit();

            } catch (HibernateException e) {
                System.err.println("SAVE:Empresa");
                tx.rollback();
                JsfUtil.addErrorMessage(e.getMessage());
            }
        } else {

        }
    }*/
    public void resetParents() {

    }

    public void delete() {

    }

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                
                session.update(this.selected);
                tx.commit();

                this.selected = null;
            } catch (HibernateException e) {
                System.err.println("SAVE:Relacion Laboral");
                tx.rollback();
                JsfUtil.addErrorMessage(e.getMessage());
            }
        } else {

        }
    }
    
    public void cancel(){
        this.selected = null;
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<RelacionLaboral> getContratos() {
        return contratos;
    }

    public void setContratos(List<RelacionLaboral> contratos) {
        this.contratos = contratos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public RelacionLaboral getSelected() {
        return selected;
    }

    public void setSelected(RelacionLaboral selected) {
        this.selected = selected;
    }

    public List<Empresa> getEmpresaItems() {
        return empresaItems;
    }

    public void setEmpresaItems(List<Empresa> empresaItems) {
        this.empresaItems = empresaItems;
    }

    public List<TipoContrato> getTiposContratosItems() {
        return tiposContratosItems;
    }

    public void setTiposContratosItems(List<TipoContrato> tiposContratosItems) {
        this.tiposContratosItems = tiposContratosItems;
    }

}
