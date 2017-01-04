/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.FeriadoLegalDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDao;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.LicenciaMedicaDaoImpl;
import com.areatecnica.sigf_v1.dao.LiquidacionSueldoDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.FeriadoLegal;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.HaberTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.entities.LicenciaMedica;
import com.areatecnica.sigf_v1.entities.LiquidacionSueldo;
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

    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    private TrabajadorDaoImpl trabajadorDaoImpl;
    private List<Trabajador> trabajadorItems;
    private Trabajador selected;

    private RelacionLaboral relacionLaboral;
    private List<RelacionLaboral> relacionLaboralItems;
    private RelacionLaboralDaoImpl relacionLaboralDao;

    private LiquidacionSueldo selectedLiquidacionSueldo;
    private List<LiquidacionSueldo> liquidacionItems;
    private LiquidacionSueldoDaoImpl liquidacionSueldoDaoImpl;

    private FeriadoLegal feriadoLegal;
    private FeriadoLegalDaoImpl feriadoLegalDaoImpl;

    private List<LicenciaMedica> licenciaMedicaItems;
    private LicenciaMedicaDaoImpl licenciaMedicaDaoImpl;
    private List<Date> licencias;
    private List<String> licenciasString;

    private GuiaDaoImpl guiaDao;
    private List<Guia> guiaItems;

    private Empresa empresa;
    private List<Empresa> empresasList;

    private Map empresasMap;
    private int mes;
    private int anio;
    private int maxDate;
    private Date fecha;
    private Date fechaMax;
    private int idOperador;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public PlanillonImponiblesController() {

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        this.maxDate = calendar.getActualMaximum(Calendar.DATE);
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

        String from = "01/" + mes + "/" + anio;
        String to = maxDate + "/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
            this.fechaMax = format.parse(to);
        } catch (ParseException p) {

        }
    }

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String to = maxDate + "/" + mes + "/" + anio;
        String from = "01/" + this.mes + "/" + this.anio;
        try {
            this.fecha = format.parse(from);
            this.fechaMax = format.parse(to);
        } catch (ParseException p) {

        }

        System.err.println("IDENTIFICACIÓN OPERADOR:" + this.idOperador + " Fecha:" + this.fecha);

        this.relacionLaboralDao = new RelacionLaboralDaoImpl();
        this.relacionLaboralItems = this.relacionLaboralDao.findActivas(this.fecha, this.idOperador);

        this.empresasMap = new HashMap();

        this.empresasList = new ArrayList<>();
        this.liquidacionItems = new ArrayList<>();
        //this.trabajadorItems = new ArrayList<>();

        this.feriadoLegalDaoImpl = new FeriadoLegalDaoImpl();
        this.licenciaMedicaDaoImpl = new LicenciaMedicaDaoImpl();
        this.guiaDao = new GuiaDaoImpl();

        for (RelacionLaboral r : this.relacionLaboralItems) {
            this.empresasMap.put(r.getEmpresa().getIdEmpresa(), r.getEmpresa());
            System.err.println("EMPRESA:" + r.getEmpresa().getNombreEmpresa());
            LiquidacionSueldo l = new LiquidacionSueldo();

            l.setTrabajador(r.getTrabajador());
            l.setEmpresa(r.getEmpresa());
            l.setTipoContrato(r.getTipoContrato());
            l.setIdTerminal(r.getTerminal().getIdTerminal());
            l.setNombreTerminal(r.getTerminal().getNombreTerminal());

            if (r.getFechaInicio().before(this.fecha)) {
                l.setFechaContrato(this.fecha);
            } else {
                l.setFechaContrato(r.getFechaInicio());
            }

            //d.compareTo(min) >= 0 && d.compareTo(max) <= 0;
            if (!r.getFechaFin().equals(r.getFechaInicio())) {
                l.setFechaFiniquito(r.getFechaFin());
            } else {
                l.setFechaFiniquito(this.fechaMax);
            }

            System.err.println("FECHAS DEL CONTRATO:" + l.getFechaContrato() + " " + l.getFechaFiniquito() + " Fecha Máxima:" + this.fechaMax);

            this.feriadoLegal = this.feriadoLegalDaoImpl.findByTrabajador(r.getTrabajador(), fecha);

            if (this.feriadoLegal != null) {
                l.setFechaDesdeFeriado(this.feriadoLegal.getFechaDesdeFeriado());
                l.setFechaHastaFeriado(this.feriadoLegal.getFechaHastaFeriado());
            }

            this.licenciaMedicaItems = this.licenciaMedicaDaoImpl.findByTrabajador(r.getTrabajador(), fecha);
            this.licencias = new ArrayList<>();
            this.licenciasString = new ArrayList<>();

            if (!this.licenciaMedicaItems.isEmpty()) {
                for (LicenciaMedica lm : this.licenciaMedicaItems) {
                    this.licencias.addAll(getDaysBetweenDates(lm.getFechaDesdeReposo(), lm.getFechaHastaReposo()));
                }

                for (Date d : this.licencias) {
                    this.licenciasString.add("'" + format.format(d) + "'");
                }
            }

            int montoBruto = 0;

            if (this.feriadoLegal == null && this.licenciaMedicaItems.isEmpty()) {

                this.guiaItems = this.guiaDao.findBrutoByConductor(r.getTrabajador(), l.getFechaContrato(), l.getFechaFiniquito());
                for (Guia g : this.guiaItems) {
                    montoBruto += g.getTotalIngresos();
                }

            } else if (this.feriadoLegal == null && !this.licenciaMedicaItems.isEmpty()) {

                this.guiaItems = this.guiaDao.findBrutoByConductorWithLicencias(r.getTrabajador(), l.getFechaContrato(), l.getFechaFiniquito(), this.licenciasString);
                for (Guia g : this.guiaItems) {
                    montoBruto += g.getTotalIngresos();
                }

            } else if (this.feriadoLegal != null && this.licenciaMedicaItems.isEmpty()) {

                this.guiaItems = this.guiaDao.findBrutoByConductorWithFeriado(r.getTrabajador(), l.getFechaContrato(), l.getFechaFiniquito(), this.feriadoLegal.getFechaDesdeFeriado(), this.feriadoLegal.getFechaHastaFeriado());
                for (Guia g : this.guiaItems) {
                    montoBruto += g.getTotalIngresos();
                }

            } else {

                this.guiaItems = this.guiaDao.findBrutoByConductorWithLicenciasAndFeriado(r.getTrabajador(), l.getFechaContrato(), l.getFechaFiniquito(), this.feriadoLegal.getFechaDesdeFeriado(), this.feriadoLegal.getFechaHastaFeriado(), this.licenciasString);
                for (Guia g : this.guiaItems) {
                    montoBruto += g.getTotalIngresos();
                }

            }
            l.setMontoBruto(montoBruto);
            
            this.liquidacionItems.add(l);
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

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public List<LiquidacionSueldo> getLiquidacionItems() {
        return liquidacionItems;
    }

    public void setLiquidacionItems(List<LiquidacionSueldo> liquidacionItems) {
        this.liquidacionItems = liquidacionItems;
    }

    public LiquidacionSueldo getSelectedLiquidacionSueldo() {
        return selectedLiquidacionSueldo;
    }

    public void setSelectedLiquidacionSueldo(LiquidacionSueldo selectedLiquidacionSueldo) {
        this.selectedLiquidacionSueldo = selectedLiquidacionSueldo;
    }
}
