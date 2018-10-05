/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers.tesoreria.administracion;

import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.GastoAdministracionMensualDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoCargoDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.CargoBus;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.TipoCargo;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import com.areatecnica.sigf_v1.util.JsfUtil;
import com.ibm.icu.text.NumberFormat;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
@Named(value = "traspasoAdministracionController")
@ViewScoped
public class TraspasoAdministracionController implements Serializable {

    private List<Bus> itemsBuses;
    private List<Guia> itemsGuias;
    private List<DiasBusesHelper> items;

    private GastoAdministracionMensualDaoImpl administracionMensualDaoImpl;
    private GuiaDaoImpl guiaDao;
    private TipoCargoDaoImpl tipoCargoDaoImpl;
    private BusDaoImpl busDaoImpl;

    private TipoCargo tipoCargo;

    private Date fecha;
    private Date fechaAux;
    private int mes;
    private int anio;
    private int administracion;
    private int totaltGastos;
    private int numeroBuses;
    private int numeroBusesCompleto;
    private int numeroBusesProporcional;
    private int totalDias;
    private int valorDia;
    private int totalAdministracion;

    private static final NumberFormat format = NumberFormat.getInstance();

    /**
     * Creates a new instance of DiasBusesController
     */
    public TraspasoAdministracionController() {
        this.tipoCargoDaoImpl = new TipoCargoDaoImpl();
        this.tipoCargo = this.tipoCargoDaoImpl.findById(2);

        System.err.println("tipo de cargo:" + this.tipoCargo.getNombreTipoCargo());
        this.totalDias = 0;
        this.numeroBusesCompleto = 0;
        this.numeroBusesProporcional = 0;

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
            System.err.println("FECHA:" + this.fecha);
        } catch (ParseException p) {

        }

    }

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
        } catch (ParseException p) {

        }

        System.err.println("TOTAL ADMINISTRACIÓN:" + this.administracion);

        this.guiaDao = new GuiaDaoImpl();
        this.itemsGuias = this.guiaDao.findByFecha(fecha);
        this.items = new ArrayList<>();

        this.numeroBuses = 0;
        System.err.println("n° de buses:" + this.numeroBuses);

        this.administracionMensualDaoImpl = new GastoAdministracionMensualDaoImpl();
        this.totaltGastos = this.administracionMensualDaoImpl.findTotalByMonthAndYear(fecha);
        System.err.println("TOTAL GASTOS ADMINISTRACIÓN: " + this.totaltGastos);

        for (Guia g : this.itemsGuias) {
            if (g.getBus().getEmpresa().getIdEmpresa() != 29) {

                this.numeroBuses = this.numeroBuses + 1;

                DiasBusesHelper d = new DiasBusesHelper();

                d.setBus(g.getBus());
                d.setDt(this.guiaDao.findDTByBusBetweenDates(g.getBus(), fecha));

                if (d.getDt() < 15) {
                    this.totalDias = this.totalDias + d.getDt();
                    this.numeroBusesProporcional = this.numeroBusesProporcional + 1;
                } else {
                    this.numeroBusesCompleto = this.numeroBusesCompleto + 1;
                }

                this.items.add(d);
            }
        }

        Collections.sort(this.items, new Comparator<DiasBusesHelper>() {
            @Override
            public int compare(DiasBusesHelper o1, DiasBusesHelper o2) {
                if (o1.getDt() == o2.getDt()) {
                    return 0;
                } else if (o1.getDt() < o2.getDt()) {
                    return -1;
                }
                return 1;
            }
        });

        if (this.totaltGastos > 0 && this.numeroBuses > 0) {
            this.administracion = this.totaltGastos / this.numeroBuses;
        } else {
            this.administracion = 0;
        }

        System.err.println("TOTAL ADMINISTRACIÓN: " + this.administracion);

        int valorMitadAdministracion = this.numeroBusesCompleto * this.administracion;
        int diferenciaGastos = this.totaltGastos - valorMitadAdministracion;

        if (totalDias > 0) {
            this.valorDia = Math.round(diferenciaGastos / this.totalDias);
        } else {
            this.valorDia = 0;
        }

        System.err.println("total dias:" + this.totalDias + "DIFERENCIA DE GASTOS: " + diferenciaGastos + " valor dia: " + this.valorDia);
    }

    public int getTotalAdministracion() {
        return totalAdministracion;
    }

    public void setTotalAdministracion(int totalAdministracion) {
        this.totalAdministracion = totalAdministracion;
    }

    public String getFormatValue(int valor) {
        return format.format(valor);
    }
    
    public String getFormatAdminitracion(){
        return getFormatValue(this.totalAdministracion);
    }

    public void traspaso() {
        this.totalAdministracion = 0;
        for (DiasBusesHelper d : this.items) {
            if (d.getDt() > 14) {
                d.setMontoAdministracion(administracion);
            } else {

                switch (d.getDt()) {
                    case 1:
                        d.setMontoAdministracion((int) (administracion * 0.05));
                        break;
                    case 2:
                        d.setMontoAdministracion((int) (administracion * 0.1));
                        break;
                    case 3:
                        d.setMontoAdministracion((int) (administracion * 0.15));
                        break;
                    case 4:
                        d.setMontoAdministracion((int) (administracion * 0.20));
                        break;
                    case 5:
                        d.setMontoAdministracion((int) (administracion * 0.25));
                        break;
                    case 6:
                        d.setMontoAdministracion((int) (administracion * 0.30));
                        break;
                    case 7:
                        d.setMontoAdministracion((int) (administracion * 0.35));
                        break;
                    case 8:
                        d.setMontoAdministracion((int) (administracion * 0.40));
                        break;
                    case 9:
                        d.setMontoAdministracion((int) (administracion * 0.45));
                        break;
                    case 10:
                        d.setMontoAdministracion((int) (administracion * 0.50));
                        break;
                    case 11:
                        d.setMontoAdministracion((int) (administracion * 0.60));
                        break;
                    case 12:
                        d.setMontoAdministracion((int) (administracion * 0.65));
                        break;
                    case 13:
                        d.setMontoAdministracion((int) (administracion * 0.75));
                        break;
                    case 14:
                        d.setMontoAdministracion((int) (administracion * 0.85));
                        break;
                }

            }

            this.totalAdministracion = totalAdministracion + d.getMontoAdministracion();
        }

    }

    public void save() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {

            for (DiasBusesHelper d : this.items) {
                CargoBus cargo = new CargoBus();

                cargo.setTipoCargo(tipoCargo);
                cargo.setBus(d.getBus());
                cargo.setFechaIngresoCargoBus(new Date());
                cargo.setFechaInicioCargoBus(this.fecha);
                cargo.setNumeroCuotasCargoBus(1);
                cargo.setTotalCuotasCargoBus(0);
                cargo.setMontoCargoBus(d.montoAdministracion);
                cargo.setDescripcion(" ");
                cargo.setIdCargo(1);
                cargo.setActivoCargoBus(true);

                session.save(cargo);
            }

            tx.commit();
            JsfUtil.addSuccessMessage("Se ha realizado el cargo de ADMINISTRACIÓN" + this.items.size() + " cargos");
            this.items = new ArrayList<>();

        } catch (HibernateException e) {
            tx.rollback();
            System.err.println("NULL:CargoBus");
        }

    }

    public List<Bus> getItemsBuses() {
        return itemsBuses;
    }

    public void setItemsBuses(List<Bus> itemsBuses) {
        this.itemsBuses = itemsBuses;
    }

    public List<Guia> getItemsGuias() {
        return itemsGuias;
    }

    public void setItemsGuias(List<Guia> itemsGuias) {
        this.itemsGuias = itemsGuias;
    }

    public List<DiasBusesHelper> getItems() {
        return items;
    }

    public void setItems(List<DiasBusesHelper> items) {
        this.items = items;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public int getAdministracion() {
        return administracion;
    }

    public void setAdministracion(int administracion) {
        this.administracion = administracion;
    }

    public int getTotaltGastos() {
        return totaltGastos;
    }

    public void setTotaltGastos(int totaltGastos) {
        this.totaltGastos = totaltGastos;
    }

    public int getNumeroBuses() {
        return numeroBuses;
    }

    public void setNumeroBuses(int numeroBuses) {
        this.numeroBuses = numeroBuses;
    }

    public int getNumeroBusesCompleto() {
        return numeroBusesCompleto;
    }

    public void setNumeroBusesCompleto(int numeroBusesCompleto) {
        this.numeroBusesCompleto = numeroBusesCompleto;
    }

    public int getNumeroBusesProporcional() {
        return numeroBusesProporcional;
    }

    public void setNumeroBusesProporcional(int numeroBusesProporcional) {
        this.numeroBusesProporcional = numeroBusesProporcional;
    }

    public int getTotalDias() {
        return totalDias;
    }

    public void setTotalDias(int totalDias) {
        this.totalDias = totalDias;
    }

    public int getValorDia() {
        return valorDia;
    }

    public void setValorDia(int valorDia) {
        this.valorDia = valorDia;
    }

    public class DiasBusesHelper {

        private Bus bus;
        private int dt;
        private int montoAdministracion;

        public DiasBusesHelper() {
        }

        public DiasBusesHelper(Bus bus, int dt) {
            this.bus = bus;
            this.dt = dt;
        }

        public Bus getBus() {
            return bus;
        }

        public void setBus(Bus bus) {
            this.bus = bus;
        }

        public int getDt() {
            return dt;
        }

        public void setDt(int dt) {
            this.dt = dt;
        }

        public int getMontoAdministracion() {
            return montoAdministracion;
        }

        public void setMontoAdministracion(int montoAdministracion) {
            this.montoAdministracion = montoAdministracion;
        }

    }

}
