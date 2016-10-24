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
@Named(value = "registroCargoBusController")
@ViewScoped
public class RegistroCargoBusController implements Serializable {

    private TipoCargoDaoImpl tipoCargoDao;
    private CargoBusDaoImpl cargoBusDaoImpl;
    private BusDaoImpl busDaoImpl;
   
    private CargoBus selected;

    private List<TipoCargo> tipoCargoItems;
    private List<CargoBus> items;
    private Bus bus;
    private List<Bus> busItems;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroCargoBusController() {
        this.tipoCargoDao = new TipoCargoDaoImpl();        
        this.tipoCargoItems = this.tipoCargoDao.findAll();
        
        this.busDaoImpl = new BusDaoImpl();
        this.busItems = this.busDaoImpl.findAll();

        this.cargoBusDaoImpl = new CargoBusDaoImpl();
        this.items = this.cargoBusDaoImpl.findAll();

        this.selected = prepareCreate();

    }

    public CargoBus prepareCreate() {        
        CargoBus newCargoBus;
        newCargoBus = new CargoBus();
        newCargoBus.setMontoCargoBus(0);
        newCargoBus.setNumeroCuotasCargoBus(0);
        newCargoBus.setFechaInicioCargoBus(new Date());
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
                                                
                JsfUtil.addSuccessMessage("Se ha ingresado el cargo de tipo:"+this.selected.getTipoCargo()+" al Bus N°:"+this.selected.getBus()+" por un monto de:"+this.selected.getMontoCargoBus());
                
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
                
                JsfUtil.addSuccessMessage("El cargo:"+this.selected.getTipoCargo()+" fue eliminado del Bus N°: "+this.selected.getBus()+" Unidad: "+this.selected.getBus().getUnidadNegocio().getNombreUnidadNegocio()+" Patente: "+this.selected.getBus().getPatente());
                
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:CargoBus");
            }
        } else {

        }
    }
    
    public void setMontoXDefecto(){
        if(this.selected.getTipoCargo()!=null){
            this.selected.setMontoCargoBus(this.selected.getTipoCargo().getMontoDefecto());
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
}
