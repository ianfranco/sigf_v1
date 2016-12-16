/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.HaberTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.HaberTrabajadorLiquidacionDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.HaberTrabajador;
import com.areatecnica.sigf_v1.entities.HaberTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "aguinaldoController")
@ViewScoped
public class AguinaldoController implements Serializable {

    private HaberTrabajadorDaoImpl haberTrabajadorDaoImpl;
    private HaberTrabajadorLiquidacionDaoImpl haberTrabajadorLiquidacionDaoImpl;
    private TrabajadorDaoImpl trabajadorDaoImpl;

    private HaberTrabajador haberTrabajador;
    private HaberTrabajadorLiquidacion selected;

    private List<HaberTrabajadorLiquidacion> items;
    private List<HaberTrabajador> haberItems;
    private List<Trabajador> trabajadorItems;
    
    private RelacionLaboral relacionLaboral;
    private List<RelacionLaboral> relacionLaboralItems;
    private RelacionLaboralDaoImpl relacionLaboralDao;
    
    private Empresa empresa; 
    private List<Empresa> empresasList;
    
    private Map empresasMap;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public AguinaldoController() {
        this.haberTrabajadorDaoImpl = new HaberTrabajadorDaoImpl();
        this.haberTrabajador = this.haberTrabajadorDaoImpl.findById(8);

        this.trabajadorDaoImpl = new TrabajadorDaoImpl();
        //this.trabajadorItems = this.trabajadorDaoImpl.findAll();

        this.haberTrabajadorLiquidacionDaoImpl = new HaberTrabajadorLiquidacionDaoImpl();
        this.items = this.haberTrabajadorLiquidacionDaoImpl.findAllById(8);

        //this.selected = prepareCreate();
        /*this.relacionLaboralDao = new RelacionLaboralDaoImpl();
        this.relacionLaboralItems = this.relacionLaboralDao.findActivas(new Date(), 0);
        
        this.empresasMap = new HashMap();
        
        this.empresasList = new ArrayList<>();
        
        for (RelacionLaboral r:this.relacionLaboralItems){
            this.empresasMap.put(r.getEmpresa().getIdEmpresa(), r.getEmpresa());
        }
        
        this.empresasList = new ArrayList<Empresa>(empresasMap.values());*/
        
    }

    public HaberTrabajador getHaberTrabajador() {
        return haberTrabajador;
    }

    public void setHaberTrabajador(HaberTrabajador haberTrabajador) {
        this.haberTrabajador = haberTrabajador;
    }

    public HaberTrabajadorLiquidacion prepareCreate() {
        HaberTrabajadorLiquidacion newHaberTrabajadorLiquidacion;
        newHaberTrabajadorLiquidacion = new HaberTrabajadorLiquidacion();
        newHaberTrabajadorLiquidacion.setMonto(0);
        newHaberTrabajadorLiquidacion.setNumeroCuotas(0);
        newHaberTrabajadorLiquidacion.setFechaIngresoHaber(new Date());
        newHaberTrabajadorLiquidacion.setFechaInicioHaber(new Date());
        return newHaberTrabajadorLiquidacion;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngresoHaber(new Date());
                this.selected.setActivoHaberTrabajador(Boolean.TRUE);
                
                HaberTrabajador haberAux = this.selected.getHaberTrabajador();
                                
                session.save(this.selected);
                tx.commit();
                this.items.add(0, selected);
                
                JsfUtil.addSuccessMessage("Se ha registrado un abono por:"+this.selected.getMonto()+" al Trabajador:"+this.selected.getTrabajador());
                Date fecha = this.selected.getFechaInicioHaber();
                
                this.selected = null;
                this.selected = new HaberTrabajadorLiquidacion();
                this.haberTrabajador = null;
                this.selected.setMonto(0);
                this.selected.setNumeroCuotas(0);
                this.selected.setFechaInicioHaber(fecha);
                this.selected.setHaberTrabajador(haberAux);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:selected");
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
                JsfUtil.addSuccessMessage("Se ha actualizado el aguinaldo");
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:selected");
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
                
                JsfUtil.addSuccessMessage("Se ha eliminado el aguinaldo");
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:selected");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<HaberTrabajadorLiquidacion> getItems() {
        return items;
    }

    public void setItems(List<HaberTrabajadorLiquidacion> items) {
        this.items = items;
    }

    public HaberTrabajadorLiquidacion getSelected() {
        return selected;
    }

    public void setSelected(HaberTrabajadorLiquidacion selected) {
        this.selected = selected;
    }

    public List<HaberTrabajador> getHaberItems() {
        return haberItems;
    }

    public void setHaberItems(List<HaberTrabajador> haberItems) {
        this.haberItems = haberItems;
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public RelacionLaboral getRelacionLaboral() {
        return relacionLaboral;
    }

    public void setRelacionLaboral(RelacionLaboral relacionLaboral) {
        this.relacionLaboral = relacionLaboral;
    }

    public List<RelacionLaboral> getRelacionLaboralItems() {
        return relacionLaboralItems;
    }

    public void setRelacionLaboralItems(List<RelacionLaboral> relacionLaboralItems) {
        this.relacionLaboralItems = relacionLaboralItems;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> getEmpresasList() {
        return empresasList;
    }

    public void setEmpresasList(List<Empresa> empresasList) {
        this.empresasList = empresasList;
    }

    public Map getEmpresasMap() {
        return empresasMap;
    }

    public void setEmpresasMap(Map empresasMap) {
        this.empresasMap = empresasMap;
    }
}
