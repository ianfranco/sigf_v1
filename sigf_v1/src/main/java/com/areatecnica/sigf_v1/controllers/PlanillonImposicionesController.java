/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.EgresoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.EmpresaDao;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Ian Franco
 */
@Named(value = "planillonImposicionesController")
@ViewScoped
public class PlanillonImposicionesController implements Serializable{

    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat formatMysql = new SimpleDateFormat("yyyy/MM/dd");

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

    private Map empresasMap;
    private int mes;
    private int anio;
    private int maxDate;
    private Date fecha;
    private Date fechaMax;
    private Date rangoDesde;
    private Date rangoHasta;
    private int diasMes;
    private int idOperador;
    private static final int SUELDOBASE = 257500;
    private static final int SUELDOBASEPARTIME = 171667;
    private static final int VALORDIA = (int) SUELDOBASE / 30;
    private static final long VALORSIS = (long) 0.0141;
    private Date FECHACESANTIA;
    private int sueldoAjustadoAux;
    
    
    /**
     * Creates a new instance of PlanillonImposicionesController
     */
    public PlanillonImposicionesController() {
        
    }
    
}
