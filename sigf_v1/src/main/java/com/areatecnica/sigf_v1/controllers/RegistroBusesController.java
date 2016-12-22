/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.BusDao;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.EmpresaDao;
import com.areatecnica.sigf_v1.dao.EmpresaDaoImpl;
import com.areatecnica.sigf_v1.dao.EstadoBusDaoImpl;
import com.areatecnica.sigf_v1.dao.FlotaDao;
import com.areatecnica.sigf_v1.dao.FlotaDaoImpl;
import com.areatecnica.sigf_v1.dao.ModeloMarcaBusDaoImpl;
import com.areatecnica.sigf_v1.dao.TerminalDao;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDao;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.EstadoBus;
import com.areatecnica.sigf_v1.entities.Flota;
import com.areatecnica.sigf_v1.entities.ModeloMarcaBus;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
@Named(value = "registroBusesController")
@ViewScoped
public class RegistroBusesController implements Serializable {

    private List<Bus> items;
    private List<Empresa> forFilterEmpresa;
    private List<UnidadNegocio> forFilterUnidad;
    private List<Flota> forFilterFlota;
    
    private List<Terminal> terminalItems;
    private List<Empresa> empresaItems;
    private List<UnidadNegocio> unidadItems;
    private List<Flota> flotaItems;
    private List<ModeloMarcaBus> modeloMarcaItems;
    
    private Bus selected;
    private EstadoBus estadoBus;
    
    private BusDao busDao;
    private EmpresaDao empresaDao;
    private UnidadNegocioDao negocioDao;
    private FlotaDao flotaDao;
    private TerminalDao terminalDao;
    private EstadoBusDaoImpl estadoBusDao;
    private ModeloMarcaBusDaoImpl modeloMarcaBusDao;
    
    /**
     * Creates a new instance of RegistroBusesController
     */
    
    public RegistroBusesController() {
        this.busDao = new BusDaoImpl();
        this.empresaDao         = new EmpresaDaoImpl();
        this.negocioDao         = new UnidadNegocioDaoImpl();
        this.flotaDao           = new FlotaDaoImpl();
        this.terminalDao = new TerminalDaoImpl();
        this.estadoBusDao = new EstadoBusDaoImpl();
        
        this.items = this.busDao.findAll();
        this.forFilterEmpresa   =   this.empresaDao.findAll();
        this.forFilterUnidad    =   this.negocioDao.findAll();
        this.forFilterFlota     =   this.flotaDao.findAll();        
        
        this.terminalItems = this.terminalDao.findAll();
        this.flotaItems = this.flotaDao.findAll();
        this.unidadItems = this.negocioDao.findAll();
        this.empresaItems = this.empresaDao.findAllClean();
        this.modeloMarcaItems = this.modeloMarcaBusDao.findAll();        
    }

    public Bus prepareCreate(ActionEvent event) {        
        Bus newItem;
        newItem = new Bus();
        this.selected = newItem;        
        this.estadoBus = this.estadoBusDao.findById(1);        
        return newItem;
    }
    
    public void resetParents() {
        
    }
    
    public void saveNew() {

        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            try {

                this.selected.setEstadoBus(estadoBus);
                this.selected.setFechaIngresoBus(new Date());
                this.selected.setActivo(Boolean.TRUE);
                
                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(this.selected);
                
                this.selected = null;
                                
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            }
        } else {
            System.err.println("ES NULO EL VALOR SELECTED!!!");
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
                e.printStackTrace();
                tx.rollback();
            }
        }
    }
    
    public List<Bus> getItems() {
        return items;
    }

    public void setItems(List<Bus> items) {
        this.items = items;
    }

    public Bus getSelected() {
        return selected;
    }

    public void setSelected(Bus selected) {
        this.selected = selected;
    }

    public List<Empresa> getForFilterEmpresa() {
        return forFilterEmpresa;
    }

    public void setForFilterEmpresa(List<Empresa> forFilterEmpresa) {
        this.forFilterEmpresa = forFilterEmpresa;
    }

    public List<UnidadNegocio> getForFilterUnidad() {
        return forFilterUnidad;
    }

    public void setForFilterUnidad(List<UnidadNegocio> forFilterUnidad) {
        this.forFilterUnidad = forFilterUnidad;
    }

    public List<Flota> getForFilterFlota() {
        return forFilterFlota;
    }

    public void setForFilterFlota(List<Flota> forFilterFlota) {
        this.forFilterFlota = forFilterFlota;
    }

    public void setTerminalItems(List<Terminal> terminalItems) {
        this.terminalItems = terminalItems;
    }

    public List<Terminal> getTerminalItems() {
        return terminalItems;
    }

    public List<Empresa> getEmpresaItems() {
        return empresaItems;
    }

    public List<Flota> getFlotaItems() {
        return flotaItems;
    }

    public List<UnidadNegocio> getUnidadItems() {
        return unidadItems;
    }

    public void setEmpresaItems(List<Empresa> empresaItems) {
        this.empresaItems = empresaItems;
    }

    public void setFlotaItems(List<Flota> flotaItems) {
        this.flotaItems = flotaItems;
    }

    public void setUnidadItems(List<UnidadNegocio> unidadItems) {
        this.unidadItems = unidadItems;
    }

    public List<ModeloMarcaBus> getModeloMarcaItems() {
        return modeloMarcaItems;
    }

    public void setModeloMarcaItems(List<ModeloMarcaBus> modeloMarcaItems) {
        this.modeloMarcaItems = modeloMarcaItems;
    }
    
    
    
}
