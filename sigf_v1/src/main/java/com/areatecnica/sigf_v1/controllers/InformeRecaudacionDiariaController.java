/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.FlotaDao;
import com.areatecnica.sigf_v1.dao.FlotaDaoImpl;
import com.areatecnica.sigf_v1.dao.ProduccionFlotaQuery;
import com.areatecnica.sigf_v1.dao.RecaudacionDiariaQuery;
import com.areatecnica.sigf_v1.entities.Flota;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "informeRecaudacionDiariaController")
@ViewScoped
public class InformeRecaudacionDiariaController implements Serializable {

    private RecaudacionDiariaQuery flotaQuery;
    private ArrayList<String> resultsHeader;
    private ArrayList<LinkedHashMap> listOfMaps;
    private LinkedHashMap selected;
    private Flota flota;
    private FlotaDao flotaDao;
    private List<Flota> flotaItems;
    private int mes;
    private int anio;
    private Date fecha;
    

    public InformeRecaudacionDiariaController() {
        this.flotaDao = new FlotaDaoImpl();

        this.flotaItems = this.flotaDao.findAll();

        this.resultsHeader = new ArrayList<>();
        this.resultsHeader.add("Nombre");
        this.resultsHeader.add("Administración");
        this.resultsHeader.add("Licitación");
        this.resultsHeader.add("Taller");
        this.resultsHeader.add("5%");
        this.resultsHeader.add("Aseo");
        this.resultsHeader.add("Ahorro");

        /*link.put("nombre_general", a[0]);
                link.put("administracion", a[1]);
                link.put("licitacion", a[2]);
                link.put("taller", a[3]);
                link.put("5%", a[4]);
                link.put("nochero", a[5]);
                link.put("ahorro", a[6]);*/
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
    }

    public void init() {
        
        this.flotaQuery = new RecaudacionDiariaQuery(fecha);
        this.listOfMaps = this.flotaQuery.loadQuery();
    }

    public List<String> getResultsHeader() {
        return resultsHeader;
    }

    public void setResultsHeader(ArrayList<String> resultsHeader) {
        this.resultsHeader = resultsHeader;
    }

    public ArrayList<LinkedHashMap> getListOfMaps() {
        return listOfMaps;
    }

    public void setListOfMaps(ArrayList<LinkedHashMap> listOfMaps) {
        this.listOfMaps = listOfMaps;
    }

    public LinkedHashMap getSelected() {
        return selected;
    }

    public void setSelected(LinkedHashMap selected) {
        this.selected = selected;
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

    public Flota getFlota() {
        return flota;
    }

    public void setFlota(Flota flota) {
        this.flota = flota;
    }

    public List<Flota> getFlotaItems() {
        return flotaItems;
    }

    public void setFlotaItems(ArrayList<Flota> flotaItems) {
        this.flotaItems = flotaItems;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
