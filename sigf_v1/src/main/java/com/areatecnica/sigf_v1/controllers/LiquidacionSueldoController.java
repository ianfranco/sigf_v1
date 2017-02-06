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
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.FeriadoLegal;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.LicenciaMedica;
import com.areatecnica.sigf_v1.entities.LiquidacionSueldo;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
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

/**
 *
 * @author Ian Franco
 */
public class LiquidacionSueldoController implements Serializable {

    private LiquidacionSueldo liquidacionSueldo;
    private RelacionLaboral relacionLaboral;
    private FeriadoLegal feriadoLegal;
    private EgresoGuia egresoGuia;

    private List<LicenciaMedica> licenciaMedicaItems;
    private List<Date> licenciasDateItems;
    private List<String> licenciasString;
    private List<Guia> guiaItems;

    private FeriadoLegalDaoImpl feriadoLegalDaoImpl;
    private LicenciaMedicaDaoImpl licenciaMedicaDaoImpl;
    private GuiaDaoImpl guiaDao;
    private EgresoGuiaDaoImpl egresoGuiaDaoImpl;

    private Date fecha;
    private Date rangoDesde;
    private Date rangoHasta;
    private Date fechaMax;
    private Date FECHACESANTIA;

    private int mes;
    private int anio;
    private int diasMes;
    private int diasVacaciones;
    private int ultimoDiaMes;
    private int sueldoAjustadoAux;

    private static final int SUELDOBASE = 264000;
    private static final int SUELDOBASEPARTIME = 176000;
    private static final int VALORDIA = (int) SUELDOBASE / 30;
    private static final float VALORSIS = (float) 0.0141;
    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat formatMysql = new SimpleDateFormat("yyyy/MM/dd");

    public LiquidacionSueldoController() {
    }

    public LiquidacionSueldoController(RelacionLaboral relacionLaboral, Date fecha) {
        this.relacionLaboral = relacionLaboral;
        this.fecha = fecha;
        init();
    }

    public void init() {

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(this.fecha);
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        this.ultimoDiaMes = calendar.getActualMaximum(Calendar.DATE);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String to = ultimoDiaMes + "/" + mes + "/" + anio;
        String from = "01/" + this.mes + "/" + this.anio;

        System.err.println("FECHA MÁXIMA:;::" + this.fechaMax);

        try {
            this.fecha = format.parse(from);
            this.fechaMax = format.parse(to);
        } catch (ParseException p) {
            p.printStackTrace();
        }

        this.egresoGuiaDaoImpl = new EgresoGuiaDaoImpl();
        this.feriadoLegalDaoImpl = new FeriadoLegalDaoImpl();
        this.guiaDao = new GuiaDaoImpl();
        this.licenciaMedicaDaoImpl = new LicenciaMedicaDaoImpl();

        try {
            this.FECHACESANTIA = format.parse("02/10/2002");
        } catch (ParseException ex) {
            Logger.getLogger(PlanillonImponiblesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LiquidacionSueldo getLiquidacion() {
        this.liquidacionSueldo = new LiquidacionSueldo();
        this.liquidacionSueldo.setTrabajador(this.relacionLaboral.getTrabajador());
        System.err.println("TRABAJADOR:" + this.liquidacionSueldo.getTrabajador() + " FECHA:" + this.fecha + " RELACIONFECHA:" + this.relacionLaboral.getFechaInicio());
        this.liquidacionSueldo.setEmpresa(this.relacionLaboral.getEmpresa());
        this.liquidacionSueldo.setTipoContrato(this.relacionLaboral.getTipoContrato());
        this.liquidacionSueldo.setIdTerminal(this.relacionLaboral.getTerminal().getIdTerminal());
        this.liquidacionSueldo.setNombreTerminal(this.relacionLaboral.getTerminal().getNombreTerminal());

        this.liquidacionSueldo.setFechaContrato(this.relacionLaboral.getFechaInicio());

        if (this.relacionLaboral.getFechaInicio().before(this.fecha)) {
            this.rangoDesde = this.fecha;
        } else {
            this.rangoDesde = this.relacionLaboral.getFechaInicio();
        }

        System.err.println("FECHA INICIO CONTRATO: " + this.relacionLaboral.getFechaInicio() + " FECHA FIN CONTRATO: " + this.relacionLaboral.getFechaFin() + " FECHA MAXIMA:" + this.fechaMax);

        this.liquidacionSueldo.setFechaFiniquito(this.relacionLaboral.getFechaFin());

        if (!relacionLaboral.getEstado()) {
            this.rangoHasta = this.relacionLaboral.getFechaFin();
        } else {
            this.rangoHasta = this.fechaMax;
        }

        System.err.println("FECHA DESDE:" + this.rangoDesde + " fecha HASTA:" + this.rangoHasta + " FECHA FINIQUITO:" + this.relacionLaboral.getFechaFin());

        this.diasMes = getDifferenceDays(this.rangoDesde, this.rangoHasta);

        System.err.println("FECHAS DEL CONTRATO:" + this.liquidacionSueldo.getFechaContrato() + " " + this.liquidacionSueldo.getFechaFiniquito() + " Fecha Máxima:" + this.fechaMax);

        this.feriadoLegal = this.feriadoLegalDaoImpl.findByTrabajador(this.relacionLaboral.getTrabajador(), fecha);

        this.diasVacaciones = 0;
        int montoFeriado = 0;

        if (this.feriadoLegal != null) {
            this.liquidacionSueldo.setFechaDesdeFeriado(this.feriadoLegal.getFechaDesdeFeriado());
            this.liquidacionSueldo.setFechaHastaFeriado(this.feriadoLegal.getFechaHastaFeriado());
            montoFeriado = this.feriadoLegal.getValorFeriado();
            diasVacaciones = getDifferenceDays(this.feriadoLegal.getFechaDesdeFeriado(), this.feriadoLegal.getFechaHastaFeriado());
        }

        this.liquidacionSueldo.setMontoFeriado(montoFeriado);
        this.liquidacionSueldo.setDiasFeriado(diasVacaciones);

        this.licenciaMedicaItems = this.licenciaMedicaDaoImpl.findByTrabajador(this.relacionLaboral.getTrabajador(), fecha);
        this.licenciasDateItems = new ArrayList<>();
        this.licenciasString = new ArrayList<>();

        String lic = "";
        String licencias2 = "";

        int diasLicencias = 0;

        if (!this.licenciaMedicaItems.isEmpty()) {
            for (LicenciaMedica lm : this.licenciaMedicaItems) {
                this.licenciasDateItems.addAll(getDaysBetweenDates(lm.getFechaDesdeReposo(), lm.getFechaHastaReposo()));
            }

            for (Date d : this.licenciasDateItems) {
                this.licenciasString.add("'" + formatMysql.format(d) + "'");
                diasLicencias++;
            }
        }

        liquidacionSueldo.setDiasLicencias(diasLicencias);

        float montoBruto = 0;
        int diasTrabajados = 0;
        int cincoPorciento = 0;
        int cincoPorcientoAux = 0;

        if (this.feriadoLegal == null && this.licenciaMedicaItems.isEmpty()) {

            this.guiaItems = this.guiaDao.findBrutoByConductor(relacionLaboral.getTrabajador(), this.rangoDesde, this.rangoHasta);

            if (!this.guiaItems.isEmpty()) {
                for (Guia g : this.guiaItems) {
                    System.err.println("GUIA n° :" + g.getFolio());
                    montoBruto += g.getTotalIngresos();
                    diasTrabajados++;

                    egresoGuia = this.egresoGuiaDaoImpl.findByGuiaAndEgresoClean(g.getIdGuia(), 12);
                    cincoPorciento = cincoPorciento + egresoGuia.getMonto();
                }
            } else {
                montoBruto = 0;
                diasTrabajados = 0;
                cincoPorciento = 0;
            }

            liquidacionSueldo.setMontoCincoPorcientoTotal(cincoPorciento);

        } else if (this.feriadoLegal == null && !this.licenciaMedicaItems.isEmpty()) {

            lic = this.licenciasString.toString();

            lic = lic.substring(1);
            lic = lic.substring(0, lic.length() - 1);

            System.err.println("LICENCIAS:" + lic);

            this.guiaItems = this.guiaDao.findBrutoByConductorWithLicencias(relacionLaboral.getTrabajador(), this.rangoDesde, this.rangoHasta, lic);

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

            this.guiaItems = this.guiaDao.findBrutoByConductorWithFeriado(relacionLaboral.getTrabajador(), this.rangoDesde, this.rangoHasta, this.feriadoLegal.getFechaDesdeFeriado(), this.feriadoLegal.getFechaHastaFeriado());
            if (!this.guiaItems.isEmpty()) {
                for (Guia g : this.guiaItems) {
                    montoBruto += g.getTotalIngresos();
                    diasTrabajados++;
                }
            }else{
                montoBruto = 0;
                diasTrabajados = 0;
            }

        } else {

            this.guiaItems = this.guiaDao.findBrutoByConductorWithLicenciasAndFeriado(relacionLaboral.getTrabajador(), this.rangoDesde, this.rangoHasta, this.feriadoLegal.getFechaDesdeFeriado(), this.feriadoLegal.getFechaHastaFeriado(), this.licenciasString);
            
            if (this.guiaItems!=null) {
                for (Guia g : this.guiaItems) {
                    montoBruto += g.getTotalIngresos();
                    diasTrabajados++;
                }
            }else{
                montoBruto = 0;
                diasTrabajados = 0;
            }

        }

        liquidacionSueldo.setDiasGuias(diasTrabajados);
        liquidacionSueldo.setMontoBruto(montoBruto);
        liquidacionSueldo.setMontoImponible(Math.round(montoBruto * 0.19));

        int diasTotal = diasTrabajados + diasLicencias;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.fecha);

        int diasDescanso = 0;
        if (liquidacionSueldo.getTipoContrato().getIdTipoContrato() == 3) {
            diasDescanso = 0;
        } else {
            diasDescanso = (calendar.getMaximum(Calendar.DATE) - diasTotal);
        }

        /*CALCULO IMPONIBLE SUELDOS FIJOS*/
        if (relacionLaboral.getTipoContrato().getIdTipoContrato() == 5) {

            float sueldo = relacionLaboral.getSueldoBase();
            int dias = this.ultimoDiaMes - diasLicencias;
            if (diasLicencias > 0) {
                sueldo = Math.round((relacionLaboral.getSueldoBase()) / 30) * dias;
            }

            if (dias < this.ultimoDiaMes) {
                sueldo = Math.round((relacionLaboral.getSueldoBase()) / 30) * dias;
            }

            if (montoFeriado != 0) {
                sueldo = montoFeriado + sueldo;
            }

            liquidacionSueldo.setMontoImponibleAjustado(Math.round(sueldo));
            liquidacionSueldo.setMontoSueldoBase(Math.round(sueldo));

        } else if (diasTrabajados > 10) {
            sueldoAjustadoAux = (int) (montoFeriado + SUELDOBASE + Math.round(montoBruto * 0.04));

            if (sueldoAjustadoAux > liquidacionSueldo.getMontoImponible()) {
                liquidacionSueldo.setMontoImponibleAjustado(sueldoAjustadoAux);
            } else {
                liquidacionSueldo.setMontoImponibleAjustado(liquidacionSueldo.getMontoImponible());
            }
            liquidacionSueldo.setMontoSueldoBase(SUELDOBASE);

        } else if ((diasTrabajados + diasLicencias + diasVacaciones) == 0 && getDifferenceDays(liquidacionSueldo.getFechaFiniquito(), this.fechaMax) < 0) {

            if (diasMes > 30) {
                diasMes = 30;
            }

            sueldoAjustadoAux = VALORDIA * diasMes;
            liquidacionSueldo.setMontoImponibleAjustado(sueldoAjustadoAux);
            liquidacionSueldo.setMontoSueldoBase(sueldoAjustadoAux);

        } else if ((diasTrabajados + diasLicencias + diasVacaciones) == 0) {
            sueldoAjustadoAux = SUELDOBASE;
            liquidacionSueldo.setMontoImponibleAjustado(sueldoAjustadoAux);
        } else if (diasTrabajados == 0 && (diasLicencias + diasVacaciones) > 0) {

            if (diasLicencias != 0) {
                sueldoAjustadoAux = (montoFeriado + Math.round(diasMes * VALORDIA));
                liquidacionSueldo.setMontoImponibleAjustado(montoBruto);
            } else {
                liquidacionSueldo.setMontoImponibleAjustado(montoFeriado);
            }

        } else if (diasTrabajados > 0 && (diasLicencias + diasVacaciones) > 0) {

            sueldoAjustadoAux = (int) (montoFeriado + liquidacionSueldo.getMontoImponible());
            liquidacionSueldo.setMontoImponibleAjustado(sueldoAjustadoAux);
        } else {
            liquidacionSueldo.setMontoImponibleAjustado(liquidacionSueldo.getMontoImponible() + montoFeriado);
        }

        if (diasLicencias == this.ultimoDiaMes) {
            liquidacionSueldo.setMontoImponibleAjustado(0);
        }

        liquidacionSueldo.setDiasTrabajados(diasTrabajados);
        liquidacionSueldo.setDiasDescanso(diasDescanso);

        /*CALCULOS PREVISIONALES*/
        //Ahorro Previsional 
        liquidacionSueldo.setMontoApv(relacionLaboral.getTrabajador().getMontoApv());

        //AFP
        
        float porcentaje = relacionLaboral.getTrabajador().getInstitucionPrevision().getPorcentajeDescuento().floatValue();
        System.err.println("PORCENTAJE:"+porcentaje);
        liquidacionSueldo.setMontoPrevision(Math.round((liquidacionSueldo.getMontoImponibleAjustado() * ( porcentaje/ 100))));

        if (liquidacionSueldo.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() != 7) {
            if (liquidacionSueldo.getTrabajador().getMonedaPactadaInstitucionSalud().getIdMonedaSalud() == 1) {
                liquidacionSueldo.setMontoIsapre(Math.round(liquidacionSueldo.getMontoImponibleAjustado() * relacionLaboral.getTrabajador().getMontoSalud().longValue() / 100));
            } else {
                if (diasMes > 30) {
                    diasMes = 30;
                }

                int diasIsapre = diasMes - diasLicencias;

                float montoIsapre = (float) (26318.21 * relacionLaboral.getTrabajador().getMontoSalud().longValue());
                montoIsapre = (montoIsapre / 30) * diasIsapre;
                System.err.println("MONTO ISAPRE 2:" + montoIsapre);
                liquidacionSueldo.setMontoIsapre(montoIsapre);
            }
            liquidacionSueldo.setNombreIsapre(relacionLaboral.getTrabajador().getInstitucionSalud().getNombreInstitucionSalud());
        }

        //CÁLCULO CODIGOS EMPRESA 
        float montoCaja = 0;
        float montoINP = 0;
        int codigoEmpresa = 0;

        int idMutual = relacionLaboral.getEmpresa().getMutual().getIdMutual();
        int idCaja = relacionLaboral.getEmpresa().getCajaCompensacion().getIdCajaCompensacion();

        if (liquidacionSueldo.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
            if (idMutual == 4) {
                if (idCaja == 1) {
                    if (liquidacionSueldo.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
                        codigoEmpresa = 10;

                        if (relacionLaboral.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() != 99) {
                            montoCaja = 0;
                            montoINP = Math.round((liquidacionSueldo.getMontoImponibleAjustado() * (7 / 100)));
                        } else {
                            montoCaja = 0;
                            montoINP = Math.round((liquidacionSueldo.getMontoImponibleAjustado() * (28.24 / 100)));
                        }
                    }

                } else if (idCaja == 6) {
                    if (liquidacionSueldo.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
                        codigoEmpresa = 15;

                        if (relacionLaboral.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() != 99) {
                            montoCaja =  Math.round((liquidacionSueldo.getMontoImponibleAjustado() * (0.6 / 100)));
                            montoINP =  Math.round((liquidacionSueldo.getMontoImponibleAjustado() * (6.4 / 100)));
                        } else {
                            montoCaja = Math.round((liquidacionSueldo.getMontoImponibleAjustado() * (0.6 / 100)));
                            montoINP = Math.round((liquidacionSueldo.getMontoImponibleAjustado() * (28.24 / 100)));
                        }
                    }
                }
            } else if (idMutual == 3) {

                if (idCaja == 1) {
                    if (liquidacionSueldo.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
                        codigoEmpresa = 20;

                        if (relacionLaboral.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() != 99) {
                            montoCaja = 0;
                            montoINP = Math.round(liquidacionSueldo.getMontoImponibleAjustado() * (7 / 100));
                        } else { // REVISAR ACÁ 
                            montoCaja = 0;
                            montoINP = Math.round(liquidacionSueldo.getMontoImponibleAjustado() * (28.24 / 100));
                        }
                    }

                } else if (idCaja == 6) {
                    if (liquidacionSueldo.getTrabajador().getInstitucionSalud().getIdInstitucionSalud() == 7) {
                        codigoEmpresa = 25;

                        if (relacionLaboral.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() != 99) {
                            montoCaja = Math.round(liquidacionSueldo.getMontoImponibleAjustado() * (0.6 / 100));
                            montoINP = Math.round(liquidacionSueldo.getMontoImponibleAjustado() * (6.4 / 100));
                        } else {
                            montoCaja = Math.round(liquidacionSueldo.getMontoImponibleAjustado() * (0.6 / 100));
                            montoINP = Math.round(liquidacionSueldo.getMontoImponibleAjustado() * (28.24 / 100));
                        }
                    }
                }

            }
        } else {
            montoCaja = 0;
            montoINP = 0;
        }

        liquidacionSueldo.setMontoCajaCompensacion(montoCaja);
        liquidacionSueldo.setMontoInp(montoINP);

        float cesantiaTrabajador = 0;
        float cesantiaEmpleador = 0;
        float cesantiaTotal = 0;

        int dias2 = getDifferenceDays(liquidacionSueldo.getFechaContrato(), this.rangoHasta);
        //
        if ((relacionLaboral.getTrabajador().getCesantia() && liquidacionSueldo.getFechaContrato().before(FECHACESANTIA)) || dias2 > 364) //Cálculo de Cesantía
        {
            if (diasLicencias >= 30 || relacionLaboral.getTrabajador().getTipoCotizacionTrabajador().getIdTipoCotizacionTrabajador() == 3) {
                cesantiaEmpleador = Math.round(liquidacionSueldo.getMontoImponibleAjustado() * (2.4 / 100));
                cesantiaTrabajador = 0;
            } else {
                cesantiaEmpleador = Math.round(liquidacionSueldo.getMontoImponibleAjustado() * (2.4 / 100));
                cesantiaTrabajador = Math.round(liquidacionSueldo.getMontoImponibleAjustado() * (0.6 / 100));
            }

        } else {
            cesantiaTotal =  Math.round((liquidacionSueldo.getMontoImponibleAjustado() * (3.0 / 100)));

        }

        liquidacionSueldo.setMontoCesantiaTrabajador(cesantiaTrabajador);
        liquidacionSueldo.setMontoCesantiaEmpresa(cesantiaEmpleador);
        liquidacionSueldo.setMontoCesantiaTotal(cesantiaTotal);
        System.err.println("VALOR CESANTIA TOTAL:"+cesantiaTotal);
        //Calcula SIS 
        if (relacionLaboral.getTrabajador().getInstitucionPrevision().getIdInstitucionPrevision() < 99) {
            System.err.println("Si paga sis");
            liquidacionSueldo.setMontoSis(Math.round(liquidacionSueldo.getMontoImponibleAjustado() * VALORSIS));
        }

        //Cargas Familiares 
        liquidacionSueldo.setNumeroCarga(liquidacionSueldo.getTrabajador().getNumeroCargas());

        liquidacionSueldo.setMontoCarga(liquidacionSueldo.getNumeroCarga() * liquidacionSueldo.getTrabajador().getAsignacionFamiliar().getMonto());

        liquidacionSueldo.setMontoRetroactivo(0);

        return this.liquidacionSueldo;
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

    public LiquidacionSueldo getLiquidacionSueldo() {
        return liquidacionSueldo;
    }

    public void setLiquidacionSueldo(LiquidacionSueldo liquidacionSueldo) {
        this.liquidacionSueldo = liquidacionSueldo;
    }

    public RelacionLaboral getRelacionLaboral() {
        return relacionLaboral;
    }

    public void setRelacionLaboral(RelacionLaboral relacionLaboral) {
        this.relacionLaboral = relacionLaboral;
    }

    public List<Guia> getGuiaItems() {
        return guiaItems;
    }

    public void setGuiaItems(List<Guia> guiaItems) {
        this.guiaItems = guiaItems;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
