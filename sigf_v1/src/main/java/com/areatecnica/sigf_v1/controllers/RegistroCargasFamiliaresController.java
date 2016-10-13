/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.AsignacionFamiliarDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "registroCargasFamiliaresController")
@ViewScoped
public class RegistroCargasFamiliaresController implements Serializable {

    
    private AsignacionFamiliarDaoImpl asignacionFamiliarDao;
    private TrabajadorDaoImpl trabajadorDaoImpl;
    private RelacionLaboralDaoImpl relacionLaboralDaoImpl;
    private List<AsignacionFamiliar> grupoAsignacion;    
    private List<RelacionLaboral> relacionLaboralItems;
    private List<Trabajador> items;
    private Trabajador selected;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroCargasFamiliaresController() {        
        this.asignacionFamiliarDao = new AsignacionFamiliarDaoImpl();
        this.grupoAsignacion = this.asignacionFamiliarDao.findAll();
        
        this.relacionLaboralDaoImpl = new RelacionLaboralDaoImpl();
        this.relacionLaboralItems = this.relacionLaboralDaoImpl.findAll();
        
        this.items = new ArrayList<>();
        
        for(RelacionLaboral r:this.relacionLaboralItems){
            Trabajador t = r.getTrabajador();
            this.items.add(t);
        }
    }

    public List<Trabajador> getItems() {
        return items;
    }

    public void setItems(List<Trabajador> items) {
        this.items = items;
    }

    public Trabajador getSelected() {
        return selected;
    }

    public void setSelected(Trabajador selected) {
        this.selected = selected;
    }
    
    

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.save(this.selected);
                tx.commit();
                this.items.add(selected);

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:AsignacionFamiliar");
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
                System.err.println("NULL:AsignacionFamiliar");
            }
        } else {

        }
    }
    
    public void resetParents(){
        
    }
    
    public void delete(){
        
    }

    public String getComponentMessages(String clientComponent, String defaultMessage){
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<AsignacionFamiliar> getGrupoAsignacion() {
        return grupoAsignacion;
    }

    public void setGrupoAsignacion(List<AsignacionFamiliar> grupoAsignacion) {
        this.grupoAsignacion = grupoAsignacion;
    }
}
