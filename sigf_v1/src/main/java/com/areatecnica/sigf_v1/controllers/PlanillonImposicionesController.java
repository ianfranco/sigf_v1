/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Ian Franco
 */
@Named(value = "planillonImposicionesController")
@ViewScoped
public class PlanillonImposicionesController implements Serializable {

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

    private Map<Empresa, List<LiquidacionSueldo>> empresasMap;
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

    @PostConstruct
    private void start() {
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

    public void init() {
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

        this.empresasMap = new HashMap();

        this.empresasList = new ArrayList<>();
        this.liquidacionItems = new ArrayList<>();
        //this.trabajadorItems = new ArrayList<>();

        this.feriadoLegalDaoImpl = new FeriadoLegalDaoImpl();
        this.licenciaMedicaDaoImpl = new LicenciaMedicaDaoImpl();
        this.guiaDao = new GuiaDaoImpl();

        
        this.liquidacionItems = new ArrayList<>();
        
        for (RelacionLaboral r : this.relacionLaboralItems) {
            
            Empresa empresaAux = r.getEmpresa();
            
            if(!empresasMap.containsValue(empresaAux)){
                this.liquidacionItems = new ArrayList<>();
                empresasMap.put(empresaAux, null);
            }
            
            //this.empresasMap.put(r.getEmpresa().getIdEmpresa(), r.getEmpresa());
            
            System.err.println("EMPRESA:" + r.getEmpresa().getNombreEmpresa());
            
            LiquidacionSueldo l = new LiquidacionSueldo();

            l.setTrabajador(r.getTrabajador()); //ok
            l.setEmpresa(r.getEmpresa());//ok
            l.setTipoContrato(r.getTipoContrato());//ok
            l.setIdTerminal(r.getTerminal().getIdTerminal()); //ok
            l.setNombreTerminal(r.getTerminal().getNombreTerminal());//ok

            l.setFechaContrato(r.getFechaInicio()); //ok
            if (r.getFechaInicio().before(this.fecha)) {
                this.rangoDesde = this.fecha;
            } else {
                this.rangoDesde = r.getFechaInicio();
            }

            l.setFechaFiniquito(r.getFechaFin());//ok

            if (!r.getFechaFin().equals(r.getFechaInicio())) {
                this.rangoHasta = r.getFechaFin();
            } else {
                this.rangoHasta = this.fechaMax;
            }

            this.diasMes = getDifferenceDays(this.rangoDesde, this.rangoHasta);

            System.err.println("FECHAS DEL CONTRATO:" + l.getFechaContrato() + " " + l.getFechaFiniquito() + " Fecha Máxima:" + this.fechaMax);

            this.feriadoLegal = this.feriadoLegalDaoImpl.findByTrabajador(r.getTrabajador(), fecha);

            int diasVacaciones = 0;
            int montoFeriado = 0;

            if (this.feriadoLegal != null) {
                l.setFechaDesdeFeriado(this.feriadoLegal.getFechaDesdeFeriado());
                l.setFechaHastaFeriado(this.feriadoLegal.getFechaHastaFeriado());
                montoFeriado = this.feriadoLegal.getValorFeriado();
                diasVacaciones = getDifferenceDays(this.feriadoLegal.getFechaDesdeFeriado(), this.feriadoLegal.getFechaHastaFeriado());
            }

            l.setMontoFeriado(montoFeriado);
            l.setDiasFeriado(diasVacaciones);

            this.licenciaMedicaItems = this.licenciaMedicaDaoImpl.findByTrabajador(r.getTrabajador(), fecha);
            this.licencias = new ArrayList<>();
            this.licenciasString = new ArrayList<>();

            String lic = "";
            String licencias2 = "";

            int diasLicencias = 0;

            if (!this.licenciaMedicaItems.isEmpty()) {
                for (LicenciaMedica lm : this.licenciaMedicaItems) {
                    this.licencias.addAll(getDaysBetweenDates(lm.getFechaDesdeReposo(), lm.getFechaHastaReposo()));
                }

                for (Date d : this.licencias) {
                    this.licenciasString.add("'" + formatMysql.format(d) + "'");

                    diasLicencias++;
                }
            }

            l.setDiasLicencias(diasLicencias);
            int montoBruto = 0;
            int diasTrabajados = 0;
            int cincoPorciento = 0;
            int cincoPorcientoAux = 0;

            if (this.feriadoLegal == null && this.licenciaMedicaItems.isEmpty()) {

                this.guiaItems = this.guiaDao.findBrutoByConductor(r.getTrabajador(), this.rangoDesde, this.rangoHasta);
                for (Guia g : this.guiaItems) {
                    montoBruto += g.getTotalIngresos();
                    diasTrabajados++;

                    egresoGuia = this.egresoGuiaDaoImpl.findByGuiaAndEgresoClean(g.getIdGuia(), 12);
                    cincoPorciento = cincoPorciento + egresoGuia.getMonto();
                }

                l.setMontoCincoPorcientoTotal(cincoPorciento);

            } else if (this.feriadoLegal == null && !this.licenciaMedicaItems.isEmpty()) {

                lic = this.licenciasString.toString();

                lic = lic.substring(1);
                lic = lic.substring(0, lic.length() - 1);

                System.err.println("LICENCIAS:" + lic);

                this.guiaItems = this.guiaDao.findBrutoByConductorWithLicencias(r.getTrabajador(), this.rangoDesde, this.rangoHasta, lic);

                if (this.guiaItems.isEmpty()) {
                    montoBruto = 0;
                    diasTrabajados = 0;
                } else {
                    for (Guia g : this.guiaItems) {
                        montoBruto += g.getTotalIngresos();
                        diasTrabajados++;
                    }
                }

            } else if (this.feriadoLegal != null && this.licenciaMedicaItems.isEmpty()) {

                this.guiaItems = this.guiaDao.findBrutoByConductorWithFeriado(r.getTrabajador(), this.rangoDesde, this.rangoHasta, this.feriadoLegal.getFechaDesdeFeriado(), this.feriadoLegal.getFechaHastaFeriado());
                for (Guia g : this.guiaItems) {
                    montoBruto += g.getTotalIngresos();
                    diasTrabajados++;
                }

            } else {

                this.guiaItems = this.guiaDao.findBrutoByConductorWithLicenciasAndFeriado(r.getTrabajador(), this.rangoDesde, this.rangoHasta, this.feriadoLegal.getFechaDesdeFeriado(), this.feriadoLegal.getFechaHastaFeriado(), this.licenciasString);
                for (Guia g : this.guiaItems) {
                    montoBruto += g.getTotalIngresos();
                    diasTrabajados++;
                }

            }

            l.setDiasGuias(diasTrabajados);
            l.setMontoBruto(montoBruto);
            l.setMontoImponible((int) (montoBruto * 0.19));

            int diasTotal = diasTrabajados + diasLicencias;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.fecha);

            int diasDescanso = 0;
            if (l.getTipoContrato().getIdTipoContrato() == 3) {
                diasDescanso = 0;
            } else {
                diasDescanso = (calendar.getMaximum(Calendar.DATE) - diasTotal);
            }

            /*CALCULO IMPONIBLE SUELDOS FIJOS*/
            if (l.getTipoContrato().getIdTipoContrato() == 5) {

                int sueldo = r.getSueldoBase();
                int dias = this.maxDate - diasLicencias;
                if (diasLicencias > 0) {
                    sueldo = (int) ((r.getSueldoBase()) / 30) * dias;
                }

                if (dias < this.maxDate) {
                    sueldo = (int) ((r.getSueldoBase()) / 30) * dias;
                }

                if (montoFeriado != 0) {
                    sueldo = montoFeriado + sueldo;
                }

                l.setMontoImponibleAjustado(sueldo);
                l.setMontoSueldoBase(sueldo);
            } else if (diasTrabajados > 10) {
                sueldoAjustadoAux = (int) (montoFeriado + SUELDOBASE + (montoBruto * 0.04));

                if (sueldoAjustadoAux > l.getMontoImponible()) {
                    l.setMontoImponibleAjustado(sueldoAjustadoAux);
                } else {
                    l.setMontoImponibleAjustado(l.getMontoImponible());
                }
                l.setMontoSueldoBase(SUELDOBASE);

            } else if ((diasTrabajados + diasLicencias + diasVacaciones) == 0 && getDifferenceDays(l.getFechaFiniquito(), this.fechaMax) < 0) {

                if (diasMes > 30) {
                    diasMes = 30;
                }

                sueldoAjustadoAux = VALORDIA * diasMes;
                l.setMontoImponibleAjustado(sueldoAjustadoAux);
                l.setMontoSueldoBase(sueldoAjustadoAux);
            } else if ((diasTrabajados + diasLicencias + diasVacaciones) == 0) {
                sueldoAjustadoAux = SUELDOBASE;
                l.setMontoImponibleAjustado(sueldoAjustadoAux);
            } else if (diasTrabajados == 0 && (diasLicencias + diasVacaciones) > 0) {

                if (diasLicencias != 0) {
                    sueldoAjustadoAux = (montoFeriado + (diasMes * VALORDIA));
                    l.setMontoImponibleAjustado(montoBruto);
                } else {
                    l.setMontoImponibleAjustado(montoFeriado);
                }

            } else if (diasTrabajados > 0 && (diasLicencias + diasVacaciones) > 0) {

                sueldoAjustadoAux = montoFeriado + l.getMontoImponible();
                l.setMontoImponibleAjustado(sueldoAjustadoAux);
            } else {
                l.setMontoImponibleAjustado(l.getMontoImponible() + montoFeriado);
            }

            if (diasLicencias == this.maxDate) {
                l.setMontoImponibleAjustado(0);
            }

            l.setDiasTrabajados(diasTrabajados);
            l.setDiasDescanso(diasDescanso);

            /*CALCULOS PREVISIONALES*/
            //Ahorro Previsional 
            l.setMontoApv(r.getTrabajador().getMontoApv());

            //AFP
            l.setMontoPrevision((int) (l.getMontoImponible() * r.getTrabajador().getInstitucionPrevision().getPorcentajeDescuento().longValue() / 100));

            if (l.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() != 7) {
                if (l.getTrabajador().getMonedaPactadaInstitucionSalud().getIdMonedaSalud() == 1) {
                    l.setMontoIsapre((int) (l.getMontoImponibleAjustado() * (l.getTrabajador().getMontoSalud().longValue() / 100)));
                } else {
                    if (diasMes > 30) {
                        diasMes = 30;
                    }

                    int diasIsapre = diasMes - diasLicencias;

                    int montoIsapre = (int) (26318.21 * l.getTrabajador().getMontoSalud().longValue());
                    montoIsapre = (montoIsapre / 30) * diasIsapre;
                    l.setMontoIsapre(montoIsapre);
                }
            }

            //CÁLCULO CODIGOS EMPRESA 
            int montoCaja = 0;
            int montoINP = 0;
            int codigoEmpresa = 0;

            int idMutual = r.getEmpresa().getMutual().getIdMutual();
            int idCaja = r.getEmpresa().getCajaCompensacion().getIdCajaCompensacion();

            if (l.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
                if (idMutual == 4) {
                    if (idCaja == 1) {
                        if (l.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
                            codigoEmpresa = 10;

                            if (r.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() != 99) {
                                montoCaja = 0;
                                montoINP = (int) (l.getMontoImponible() * (7 / 100));
                            } else {
                                montoCaja = 0;
                                montoINP = (int) (l.getMontoImponible() * (28.24 / 100));
                            }
                        }

                    } else if (idCaja == 6) {
                        if (l.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
                            codigoEmpresa = 15;

                            if (r.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() != 99) {
                                montoCaja = (int) (l.getMontoImponibleAjustado() * (0.6 / 100));
                                montoINP = (int) (l.getMontoImponibleAjustado() * (6.4 / 100));
                            } else {
                                montoCaja = (int) (l.getMontoImponibleAjustado() * (0.6 / 100));
                                montoINP = (int) (l.getMontoImponibleAjustado() * (28.24 / 100));
                            }
                        }
                    }
                } else if (idMutual == 3) {

                    if (idCaja == 1) {
                        if (l.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
                            codigoEmpresa = 20;

                            if (r.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() != 99) {
                                montoCaja = 0;
                                montoINP = (int) (l.getMontoImponibleAjustado() * (7 / 100));
                            } else { // REVISAR ACÁ 
                                montoCaja = 0;
                                montoINP = (int) (l.getMontoImponibleAjustado() * (28.24 / 100));
                            }
                        }

                    } else if (idCaja == 6) {
                        if (l.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
                            codigoEmpresa = 25;

                            if (r.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() != 99) {
                                montoCaja = (int) (l.getMontoImponibleAjustado() * (0.6 / 100));
                                montoINP = (int) (l.getMontoImponibleAjustado() * (6.4 / 100));
                            } else {
                                montoCaja = (int) (l.getMontoImponibleAjustado() * (0.6 / 100));
                                montoINP = (int) (l.getMontoImponibleAjustado() * (28.24 / 100));
                            }
                        }
                    }

                }
            } else {
                montoCaja = 0;
                montoINP = 0;
            }

            l.setMontoCajaCompensacion(montoCaja);
            l.setMontoInp(montoINP);

            int cesantiaTrabajador = 0;
            int cesantiaEmpleador = 0;
            int cesantiaTotal = 0;

            int dias2 = getDifferenceDays(l.getFechaContrato(), this.rangoHasta);
            //
            if ((l.getTrabajador().getCesantia() && l.getFechaContrato().before(FECHACESANTIA)) || dias2 > 364) //Cálculo de Cesantía
            {
                if (diasLicencias >= 30 || l.getTrabajador().getTipoCotizacionTrabajador().getIdTipoCotizacionTrabajador() == 3) {
                    cesantiaEmpleador = (int) (l.getMontoImponibleAjustado() * (2.4 / 100));
                    cesantiaTrabajador = 0;
                } else {
                    cesantiaEmpleador = (int) (l.getMontoImponibleAjustado() * (2.4 / 100));
                    cesantiaTrabajador = (int) (l.getMontoImponibleAjustado() * (0.6 / 100));
                }

            } else {
                cesantiaTotal = (int) (l.getMontoImponibleAjustado() * (3.0 / 100));

            }

            l.setMontoCesantiaTrabajador(cesantiaTrabajador);
            l.setMontoCesantiaEmpresa(cesantiaEmpleador);
            l.setMontoCesantiaTotal(cesantiaTotal);

            //Calcula SIS 
            if (l.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() < 99) {
                l.setMontoSis((int) (l.getMontoImponibleAjustado() * VALORSIS));
            }

            //Cargas Familiares 
            l.setNumeroCarga(l.getTrabajador().getNumeroCargas());

            l.setMontoCarga(l.getNumeroCarga() * l.getTrabajador().getAsignacionFamiliar().getMonto());

            l.setMontoRetroactivo(0);

            this.liquidacionItems.add(l);
            
            empresasMap.put(empresaAux, this.liquidacionItems);
        }
        
        for(Entry<Empresa, List<LiquidacionSueldo>> a: empresasMap.entrySet()){
            EmpresaLiquidacion el = new EmpresaLiquidacion(a.getKey(), a.getValue());
            this.items.add(el);
        }
        

        //this.empresasList = new ArrayList<Empresa>(empresasMap.values());
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

    public int getDifferenceDays(Date d1, Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
        daysdiff = (int) diffDays;
        return daysdiff;
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

    public List<EmpresaLiquidacion> getItems() {
        return items;
    }

    public void setItems(List<EmpresaLiquidacion> items) {
        this.items = items;
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

    public FeriadoLegal getFeriadoLegal() {
        return feriadoLegal;
    }

    public void setFeriadoLegal(FeriadoLegal feriadoLegal) {
        this.feriadoLegal = feriadoLegal;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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

    public int getDiasMes() {
        return diasMes;
    }

    public void setDiasMes(int diasMes) {
        this.diasMes = diasMes;
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }

    public int getSueldoAjustadoAux() {
        return sueldoAjustadoAux;
    }

    public void setSueldoAjustadoAux(int sueldoAjustadoAux) {
        this.sueldoAjustadoAux = sueldoAjustadoAux;
    }

}
