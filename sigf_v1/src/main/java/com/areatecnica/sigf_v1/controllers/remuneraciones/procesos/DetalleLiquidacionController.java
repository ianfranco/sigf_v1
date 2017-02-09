/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers.remuneraciones.procesos;

import com.areatecnica.sigf_v1.controllers.PlanillonImponiblesController;
import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorLiquidacionDaoImpl;
import com.areatecnica.sigf_v1.dao.EgresoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajador;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.Guia;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "detalleLiquidacionController")
@ViewScoped
public class DetalleLiquidacionController implements Serializable {

    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat formatMysql = new SimpleDateFormat("yyyy/MM/dd");

    private GuiaDaoImpl guiaDaoImpl;
    private EgresoGuiaDaoImpl egresoGuiaDao;
    private DescuentoTrabajadorLiquidacionDaoImpl descuentoTrabajadorLiquidacionDaoImpl;
    private DescuentoTrabajadorDaoImpl descuentoTrabajadorDaoImpl;
    private TrabajadorDaoImpl trabajadorDaoImpl;
    private RelacionLaboralDaoImpl relacionLaboralDao;

    private List<Trabajador> trabajadorItems;
    private List<RelacionLaboral> relacionLaboralItems;
    private List<DescuentoTrabajadorLiquidacion> descuentosLiquidacionItems;
    private List<DescuentoTrabajador> descuentosTrabajadorItems;
    private List<Guia> guiaItems;
    private ArrayList<Object> guiaHelper;

    private Trabajador selected;
    private RelacionLaboral relacionLaboral;
    private EgresoGuia egresoGuia;

    private int mes;
    private int anio;
    private int maxDate;
    private Date fecha;
    private Date fechaMax;
    private Date rangoDesde;
    private Date rangoHasta;
    private int diasMes;
    private static final int SUELDOBASE = 264000;
    private static final int SUELDOBASEPARTIME = 176000;
    private static final int VALORDIA = (int) SUELDOBASE / 30;
    private static final long VALORSIS = (long) 0.0141;
    private Date FECHACESANTIA;
    private int sueldoAjustadoAux;

    private int ingresos;

    /**
     * Creates a new instance of ProcesoImponiblesController
     */
    public DetalleLiquidacionController() {
    }

    @PostConstruct
    private void init() {
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        this.maxDate = calendar.getActualMaximum(Calendar.DATE);

        this.trabajadorDaoImpl = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDaoImpl.findAllClean();

        try {
            this.FECHACESANTIA = format.parse("02/10/2002");
        } catch (ParseException ex) {
            Logger.getLogger(PlanillonImponiblesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void find() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String to = maxDate + "/" + mes + "/" + anio;
        String from = "01/" + this.mes + "/" + this.anio;

        try {
            this.fecha = format.parse(from);
            this.fechaMax = format.parse(to);
        } catch (ParseException p) {

        }

        this.guiaDaoImpl = new GuiaDaoImpl();
        this.guiaItems = this.guiaDaoImpl.findByConductorBetweenDates(this.selected, fecha);

        this.egresoGuiaDao = new EgresoGuiaDaoImpl();
        this.guiaHelper = new ArrayList<>();
        for (Guia g : guiaItems) {

            GuiaLiquidacionHelper helper = new GuiaLiquidacionHelper();

            this.egresoGuia = this.egresoGuiaDao.findByGuiaAndEgresoClean(g.getIdGuia(), 12);
            helper.setCinco(this.egresoGuia.getMonto());

            this.egresoGuia = this.egresoGuiaDao.findByGuiaAndEgresoClean(g.getIdGuia(), 14);
            helper.setAhorro(this.egresoGuia.getMonto());

            helper.setSaldo(helper.getCinco() + helper.getAhorro());

            helper.setFolio(g.getFolio());
            helper.setFechaRecaudacion(g.getFechaGuia());

            this.guiaHelper.add(helper);

            this.ingresos = this.ingresos + helper.saldo;
        }

        this.relacionLaboralDao = new RelacionLaboralDaoImpl();
        this.relacionLaboralItems = this.relacionLaboralDao.findActivasByTrabajador(this.fecha, this.selected);

        this.descuentoTrabajadorLiquidacionDaoImpl = new DescuentoTrabajadorLiquidacionDaoImpl();
        this.descuentoTrabajadorDaoImpl = new DescuentoTrabajadorDaoImpl();

        this.descuentosTrabajadorItems = this.descuentoTrabajadorDaoImpl.findAll();
        
        this.descuentosLiquidacionItems = new ArrayList<>();
        
        for (DescuentoTrabajador d : this.descuentosTrabajadorItems) {

            DescuentoTrabajadorLiquidacion descuento = this.descuentoTrabajadorLiquidacionDaoImpl.findByTrabajadorAndDescuentoAndDate(selected, d, fecha);

            if (descuento != null) {
                this.descuentosLiquidacionItems.add(descuento);
            } else {
                descuento = new DescuentoTrabajadorLiquidacion();
                descuento.setDescuentoTrabajador(d);
                descuento.setMonto(0);
                descuento.setFechaInicioDescuento(fecha);
                descuento.setFechaIngresoDescuento(new Date());
                descuento.setNumeroCuotas(0);
                descuento.setActivoDescuentoTrabajador(true);
            }

            this.descuentosLiquidacionItems.add(descuento);

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

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public Trabajador getSelected() {
        return selected;
    }

    public void setSelected(Trabajador selected) {
        this.selected = selected;
    }

    public class GuiaLiquidacionHelper {

        private int cinco;
        private int ahorro;
        private int saldo;
        private int folio;
        private Date fechaRecaudacion;

        public int getFolio() {
            return folio;
        }

        public void setFolio(int folio) {
            this.folio = folio;
        }

        public int getCinco() {
            return cinco;
        }

        public void setCinco(int cinco) {
            this.cinco = cinco;
        }

        public int getAhorro() {
            return ahorro;
        }

        public void setAhorro(int ahorro) {
            this.ahorro = ahorro;
        }

        public int getSaldo() {
            return saldo;
        }

        public void setSaldo(int saldo) {
            this.saldo = saldo;
        }

        public Date getFechaRecaudacion() {
            return fechaRecaudacion;
        }

        public void setFechaRecaudacion(Date fechaRecaudacion) {
            this.fechaRecaudacion = fechaRecaudacion;
        }

    }

}
