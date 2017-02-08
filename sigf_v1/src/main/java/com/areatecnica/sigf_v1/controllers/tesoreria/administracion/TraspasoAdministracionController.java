/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers.tesoreria.administracion;

import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.GastoAdministracionMensualDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.Guia;
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
    private BusDaoImpl busDaoImpl;

    private Date fecha;
    private Date fechaAux;
    private int mes;
    private int anio;
    private int administracion;

    /**
     * Creates a new instance of DiasBusesController
     */
    public TraspasoAdministracionController() {
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
            System.err.println("FECHA:"+this.fecha);
        } catch (ParseException p) {

        }

        /*this.administracionMensualDaoImpl = new GastoAdministracionMensualDaoImpl();
        this.administracion = this.administracionMensualDaoImpl.findTotalByMonthAndYear(fecha);
        System.err.println("TOTAL ADMINISTRACIÓN:"+this.administracion);*/
    }

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
        } catch (ParseException p) {

        }

        this.guiaDao = new GuiaDaoImpl();
        this.itemsGuias = this.guiaDao.findByFecha(fecha);
        this.items = new ArrayList<>();

        for (Guia g : this.itemsGuias) {
            DiasBusesHelper d = new DiasBusesHelper();

            d.setBus(g.getBus());
            d.setDt(this.guiaDao.findDTByBusBetweenDates(g.getBus(), fecha));

            if(d.getDt()>14){
                d.setMontoAdministracion(administracion);
            }else{
                switch(d.getDt()){
                    case 1:
                        d.setMontoAdministracion((int) (administracion*1.1));
                        break;
                    case 2:
                        d.setMontoAdministracion((int) (administracion*1.1));
                        break;
                    case 3:
                        d.setMontoAdministracion((int) (administracion*1.12));
                        break;
                    case 4:
                        d.setMontoAdministracion((int) (administracion*1.13));
                        break;
                    case 5:
                        d.setMontoAdministracion((int) (administracion*1.14));
                        break;
                    case 6:
                        d.setMontoAdministracion((int) (administracion*1.16));
                        break;
                    case 7: 
                        d.setMontoAdministracion((int) (administracion*1.18));
                        break;
                    case 8:
                        d.setMontoAdministracion((int) (administracion*1.20));
                        break;
                    case 9:
                        d.setMontoAdministracion((int) (administracion*1.21));
                        break;
                    case 10:
                        d.setMontoAdministracion((int) (administracion*1.27));
                        break;
                    case 11:
                        d.setMontoAdministracion((int) (administracion*1.30));
                        break;
                    case 12:
                        d.setMontoAdministracion((int) (administracion*1.35));
                        break;
                    case 13:
                        d.setMontoAdministracion((int) (administracion*1.40));
                        break;
                    case 14:
                        d.setMontoAdministracion((int) (administracion*1.45));
                        break;
                }
            }
            
            this.items.add(d);
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
