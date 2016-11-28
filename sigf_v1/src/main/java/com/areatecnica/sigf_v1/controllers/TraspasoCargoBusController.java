/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.CargoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoCargoDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.CargoBus;
import com.areatecnica.sigf_v1.entities.TipoCargo;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "traspasoCargoBusController")
@ViewScoped
public class TraspasoCargoBusController implements Serializable {

    private TipoCargoDaoImpl tipoCargoDao;
    private CargoBusDaoImpl cargoBusDaoImpl;
    private BusDaoImpl busDaoImpl;

    private CargoBus selected;
    private CargoBus rowSelected;

    private List<TipoCargo> tipoCargoItems;
    private List<CargoBus> items;
    private Bus bus;
    private List<Bus> busItems;

    private int mes;
    private int anio;
    private Date fecha;
    private Date fechaCargo;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public TraspasoCargoBusController() {
        this.tipoCargoDao = new TipoCargoDaoImpl();
        this.tipoCargoItems = this.tipoCargoDao.findAll();

        this.busDaoImpl = new BusDaoImpl();
        this.busItems = this.busDaoImpl.findAll();

        this.cargoBusDaoImpl = new CargoBusDaoImpl();
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
        this.selected = new CargoBus();
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {

                for (CargoBus cargo : this.items) {

                    CargoBus cargoBus = new CargoBus();
                    cargoBus.setActivoCargoBus(cargo.isActivoCargoBus());
                    cargoBus.setBus(cargo.getBus());
                    cargoBus.setDescripcion(cargo.getDescripcion());
                    cargoBus.setFechaIngresoCargoBus(new Date());
                    cargoBus.setFechaInicioCargoBus(this.fechaCargo);
                    cargoBus.setTipoCargo(cargo.getTipoCargo());
                    cargoBus.setMontoCargoBus(cargo.getMontoCargoBus());
                    cargoBus.setNumeroCuotasCargoBus(cargo.getNumeroCuotasCargoBus());
                    cargoBus.setTotalCuotasCargoBus(cargo.getTotalCuotasCargoBus());

                    session.saveOrUpdate(cargoBus);
                }

                tx.commit();
                JsfUtil.addSuccessMessage("Se han registrado " + this.items.size() + " cargos");
                this.items = new ArrayList<>();
                this.selected = new CargoBus();
                this.fechaCargo = new Date();
                
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
            }
        } else {

        }
    }

    public void resetParents() {

    }

    public void delete() {
        if (this.rowSelected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.delete(this.rowSelected);
                tx.commit();

                JsfUtil.addSuccessMessage("El cargo:" + this.rowSelected.getTipoCargo() + " fue eliminado del Bus N°: " + this.rowSelected.getBus() + " Unidad: " + this.rowSelected.getBus().getUnidadNegocio().getNombreUnidadNegocio() + " Patente: " + this.rowSelected.getBus().getPatente());

                this.items.remove(this.rowSelected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
            }
        } else {

        }
    }

    public void setDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + this.mes + "/" + this.anio;
        try {
            this.fecha = format.parse(from);

            this.selected = new CargoBus();
            this.items = new ArrayList<>();

        } catch (ParseException p) {

        }
    }

    public void setMontoXDefecto() {
        if (this.selected.getTipoCargo() != null) {
            this.selected.setMontoCargoBus(this.selected.getTipoCargo().getMontoDefecto());
        }
    }

    public void loadDescripcion() {
        if (this.items != null && !this.items.isEmpty()) {
            for (CargoBus r : this.items) {
                if (!"".equals(this.selected.getDescripcion())) {
                    r.setDescripcion(this.selected.getDescripcion());
                }
            }
        }
    }

    public void loadNumeroCuotas() {
        if (this.items != null && !this.items.isEmpty()) {
            for (CargoBus r : this.items) {
                if (!"".equals(this.selected.getNumeroCuotasCargoBus())) {
                    r.setNumeroCuotasCargoBus(this.selected.getNumeroCuotasCargoBus());
                }
            }
        }
    }

    public void loadTotalCuotas() {
        if (this.items != null && !this.items.isEmpty()) {
            for (CargoBus r : this.items) {
                if (!"".equals(this.selected.getTotalCuotasCargoBus())) {
                    r.setTotalCuotasCargoBus(this.selected.getTotalCuotasCargoBus());
                }
            }
        }
    }

    public void loadActivo() {
        if (this.items != null && !this.items.isEmpty()) {
            for (CargoBus r : this.items) {
                r.setNumeroCuotasCargoBus(0);
                r.setTotalCuotasCargoBus(0);
            }
        }
    }

    public void loadMonto() {
        if (this.items != null && !this.items.isEmpty()) {
            for (CargoBus r : this.items) {
                if (!"".equals(this.selected.getMontoCargoBus())) {
                    r.setMontoCargoBus(this.selected.getMontoCargoBus());
                }
            }
        }
    }

    public void loadCargos() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + this.mes + "/" + this.anio;
        try {
            this.fecha = format.parse(from);
            System.err.println("FECHA SELECCIONADA:" + this.mes + " " + this.anio + " :" + this.fecha);
        } catch (ParseException p) {

        }
        this.cargoBusDaoImpl = new CargoBusDaoImpl();

        this.items = this.cargoBusDaoImpl.findByCargoAndDate(this.selected.getTipoCargo(), fecha);
    }

    public void loadFecha() {
        if (this.items != null && !this.items.isEmpty()) {
            for (CargoBus r : this.items) {
                r.setFechaInicioCargoBus(this.fechaCargo);
            }
        }
    }

    public void remove() {
        if (this.rowSelected != null) {
            this.items.remove(this.rowSelected);
        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<CargoBus> getItems() {
        return items;
    }

    public void setItems(List<CargoBus> items) {
        this.items = items;
    }

    public CargoBus getSelected() {
        return selected;
    }

    public void setSelected(CargoBus selected) {
        this.selected = selected;
    }

    public void setBusItems(List<Bus> trabajadorItems) {
        this.busItems = trabajadorItems;
    }

    public List<Bus> getBusItems() {
        return this.busItems;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public List<TipoCargo> getTipoCargoItems() {
        return tipoCargoItems;
    }

    public void setTipoCargoItems(List<TipoCargo> tipoCargoItems) {
        this.tipoCargoItems = tipoCargoItems;
    }

    public CargoBus getRowSelected() {
        return rowSelected;
    }

    public void setRowSelected(CargoBus rowSelected) {
        this.rowSelected = rowSelected;
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

    public Date getFechaCargo() {
        return fechaCargo;
    }

    public void setFechaCargo(Date fechaCargo) {
        this.fechaCargo = fechaCargo;
    }
}
