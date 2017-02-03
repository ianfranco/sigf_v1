/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers.remuneraciones.procesos;

import com.areatecnica.sigf_v1.controllers.EmpresaLiquidacion;
import com.areatecnica.sigf_v1.controllers.LiquidacionSueldoController;
import com.areatecnica.sigf_v1.controllers.PlanillonImponiblesController;
import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EgresoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.FeriadoLegalDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.LicenciaMedicaDaoImpl;
import com.areatecnica.sigf_v1.dao.LiquidacionSueldoDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.FeriadoLegal;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.LicenciaMedica;
import com.areatecnica.sigf_v1.entities.LiquidacionSueldo;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.Trabajador;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "procesoImponiblesController")
@ViewScoped
public class ProcesoImponiblesController implements Serializable {

    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat formatMysql = new SimpleDateFormat("yyyy/MM/dd");

    private EmpresaLiquidacion selectedEmpresaLiquidacion;
    private List<EmpresaLiquidacion> items;

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

    private EgresoGuiaDaoImpl egresoGuiaDaoImpl;
    private EgresoGuia egresoGuia;

    private Empresa empresa;
    private List<Empresa> empresasList;

    private LinkedHashMap<Empresa, ArrayList<LiquidacionSueldo>> empresasMap;
    private int mes;
    private int anio;
    private int maxDate;
    private Date fecha;
    private Date fechaMax;
    private Date rangoDesde;
    private Date rangoHasta;
    private int diasMes;
    private int idOperador;
    private static final int SUELDOBASE = 264000;
    private static final int SUELDOBASEPARTIME = 176000;
    private static final int VALORDIA = (int) SUELDOBASE / 30;
    private static final long VALORSIS = (long) 0.0141;
    private Date FECHACESANTIA;
    private int sueldoAjustadoAux;

    /**
     * Creates a new instance of ProcesoImponiblesController
     */
    public ProcesoImponiblesController() {
    }

    @PostConstruct
    private void init() {
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        this.maxDate = calendar.getActualMaximum(Calendar.DATE);
        this.idOperador = -1;
        try {
            this.FECHACESANTIA = format.parse("02/10/2002");
        } catch (ParseException ex) {
            Logger.getLogger(PlanillonImponiblesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public void find(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String to = maxDate + "/" + mes + "/" + anio;
        String from = "01/" + this.mes + "/" + this.anio;
        try {
            this.fecha = format.parse(from);
            this.fechaMax = format.parse(to);
        } catch (ParseException p) {

        }
        
        this.relacionLaboralDao = new RelacionLaboralDaoImpl();
        this.relacionLaboralItems = this.relacionLaboralDao.findEmpresasActivas(this.fecha, this.idOperador);
        
        this.empresasList = new ArrayList<>();
        
        for(RelacionLaboral rl:this.relacionLaboralItems){
            this.empresasList.add(rl.getEmpresa());
        }
        
    }*/
    public void find() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String to = maxDate + "/" + mes + "/" + anio;
        String from = "01/" + this.mes + "/" + this.anio;

        try {
            this.fecha = format.parse(from);
            this.fechaMax = format.parse(to);
        } catch (ParseException p) {

        }

        this.items = new ArrayList<>();

        System.err.println("IDENTIFICACIÓN OPERADOR:" + this.idOperador + " Fecha:" + this.fecha);

        this.relacionLaboralDao = new RelacionLaboralDaoImpl();
        this.relacionLaboralItems = this.relacionLaboralDao.findActivas(this.fecha, this.idOperador);

        this.egresoGuiaDaoImpl = new EgresoGuiaDaoImpl();

        this.empresasMap = new LinkedHashMap();

        this.empresasList = new ArrayList<>();
        this.liquidacionItems = new ArrayList<>();
        //this.trabajadorItems = new ArrayList<>();

        this.feriadoLegalDaoImpl = new FeriadoLegalDaoImpl();
        this.licenciaMedicaDaoImpl = new LicenciaMedicaDaoImpl();
        this.guiaDao = new GuiaDaoImpl();

        this.liquidacionItems = new ArrayList<>();

        System.err.println("TAMAÑO DEL ARCHIVO DE RELACIONES:" + this.relacionLaboralItems.size());

        for (RelacionLaboral r : this.relacionLaboralItems) {

            if (!empresasMap.containsKey(r.getEmpresa())) {

                empresasMap.put(r.getEmpresa(), new ArrayList<LiquidacionSueldo>());
            }
            
            
            RelacionLaboral relacion = r;
            
            System.err.println("TRABAJADOR: "+relacion.getTrabajador());
            LiquidacionSueldoController l = new LiquidacionSueldoController();
            l.setRelacionLaboral(relacion);
            l.setFecha(fecha);
            l.init();
            empresasMap.get(r.getEmpresa()).add(l.getLiquidacion());

        }

        for (Map.Entry<Empresa, ArrayList<LiquidacionSueldo>> a : empresasMap.entrySet()) {
            EmpresaLiquidacion el = new EmpresaLiquidacion(a.getKey(), a.getValue());
            this.items.add(el);
        }

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

    public void findRelacionByEmpresa(Empresa empresa) {
        JsfUtil.addSuccessMessage("Empresa: " + empresa.getNombreEmpresa());
    }

    public ArrayList<LiquidacionSueldo> findRelaciones(Empresa empresa) {
        JsfUtil.addSuccessMessage("Empresa: " + empresa.getNombreEmpresa());

        return new ArrayList<>();
    }

    public List<Empresa> getEmpresasList() {
        return empresasList;
    }

    public void setEmpresasList(List<Empresa> empresasList) {
        this.empresasList = empresasList;
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

    public int getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(int maxDate) {
        this.maxDate = maxDate;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaMax() {
        return fechaMax;
    }

    public void setFechaMax(Date fechaMax) {
        this.fechaMax = fechaMax;
    }

    public Date getRangoDesde() {
        return rangoDesde;
    }

    public void setRangoDesde(Date rangoDesde) {
        this.rangoDesde = rangoDesde;
    }

    public Date getRangoHasta() {
        return rangoHasta;
    }

    public void setRangoHasta(Date rangoHasta) {
        this.rangoHasta = rangoHasta;
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }

    public List<EmpresaLiquidacion> getItems() {
        return items;
    }

    public void setItems(List<EmpresaLiquidacion> items) {
        this.items = items;
    }

}
