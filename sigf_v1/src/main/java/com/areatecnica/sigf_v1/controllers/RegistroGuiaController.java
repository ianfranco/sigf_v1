/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.ProcesoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "registroGuiaController")
@SessionScoped
public class RegistroGuiaController implements Serializable {

    private GuiaDaoImpl registroGuiaDao;
    private ProcesoRecaudacionDaoImpl procesoRecaudacionDaoImpl;

    private List<Guia> items;
    private List<ProcesoRecaudacion> procesoRecaudacionItems;

    private Guia selected;

    private Date fechaRecaudacion;
    private ProcesoRecaudacion procesoRecaudacion;
    private boolean estado;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroGuiaController() {
        this.registroGuiaDao = new GuiaDaoImpl();
        this.procesoRecaudacionDaoImpl = new ProcesoRecaudacionDaoImpl();

        this.items = this.registroGuiaDao.findAll();
        this.procesoRecaudacionItems = this.procesoRecaudacionDaoImpl.findAll();
        
        this.estado = false;
    }

    public List<Guia> getItems() {
        return items;
    }

    public void setItems(List<Guia> items) {
        this.items = items;
    }

    public Guia getSelected() {
        return selected;
    }

    public void setSelected(Guia selected) {
        this.selected = selected;
    }

    public Date getFechaRecaudacion() {
        return fechaRecaudacion;
    }

    public void setFechaRecaudacion(Date fechaRecaudacion) {
        this.fechaRecaudacion = fechaRecaudacion;
    }

    public ProcesoRecaudacion getProcesoRecaudacion() {
        return procesoRecaudacion;
    }

    public void setProcesoRecaudacion(ProcesoRecaudacion procesoRecaudacion) {
        this.procesoRecaudacion = procesoRecaudacion;
    }

    public List<ProcesoRecaudacion> getProcesoRecaudacionItems() {
        return procesoRecaudacionItems;
    }

    public void setProcesoRecaudacionItems(List<ProcesoRecaudacion> procesoRecaudacionItems) {
        this.procesoRecaudacionItems = procesoRecaudacionItems;
    }
    
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Guia prepareCreate(ActionEvent event) {
        Guia newGuia;
        newGuia = new Guia();
        this.selected = newGuia;
        return newGuia;
    }

    public void init() {

    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:Guia");
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
                this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:Guia");
            }
        } else {

        }
    }

    public void resetParents() {

    }

    public void delete() {

    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }
    
}
