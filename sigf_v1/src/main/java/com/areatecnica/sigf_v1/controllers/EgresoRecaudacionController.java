/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EgresoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.ReorderEvent;

/**
 *
 * @author ianfr
 */
@Named(value = "egresoRecaudacionController")
@ViewScoped
public class EgresoRecaudacionController implements Serializable {

    private EgresoRecaudacionDaoImpl egresoRecaudacionDao;
    private List<EgresoRecaudacion> items;
    private EgresoRecaudacion selected;
    private ProcesoRecaudacion pr;
    
    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public EgresoRecaudacionController() {        
        this.egresoRecaudacionDao = new EgresoRecaudacionDaoImpl();
        
    }

    public List<EgresoRecaudacion> getItems() {
        return items;
    }

    public void setItems(List<EgresoRecaudacion> items) {
        this.items = items;
    }

    public EgresoRecaudacion getSelected() {
        return selected;
    }

    public void setSelected(EgresoRecaudacion selected) {
        this.selected = selected;
    }
    
    
    
     public EgresoRecaudacion prepareCreate(ActionEvent event) {
        EgresoRecaudacion newEgresoRecaudacion;
        newEgresoRecaudacion = new EgresoRecaudacion();
        this.selected = newEgresoRecaudacion;
        this.selected.setPorcentaje(BigDecimal.ZERO);
        this.selected.setValorDefectoEgreso(0);
        return newEgresoRecaudacion;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                
                this.selected.setProcesoRecaudacion(pr);
                
                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(selected);
                this.selected = null;
            } catch (HibernateException e) {
                System.err.println("NULL:EgresoRecaudacion");
                tx.rollback();
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
                System.err.println("NULL:EgresoRecaudacion");
            }
        } else {

        }
    }
    
    public void init(){
        this.items = this.egresoRecaudacionDao.findByProceso(pr);
    }
    
    public void resetParents(){
        
    }
    
    public void delete(){
        
    }

    public String getComponentMessages(String clientComponent, String defaultMessage){
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }
    
    public void onRowReorder(ReorderEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row Moved", "From: " + event.getFromIndex() + ", To:" + event.getToIndex());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public ProcesoRecaudacion getPr() {
        return pr;
    }

    public void setPr(ProcesoRecaudacion pr) {
        this.pr = pr;
    }
    
}
