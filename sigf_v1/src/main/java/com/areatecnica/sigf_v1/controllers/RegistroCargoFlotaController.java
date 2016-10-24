/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.CargoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.FlotaDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoCargoDaoImpl;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.CargoBus;
import com.areatecnica.sigf_v1.entities.Flota;
import com.areatecnica.sigf_v1.entities.TipoCargo;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
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
@Named(value = "registroCargoFlotaController")
@ViewScoped
public class RegistroCargoFlotaController implements Serializable {

    private FlotaDaoImpl flotaDaoImpl;
    private UnidadNegocioDaoImpl unidadNegocioDaoImpl;
    private TipoCargoDaoImpl tipoCargoDao;
    private CargoBusDaoImpl cargoBusDaoImpl;
    private BusDaoImpl busDaoImpl;

    private CargoBus selected;
    private TipoCargo tipoCargo;
    private List<TipoCargo> tipoCargoItems;
    private List<CargoBus> items;
    private List<Bus> busItems;
    private List<Flota> flotaItems;
    private List<UnidadNegocio> unidadNegocioItems;
    private List<CargoBus> registroCargoItems;

    private Bus bus;
    private UnidadNegocio unidadNegocio;
    private Flota flota;
    private int monto;
    private int numeroCuotas;
    private Date fecha;
    private int idFina;
    private int mes;
    private int anio;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroCargoFlotaController() {
        this.tipoCargoDao = new TipoCargoDaoImpl();
        this.tipoCargoItems = this.tipoCargoDao.findAll();

        this.flotaDaoImpl = new FlotaDaoImpl();
        this.flotaItems = this.flotaDaoImpl.findAll();

        this.unidadNegocioDaoImpl = new UnidadNegocioDaoImpl();
        this.unidadNegocioItems = this.unidadNegocioDaoImpl.findVinabus();

        this.cargoBusDaoImpl = new CargoBusDaoImpl();
        this.items = this.cargoBusDaoImpl.findAll();

        this.selected = prepareCreate();
        this.selected.setFechaInicioCargoBus(new Date());

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);

    }

    public CargoBus prepareCreate() {
        CargoBus newCargoBus;
        newCargoBus = new CargoBus();
        newCargoBus.setMontoCargoBus(0);
        newCargoBus.setNumeroCuotasCargoBus(0);
        return newCargoBus;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                for (CargoBus r : this.registroCargoItems) {

                    if (r.getMontoCargoBus() != 0) {
                        if (r.isActivoCargoBus()) {
                            System.err.println("descripcion;"+r.getDescripcion());
                            System.err.println("mnto:"+r.getMontoCargoBus());
                            session.update(r);
                        } else {

                            CargoBus cargo = new CargoBus();

                            cargo.setFechaIngresoCargoBus(new Date());
                            cargo.setFechaInicioCargoBus(r.getFechaInicioCargoBus());
                            cargo.setDescripcion(r.getDescripcion());
                            cargo.setNumeroCuotasCargoBus(this.numeroCuotas);
                            cargo.setMontoCargoBus(r.getMontoCargoBus());
                            cargo.setBus(r.getBus());
                            cargo.setTipoCargo(r.getTipoCargo());
                            cargo.setIdCargo(idFina);
                            cargo.setActivoCargoBus(Boolean.TRUE);

                            System.err.println("Bus:" + r.getBus().getPatente() + " MONTO CARGO:" + cargo.getMontoCargoBus() + " MONTO REGISTRO:" + r.getMontoCargoBus());

                            session.save(cargo);
                        }
                    }
                }

                tx.commit();
                JsfUtil.addSuccessMessage("Se ha realizado un registro de cargos a la flota:" + this.flota);

                Date date = this.selected.getFechaInicioCargoBus();
                String descripcion = this.selected.getDescripcion();
                /*this.selected = null;
                this.selected = new CargoBus();
                this.selected.setMontoCargoBus(0);
                this.selected.setNumeroCuotasCargoBus(0);
                this.selected.setFechaInicioCargoBus(date);
                this.selected.setDescripcion(descripcion);*/
                this.flota = null;
                this.unidadNegocio = null;
                this.registroCargoItems = new ArrayList<>();

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
            } 
        } else {

        }
    }

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.update(this.selected);
                tx.commit();

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
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {

                session.delete(this.selected);

                tx.commit();

                JsfUtil.addSuccessMessage("El cargo:" + this.selected.getTipoCargo() + " fue eliminado del Bus N°: " + this.selected.getBus() + " Unidad: " + this.selected.getBus().getUnidadNegocio().getNombreUnidadNegocio() + " Patente: " + this.selected.getBus().getPatente());

                this.registroCargoItems.remove(this.selected);
                this.selected = null;
            } catch (HibernateException e) {
                tx.rollback();

                //JsfUtil.addErrorMessage(e.getLocalizedMessage());
                System.err.println("NULL:CargoBus");
            }
        } else {

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

    public List<Flota> getFlotaItems() {
        return flotaItems;
    }

    public void setFlotaItems(List<Flota> flotaItems) {
        this.flotaItems = flotaItems;
    }

    public List<UnidadNegocio> getUnidadNegocioItems() {
        return unidadNegocioItems;
    }

    public void setUnidadNegocioItems(List<UnidadNegocio> unidadNegocioItems) {
        this.unidadNegocioItems = unidadNegocioItems;
    }

    public UnidadNegocio getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public Flota getFlota() {
        return flota;
    }

    public void setFlota(Flota flota) {
        this.flota = flota;
    }

    public List<CargoBus> getRegistroCargoItems() {
        return registroCargoItems;
    }

    public void setRegistroCargoItems(List<CargoBus> registroCargoItems) {
        this.registroCargoItems = registroCargoItems;
    }

    public void handleFlota() {
        boolean flag = false;
        if (this.flota != null) {

            this.busDaoImpl = new BusDaoImpl();
            this.busItems = this.busDaoImpl.findByFlotaVinabus(flota);

            registroCargoItems = new ArrayList<>();

            int i = 0;
            for (Bus b : this.busItems) {

                this.items = this.cargoBusDaoImpl.findByBusAndDateAndCargo(b, this.selected.getFechaInicioCargoBus(), this.selected.getTipoCargo());

                if (!this.items.isEmpty()) {
                    for (CargoBus c : this.items) {
                        registroCargoItems.add(c);
                    }

                    flag = true;
                } else {
                    CargoBus cargo = new CargoBus();
                    
                    cargo.setFechaIngresoCargoBus(new Date());
                    cargo.setFechaInicioCargoBus(this.selected.getFechaInicioCargoBus());
                    cargo.setMontoCargoBus(0);
                    cargo.setDescripcion(this.selected.getDescripcion());
                    cargo.setBus(b);
                    cargo.setTipoCargo(this.selected.getTipoCargo());
                    registroCargoItems.add(cargo);
                }

                i++;
            }

        }
        if (flag) {
            JsfUtil.addSuccessMessage("Existen cargos del tipo: " + this.selected.getTipoCargo().getNombreTipoCargo() + " ingresados a la flota:" + this.flota.getNombreFlota() + ". Los cambios que realice afectarán a todos los cargos registrados");
        }
    }

    public void loadUnidad() {
        boolean flag = false;
        if (this.unidadNegocio != null) {

            this.busDaoImpl = new BusDaoImpl();
            this.busItems = this.busDaoImpl.findByFlotaAndUnidad(this.flota, this.unidadNegocio);

            registroCargoItems = new ArrayList<>();

            int i = 0;
            for (Bus b : this.busItems) {

                this.items = this.cargoBusDaoImpl.findByBusAndDateAndCargo(b, this.selected.getFechaInicioCargoBus(), this.selected.getTipoCargo());

                if (!this.items.isEmpty()) {
                    for (CargoBus c : this.items) {
                        registroCargoItems.add(c);
                        flag = true;
                    }
                } else {
                    CargoBus cargo = new CargoBus();
                    
                    cargo.setFechaIngresoCargoBus(new Date());
                    cargo.setFechaInicioCargoBus(this.selected.getFechaInicioCargoBus());
                    cargo.setMontoCargoBus(0);
                    cargo.setDescripcion(this.selected.getDescripcion());
                    cargo.setBus(b);
                    cargo.setTipoCargo(this.selected.getTipoCargo());
                    registroCargoItems.add(cargo);
                }

                i++;
            }

        } else {
            handleFlota();
        }
        if (flag) {
            JsfUtil.addSuccessMessage("Existen cargos del tipo: " + this.selected.getTipoCargo().getNombreTipoCargo() + " ingresados a la flota:" + this.flota.getNombreFlota() + ". Los cambios que realice afectarán a todos los cargos registrados");
        }
    }

    public void loadDescripcion() {
        if (this.registroCargoItems != null && !this.registroCargoItems.isEmpty()) {
            for (CargoBus r : this.registroCargoItems) {
                if (!"".equals(this.selected.getDescripcion())) {
                    r.setDescripcion(this.selected.getDescripcion());
                }
            }
        }
    }

    public void setMontoXDefecto() {
        if (this.selected != null) {
            this.selected.setMontoCargoBus(monto);
            System.err.println("monto:" + monto);
            if (this.flota != null) {
                for (CargoBus r : registroCargoItems) {
                    r.setMontoCargoBus(monto);
                }
            }
        }
    }

    public void setMontoXDefectoTipoCargo() {
        if (this.selected != null) {
            this.monto = this.selected.getTipoCargo().getMontoDefecto();
            if (this.flota != null) {
                this.flota = null;
                this.registroCargoItems = null;
            }
        }
    }

    public void init() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
        } catch (ParseException p) {

        }

        if (this.flota != null) {

            this.busDaoImpl = new BusDaoImpl();
            this.busItems = this.busDaoImpl.findByFlotaVinabus(flota);

            registroCargoItems = new ArrayList<>();

            int i = 0;
            for (Bus b : this.busItems) {

                this.items = this.cargoBusDaoImpl.findByBusAndDateAndCargo(b, this.fecha, this.tipoCargo);

                if (!this.items.isEmpty()) {
                    for (CargoBus c : this.items) {
                        registroCargoItems.add(c);
                    }
                } else {/*
                    CargoBus cargo = new CargoBus();
                    cargo.setFechaIngresoCargoBus(new Date());
                    cargo.setFechaInicioCargoBus(this.selected.getFechaInicioCargoBus());
                    cargo.setMontoCargoBus(this.monto);
                    cargo.setActivoCargoBus(Boolean.TRUE);
                    cargo.setDescripcion(this.selected.getDescripcion());
                    cargo.setBus(b);
                    cargo.setTipoCargo(this.selected.getTipoCargo());
                    RegistroCargoFlota c = new RegistroCargoFlota(b, cargo);
                    c.setKey(i);
                    registroCargoItems.add(c);*/
                }

                i++;
            }

        }
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(int numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public int getIdFina() {
        return idFina;
    }

    public void setIdFina(int idFina) {
        this.idFina = idFina;
    }

    public TipoCargo getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(TipoCargo tipoCargo) {
        this.tipoCargo = tipoCargo;
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

    public void showMessage(String value) {
        //JsfUtil.addSuccessMessage("modificado:"+value);
    }

}
