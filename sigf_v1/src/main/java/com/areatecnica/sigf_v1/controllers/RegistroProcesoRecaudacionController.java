/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.EgresoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.dao.ProcesoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.dao.ServicioDao;
import com.areatecnica.sigf_v1.dao.TerminalDao;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.Servicio;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
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
@Named(value = "registroProcesoRecaudacionController")
@ViewScoped
public class RegistroProcesoRecaudacionController implements Serializable {

    private List<ProcesoRecaudacion> items;
    private List<Servicio> itemsServicio;
    private List<EgresoRecaudacion> itemsEgresoRecaudacion;
    private List<Terminal> itemsTerminal;
    
    private ProcesoRecaudacion selected;
    private Servicio selectedServicio;
    private EgresoRecaudacion selectedEgresoRecaudacion;
    private Terminal selectedTerminal;
    
    private ServicioDao servicioDao;
    private ProcesoRecaudacionDaoImpl procesoRecaudacionDao;
    private EgresoRecaudacionDaoImpl egresoRecaudacionDaoImpl;
    private TerminalDao terminalDao;

    /**
     * Creates a new instance of RegistroTerminalesController
     */
    public RegistroProcesoRecaudacionController() {
        this.procesoRecaudacionDao = new ProcesoRecaudacionDaoImpl();
        this.items = this.procesoRecaudacionDao.findAll();        
    }
    
    public List<ProcesoRecaudacion> getItems() {
        return items;
    }

    public void setItems(List<ProcesoRecaudacion> items) {
        this.items = items;
    }

    public List<Servicio> getItemsServicio() {
        return itemsServicio;
    }

    public void setItemsServicio(List<Servicio> itemsServicio) {
        this.itemsServicio = itemsServicio;
    }

    public List<EgresoRecaudacion> getItemsEgresoRecaudacion() {
        return itemsEgresoRecaudacion;
    }

    public void setItemsEgresoRecaudacion(List<EgresoRecaudacion> itemsEgresoRecaudacion) {
        this.itemsEgresoRecaudacion = itemsEgresoRecaudacion;
    }

    public ProcesoRecaudacion getSelected() {
        return selected;
    }

    public void setSelected(ProcesoRecaudacion selected) {
        this.selected = selected;
    }

    public Servicio getSelectedServicio() {
        return selectedServicio;
    }

    public void setSelectedServicio(Servicio selectedServicio) {
        this.selectedServicio = selectedServicio;
    }

    public EgresoRecaudacion getSelectedEgresoRecaudacion() {
        return selectedEgresoRecaudacion;
    }

    public void setSelectedEgresoRecaudacion(EgresoRecaudacion selectedEgresoRecaudacion) {
        this.selectedEgresoRecaudacion = selectedEgresoRecaudacion;
    }
    
    
    public Servicio prepareCreate(ActionEvent event) {
        Servicio newServicio;
        try {
            newServicio = new Servicio();
            this.selectedServicio = newServicio;

            return newServicio;
        } catch (Exception ex) {

        }
        return null;
    }

    public String delete() {
        if (this.selectedServicio != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            try {

                session.delete(this.selectedServicio);
                tx.commit();
                
                this.items.remove(this.selectedServicio);
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            }
        }
        return null;        
    }

    public void saveNew() {

        if (this.selectedServicio != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            try {

                session.saveOrUpdate(this.selectedServicio);
                tx.commit();
                //this.items.add(this.selectedServicio);
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            }
        } else {
            System.err.println("ES NULO EL VALOR SELECTED!!!");
        }

        
    }

    public void save() {
        if (this.selectedServicio != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            try {

                session.saveOrUpdate(this.selectedServicio);
                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            }
        }        
    }

    public void init(){
        this.terminalDao = new TerminalDaoImpl();
        this.itemsTerminal = this.terminalDao.findAll();
    }

}
