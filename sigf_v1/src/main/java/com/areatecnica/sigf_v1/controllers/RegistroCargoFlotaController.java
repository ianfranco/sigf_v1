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
import java.util.ArrayList;
import java.util.Date;
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

    private List<TipoCargo> tipoCargoItems;
    private List<CargoBus> items;
    private List<Bus> busItems;
    private List<Flota> flotaItems;
    private List<UnidadNegocio> unidadNegocioItems;
    private List<RegistroCargoFlota> registroCargoItems;
    
    private Bus bus;
    private UnidadNegocio unidadNegocio;
    private Flota flota;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroCargoFlotaController() {
        this.tipoCargoDao = new TipoCargoDaoImpl();        
        this.tipoCargoItems = this.tipoCargoDao.findAll();
        
        this.flotaDaoImpl = new FlotaDaoImpl();
        this.flotaItems = this.flotaDaoImpl.findAll();
        
        this.unidadNegocioDaoImpl = new UnidadNegocioDaoImpl();
        this.unidadNegocioItems = this.unidadNegocioDaoImpl.findAll();
                
        this.cargoBusDaoImpl = new CargoBusDaoImpl();
        this.items = this.cargoBusDaoImpl.findAll();

        this.selected = prepareCreate();

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
                this.selected.setFechaIngresoCargoBus(new Date());
                this.selected.setActivoCargoBus(Boolean.TRUE);                
                this.selected.setBus(bus);
                this.selected.setIdCargo(this.selected.getTipoCargo().getIdTipoCargo());
                session.save(this.selected);
                tx.commit();
                this.items.add(0, this.selected);
                                                
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

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.saveOrUpdate(this.selected);
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
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
            }
        } else {

        }
    }

    public void setMontoXDefecto(){
        if(this.selected !=null){
            int monto = this.selected.getTipoCargo().getMontoDefecto();
            this.selected.setMontoCargoBus(monto);
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
    
    public List<Bus> getBusItems(){
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

    public List<RegistroCargoFlota> getRegistroCargoItems() {
        return registroCargoItems;
    }

    public void setRegistroCargoItems(List<RegistroCargoFlota> registroCargoItems) {
        this.registroCargoItems = registroCargoItems;
    }
    
    public void handleFlota(){
        if(this.flota !=null){
            this.busDaoImpl = new BusDaoImpl();
            this.busItems = this.busDaoImpl.findByFlota(flota);
            
            registroCargoItems = new ArrayList<RegistroCargoFlota>();
            int i = 0;
            for(Bus b:this.busItems){
                RegistroCargoFlota c = new RegistroCargoFlota(b, selected);
                c.setKey(i);
                registroCargoItems.add(c);
                i++;
            }
            
        }
    }
    
    
    public class RegistroCargoFlota{
        
        private int key;
        private Bus bus;
        private CargoBus cargoBus;

        public RegistroCargoFlota(int key, Bus bus, CargoBus cargoBus) {
            this.key = key;
            this.bus = bus;
            this.cargoBus = cargoBus;
        }

        public RegistroCargoFlota() {
        }

        public RegistroCargoFlota(Bus bus, CargoBus cargoBus) {
            this.bus = bus;
            this.cargoBus = cargoBus;
        }

        public Bus getBus() {
            return bus;
        }

        public void setBus(Bus bus) {
            this.bus = bus;
        }

        public CargoBus getCargoBus() {
            return cargoBus;
        }

        public void setCargoBus(CargoBus cargoBus) {
            this.cargoBus = cargoBus;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }
        
    }
}
