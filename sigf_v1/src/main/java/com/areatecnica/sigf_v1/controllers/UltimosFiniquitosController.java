/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.CausalFiniquitoDaoImpl;
import com.areatecnica.sigf_v1.dao.FiniquitoDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.entities.CausalFiniquito;
import com.areatecnica.sigf_v1.entities.FiniquitoRelacionLaboral;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
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
@Named(value = "ultimoFiniquitosController")
@ViewScoped
public class UltimosFiniquitosController implements Serializable {

    private FiniquitoDaoImpl finiquitoDao;
    private List<FiniquitoRelacionLaboral> finiquitos;
    private Date fecha;
    private int mes;
    private int anio;
    private String header;
    private FiniquitoRelacionLaboral selected;
    private List<CausalFiniquito> causalFiniquitosItems;
    private CausalFiniquitoDaoImpl causalFiniquitoDaoImpl;

    public UltimosFiniquitosController() {
        this.finiquitoDao = new FiniquitoDaoImpl();
        this.finiquitos = new ArrayList<>();
        
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        this.causalFiniquitoDaoImpl = new CausalFiniquitoDaoImpl();
    }

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
            this.header = "Mes:"+getStringMonth()+" Año:"+anio;
            
            this.finiquitos = this.finiquitoDao.findByDate(fecha);
            
            this.causalFiniquitoDaoImpl = new CausalFiniquitoDaoImpl();
            this.causalFiniquitosItems = this.causalFiniquitoDaoImpl.findAll();
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

    public void resetParents() {

    }
   
    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<FiniquitoRelacionLaboral> getContratos() {
        return finiquitos;
    }

    public void setContratos(List<FiniquitoRelacionLaboral> contratos) {
        this.finiquitos = contratos;
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

    public List<FiniquitoRelacionLaboral> getFiniquitos() {
        return finiquitos;
    }

    public void setFiniquitos(List<FiniquitoRelacionLaboral> finiquitos) {
        this.finiquitos = finiquitos;
    }

    public FiniquitoRelacionLaboral getSelected() {
        return selected;
    }

    public void setSelected(FiniquitoRelacionLaboral finiquitoRelacionLaboral) {
        this.selected = finiquitoRelacionLaboral;
    }

    public List<CausalFiniquito> getCausalFiniquitosItems() {
        return causalFiniquitosItems;
    }

    public void setCausalFiniquitosItems(List<CausalFiniquito> causalFiniquitosItems) {
        this.causalFiniquitosItems = causalFiniquitosItems;
    }

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                
                session.update(this.selected);
                tx.commit();
                JsfUtil.addSuccessMessage("Se ha actualizado el finiquito");
                this.selected = null;
            } catch (HibernateException e) {
                System.err.println("SAVE:Relacion Laboral");
                tx.rollback();
                JsfUtil.addErrorMessage(e.getMessage());
            }
        } else {

        }
    }
    
    public void delete() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.delete(this.selected);
                tx.commit();

                JsfUtil.addSuccessMessage("Se ha eliminado el finiquito");
                
                this.finiquitos.remove(this.selected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
            }
        } else {

        }
    }
}
