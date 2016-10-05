/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "liquidacionBusController")
@ViewScoped
public class LiquidacionBusController implements Serializable{

    private int mes;
    private int anio;
    private UnidadNegocio unidadNegocio;
    private Bus bus;
    
    private UnidadNegocioDaoImpl unidadNegocioDaoImpl;
    private BusDaoImpl busDaoImpl;
    
    private List<Bus> busItems;
    private List<UnidadNegocio> unidadNegociosItems;
    
    
    
    /**
     * Creates a new instance of LiquidacionBusController
     */
    public LiquidacionBusController() {
        this.unidadNegocioDaoImpl = new UnidadNegocioDaoImpl();
        this.unidadNegociosItems = this.unidadNegocioDaoImpl.findAll();
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

    public UnidadNegocio getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public List<Bus> getBusItems() {
        return busItems;
    }

    public void setBusItems(List<Bus> busItems) {
        this.busItems = busItems;
    }

    public List<UnidadNegocio> getUnidadNegociosItems() {
        return unidadNegociosItems;
    }

    public void setUnidadNegociosItems(List<UnidadNegocio> unidadNegociosItems) {
        this.unidadNegociosItems = unidadNegociosItems;
    }
    
    public void init(){
        
    }
}
