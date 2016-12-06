/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.FeriadoLegalDaoImpl;
import com.areatecnica.sigf_v1.entities.FeriadoLegal;
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
@Named(value = "registroVacacionesMesController")
@ViewScoped
public class RegistroVacacionesMesController implements Serializable {

    private FeriadoLegalDaoImpl feriadoLegalDaoImpl;
    private List<FeriadoLegal> items;
    private Date fecha;
    private int mes;
    private int anio;
    private String header;
    private FeriadoLegal selected;

    public RegistroVacacionesMesController() {
        this.feriadoLegalDaoImpl = new FeriadoLegalDaoImpl();
        this.items = new ArrayList<>();
        
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);        
    }

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
            this.header = "Mes:"+getStringMonth()+" Año:"+anio;
            
            this.items = this.feriadoLegalDaoImpl.findBetweenDate(fecha);
            
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

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                
                session.update(this.selected);
                tx.commit();
                JsfUtil.addSuccessMessage("Se ha actualizado el feriado");
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

                JsfUtil.addSuccessMessage("Se ha eliminado el feriado");
                
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
            }
        } else {

        }
    }

    public List<FeriadoLegal> getItems() {
        return items;
    }

    public void setItems(List<FeriadoLegal> items) {
        this.items = items;
    }

    public FeriadoLegal getSelected() {
        return selected;
    }

    public void setSelected(FeriadoLegal selected) {
        this.selected = selected;
    }
}
