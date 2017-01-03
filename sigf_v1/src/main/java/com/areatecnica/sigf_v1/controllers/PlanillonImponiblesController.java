/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.HaberTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.Trabajador;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "planillonImponiblesController")
@ViewScoped
public class PlanillonImponiblesController implements Serializable {

    private TrabajadorDaoImpl trabajadorDaoImpl;
    private List<Trabajador> trabajadorItems;
    private Trabajador selected;

    private RelacionLaboral relacionLaboral;
    private List<RelacionLaboral> relacionLaboralItems;
    private RelacionLaboralDaoImpl relacionLaboralDao;

    private Empresa empresa;
    private List<Empresa> empresasList;

    private Map empresasMap;
    private int mes;
    private int anio;
    private Date fecha;
    private int idOperador;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public PlanillonImponiblesController() {

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        this.idOperador = -1;
        
    }

    public HaberTrabajadorLiquidacion prepareCreate() {
        HaberTrabajadorLiquidacion newHaberTrabajadorLiquidacion;
        newHaberTrabajadorLiquidacion = new HaberTrabajadorLiquidacion();
        newHaberTrabajadorLiquidacion.setMonto(0);
        newHaberTrabajadorLiquidacion.setNumeroCuotas(0);
        newHaberTrabajadorLiquidacion.setFechaIngresoHaber(new Date());
        newHaberTrabajadorLiquidacion.setFechaInicioHaber(new Date());
        return newHaberTrabajadorLiquidacion;
    }

    public void resetParents() {

    }

    public void setDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
        } catch (ParseException p) {

        }
    }

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + this.mes + "/" + this.anio;
        try {
            this.fecha = format.parse(from);
        } catch (ParseException p) {

        }
                
        System.err.println("IDENTIFICACIÓN OPERADOR:" + this.idOperador + " Fecha:" + this.fecha);

        this.relacionLaboralDao = new RelacionLaboralDaoImpl();
        this.relacionLaboralItems = this.relacionLaboralDao.findActivas(this.fecha, this.idOperador);

        this.empresasMap = new HashMap();

        this.empresasList = new ArrayList<>();
        this.trabajadorItems = new ArrayList<>();
        
        for (RelacionLaboral r : this.relacionLaboralItems) {
            this.empresasMap.put(r.getEmpresa().getIdEmpresa(), r.getEmpresa());
            System.err.println("EMPRESA:" + r.getEmpresa().getNombreEmpresa());
            this.trabajadorItems.add(r.getTrabajador());
        }

        this.empresasList = new ArrayList<Empresa>(empresasMap.values());
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public RelacionLaboral getRelacionLaboral() {
        return relacionLaboral;
    }

    public void setRelacionLaboral(RelacionLaboral relacionLaboral) {
        this.relacionLaboral = relacionLaboral;
    }

    public List<RelacionLaboral> getRelacionLaboralItems() {
        return relacionLaboralItems;
    }

    public void setRelacionLaboralItems(List<RelacionLaboral> relacionLaboralItems) {
        this.relacionLaboralItems = relacionLaboralItems;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> getEmpresasList() {
        return empresasList;
    }

    public void setEmpresasList(List<Empresa> empresasList) {
        this.empresasList = empresasList;
    }

    public Map getEmpresasMap() {
        return empresasMap;
    }

    public void setEmpresasMap(Map empresasMap) {
        this.empresasMap = empresasMap;
    }

    public Trabajador getSelected() {
        return selected;
    }

    public void setSelected(Trabajador selected) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }
}
