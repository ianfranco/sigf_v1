/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AbonoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.CargoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.EgresoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoCargoDaoImpl;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDao;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDaoImpl;
import com.areatecnica.sigf_v1.entities.AbonoBus;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.CargoBus;
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.TipoCargo;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
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
@Named(value = "registroLiquidacionController")
@ViewScoped
public class RegistroLiquidacionController implements Serializable {

    private UnidadNegocioDao unidadNegocioDao;
    private TipoCargoDaoImpl tipoCargoDao;
    private CargoBusDaoImpl cargoBusDaoImpl;
    private AbonoBusDaoImpl abonoBusDaoImpl;
    private GuiaDaoImpl guiaDaoImpl;
    private BusDaoImpl busDaoImpl;
    private EgresoGuiaDaoImpl egresoGuiaDao;

    private CargoBus selected;

    private List<Guia> guiaItems;
    private List<AbonoBus> abonosItems;
    private List<TipoCargo> tipoCargoItems;
    private List<UnidadNegocio> unidadNegocioItems;
    private List<CargoBus> items;
    private List<GuiaHelper> guiaHelper;
    private Bus bus;
    private EgresoGuia egresoGuia;
    private UnidadNegocio unidadNegocio;
    private AbonoBus selectedAbono;
    private List<Bus> busItems;
    private GuiaHelper selectedGuiaHelper;

    private int mes;
    private int anio;
    private Date fecha;

    private int cargos;
    private int abonos;
    private int ingresos;

    private int saldoFinal;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroLiquidacionController() {
        this.unidadNegocioDao = new UnidadNegocioDaoImpl();
        this.unidadNegocioItems = this.unidadNegocioDao.findAll();

        this.tipoCargoDao = new TipoCargoDaoImpl();
        this.tipoCargoItems = this.tipoCargoDao.findAll();

        this.selected = prepareCreate();
        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);

        this.selected = new CargoBus();

        this.ingresos = 0;
        this.cargos = 0;
        this.abonos = 0;
        this.saldoFinal = 0;

    }

    public void init() {

        this.ingresos = 0;
        this.cargos = 0;
        this.abonos = 0;
        this.saldoFinal = 0;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
        } catch (ParseException p) {

        }

        this.cargoBusDaoImpl = new CargoBusDaoImpl();
        this.items = this.cargoBusDaoImpl.findByBus(this.bus);

        this.abonoBusDaoImpl = new AbonoBusDaoImpl();
        this.abonosItems = this.abonoBusDaoImpl.findByBus(bus);

        this.guiaDaoImpl = new GuiaDaoImpl();
        this.guiaItems = this.guiaDaoImpl.findByBusBetweenDates(bus, fecha);

        this.egresoGuiaDao = new EgresoGuiaDaoImpl();
        this.guiaHelper = new ArrayList<>();
        for (Guia g : guiaItems) {

            GuiaHelper helper = new GuiaHelper();

            this.egresoGuia = this.egresoGuiaDao.findByGuiaAndEgresoClean(g.getIdGuia(), 1);
            helper.setAdministracion(this.egresoGuia.getMonto());

            this.egresoGuia = this.egresoGuiaDao.findByGuiaAndEgresoClean(g.getIdGuia(), 7);
            helper.setLicitacion(this.egresoGuia.getMonto());

            helper.setSaldo(helper.getAdministracion() + helper.getLicitacion());

            this.guiaHelper.add(helper);

            this.ingresos = this.ingresos + helper.saldo;
        }

        for (CargoBus c : this.items) {
            this.cargos = this.cargos + c.getMontoCargoBus();
        }

        for (AbonoBus a : this.abonosItems) {
            this.abonos = this.abonos + a.getMontoAbonoBus();
        }

        this.saldoFinal = (this.abonos + this.ingresos) - this.cargos;

    }

    public CargoBus prepareCreate() {
        CargoBus newCargoBus;
        newCargoBus = new CargoBus();
        newCargoBus.setMontoCargoBus(0);
        newCargoBus.setFechaInicioCargoBus(new Date());
        newCargoBus.setNumeroCuotasCargoBus(0);
        this.selected = newCargoBus;
        return newCargoBus;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngresoCargoBus(new Date());
                this.selected.setActivoCargoBus(Boolean.TRUE);
                this.selected.setBus(bus);
                this.selected.setIdCargo(100);
                session.save(this.selected);
                tx.commit();
                this.items.add(0, this.selected);

                this.cargos = 0;

                for (CargoBus c : this.items) {
                    this.cargos = this.cargos + c.getMontoCargoBus();
                }

                for (AbonoBus a : this.abonosItems) {
                    this.abonos = this.abonos + a.getMontoAbonoBus();
                }

                this.saldoFinal = (this.abonos + this.ingresos) - this.cargos;

                this.selected = null;
                this.selected = new CargoBus();
                this.selected.setMontoCargoBus(0);
                this.selected.setNumeroCuotasCargoBus(0);

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
            }
        } else {

        }
    }

    public void handleUnidadChange() {
        if (this.unidadNegocio != null) {
            this.busDaoImpl = new BusDaoImpl();
            this.busItems = this.busDaoImpl.findByUnidad(this.unidadNegocio.getIdUnidadNegocio());
        }
    }

    public void save() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {

            this.cargos = 0;
            for (CargoBus c : this.items) {

                session.update(c);
                this.cargos = this.cargos + c.getMontoCargoBus();
            }

            tx.commit();

            this.abonos = 0;
            for (AbonoBus a : this.abonosItems) {
                this.abonos = this.abonos + a.getMontoAbonoBus();
            }

            this.saldoFinal = (this.abonos + this.ingresos) - this.cargos;

            this.selected = new CargoBus();

        } catch (HibernateException e) {
            tx.rollback();
            System.err.println("NULL:CargoBus");
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

                this.cargos = 0;
                for (CargoBus c : this.items) {

                    this.cargos = this.cargos + c.getMontoCargoBus();
                }

                this.abonos = 0;
                for (AbonoBus a : this.abonosItems) {
                    this.abonos = this.abonos + a.getMontoAbonoBus();
                }

                this.saldoFinal = (this.abonos + this.ingresos) - this.cargos;

                this.items.remove(this.selected);
                
                this.selected = new CargoBus();

            } catch (HibernateException e) {
                tx.rollback();
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

    public List<Guia> getGuiaItems() {
        return guiaItems;
    }

    public void setGuiaItems(List<Guia> guiaItems) {
        this.guiaItems = guiaItems;
    }

    public List<AbonoBus> getAbonosItems() {
        return abonosItems;
    }

    public void setAbonosItems(List<AbonoBus> abonosItems) {
        this.abonosItems = abonosItems;
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

    public UnidadNegocio getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public List<UnidadNegocio> getUnidadNegocioItems() {
        return unidadNegocioItems;
    }

    public void setUnidadNegocioItems(List<UnidadNegocio> unidadNegocioItems) {
        this.unidadNegocioItems = unidadNegocioItems;
    }

    public AbonoBus getSelectedAbono() {
        return selectedAbono;
    }

    public void setSelectedAbono(AbonoBus selectedAbono) {
        this.selectedAbono = selectedAbono;
    }

    public List<GuiaHelper> getGuiaHelper() {
        return guiaHelper;
    }

    public void setGuiaHelper(List<GuiaHelper> guiaHelper) {
        this.guiaHelper = guiaHelper;
    }

    public GuiaHelper getSelectedGuiaHelper() {
        return selectedGuiaHelper;
    }

    public void setSelectedGuiaHelper(GuiaHelper selectedGuiaHelper) {
        this.selectedGuiaHelper = selectedGuiaHelper;
    }

    public int getCargos() {
        return cargos;
    }

    public void setCargos(int cargos) {
        this.cargos = cargos;
    }

    public int getAbonos() {
        return abonos;
    }

    public void setAbonos(int abonos) {
        this.abonos = abonos;
    }

    public int getIngresos() {
        return ingresos;
    }

    public void setIngresos(int ingresos) {
        this.ingresos = ingresos;
    }

    public int getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(int saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public class GuiaHelper {

        private int licitacion;
        private int administracion;
        private int saldo;
        private int folio;

        public int getFolio() {
            return folio;
        }

        public void setFolio(int folio) {
            this.folio = folio;
        }

        public int getLicitacion() {
            return licitacion;
        }

        public void setLicitacion(int licitacion) {
            this.licitacion = licitacion;
        }

        public int getAdministracion() {
            return administracion;
        }

        public void setAdministracion(int administracion) {
            this.administracion = administracion;
        }

        public int getSaldo() {
            return saldo;
        }

        public void setSaldo(int saldo) {
            this.saldo = saldo;
        }

    }

}
