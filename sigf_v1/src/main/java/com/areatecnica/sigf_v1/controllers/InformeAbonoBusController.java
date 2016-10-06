/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AbonoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.BusDao;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.CargosBusQuery;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDao;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDaoImpl;
import com.areatecnica.sigf_v1.entities.AbonoBus;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
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
@Named(value = "informeAbonosBusController")
@ViewScoped
public class InformeAbonoBusController implements Serializable {

    private UnidadNegocioDao unidadNegocioDao;
    private AbonoBusDaoImpl abonoBusDaoImpl;
    private BusDao busDao;

    private List<AbonoBus> items;
    private List<UnidadNegocio> unidadNegocioItems;
    private List<Bus> busItems;
    private Bus bus;

    private ArrayList<LinkedHashMap> listOfMaps;
    private List<String> resultsHeader;// = service.getResultsValues(...);
    private List<String> resultsTotals;
    private LinkedHashMap totales;
    private Object header;
    private CargosBusQuery cargosBusQuery;
    private AbonoBus selected;
    private int mes;
    private int anio;
    private Date fecha;

    private UnidadNegocio unidadNegocio;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public InformeAbonoBusController() {
        this.unidadNegocioDao = new UnidadNegocioDaoImpl();
        this.unidadNegocioItems = this.unidadNegocioDao.findAll();

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
    }

    public ArrayList<LinkedHashMap> getListOfMaps() {
        return listOfMaps;
    }

    public void setListOfMaps(ArrayList<LinkedHashMap> listOfMaps) {
        this.listOfMaps = listOfMaps;
    }

    public List<String> getResultsHeader() {
        return resultsHeader;
    }

    public void setResultsHeader(List<String> resultsHeader) {
        this.resultsHeader = resultsHeader;
    }

    public List<String> getResultsTotals() {
        return resultsTotals;
    }

    public void setResultsTotals(List<String> resultsTotals) {
        this.resultsTotals = resultsTotals;
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
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
        } catch (ParseException p) {

        }
        this.abonoBusDaoImpl = new AbonoBusDaoImpl();

        if (this.bus != null) {
            this.items = this.abonoBusDaoImpl.findByBus(bus);
            
            
        }

    }

    public void resetParents() {
        //JsfUtil.addSuccessMessage("Guía:" + this.selected.getFolio());
    }

    public void delete() {

    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public LinkedHashMap getTotales() {
        return totales;
    }

    public void setTotales(LinkedHashMap totales) {
        this.totales = totales;
    }

    public List<UnidadNegocio> getUnidadNegocioItems() {
        return unidadNegocioItems;
    }

    public void setUnidadNegocioItems(List<UnidadNegocio> unidadNegocioItems) {
        this.unidadNegocioItems = unidadNegocioItems;
    }

    public UnidadNegocio getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public void handleUnidadChange() {
        if (this.unidadNegocio != null) {
            this.busDao = new BusDaoImpl();
            this.busItems = this.busDao.findByUnidad(this.unidadNegocio.getIdUnidadNegocio());
        }
    }

    public List<AbonoBus> getItems() {
        return items;
    }

    public void setItems(List<AbonoBus> items) {
        this.items = items;
    }

    public List<Bus> getBusItems() {
        return busItems;
    }

    public void setBusItems(List<Bus> busItems) {
        this.busItems = busItems;
    }

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public AbonoBus getSelected() {
        return selected;
    }

    public void setSelected(AbonoBus selected) {
        this.selected = selected;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

}
