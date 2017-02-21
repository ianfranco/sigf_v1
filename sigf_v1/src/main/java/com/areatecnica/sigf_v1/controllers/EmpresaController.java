/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EmpresaDao;
import com.areatecnica.sigf_v1.dao.EmpresaDaoImpl;
import com.areatecnica.sigf_v1.entities.Empresa;
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
 * @author ianfr
 */
@Named(value = "empresaController")
@ViewScoped
public class EmpresaController implements Serializable {

    private EmpresaDao empresaDao;
    private List<Empresa> items;
    private Empresa selected;

    /**
     * Creates a new instance of TrabajadorController
     */
    public EmpresaController() {
        this.empresaDao = new EmpresaDaoImpl();
        this.items = this.empresaDao.findAll();
    }

    public List<Empresa> getItems() {
        return items;
    }

    public void setItems(List<Empresa> items) {
        this.items = items;
    }

    public Empresa getSelected() {
        return selected;
    }

    public void setSelected(Empresa selected) {
        this.selected = selected;
    }

    public Empresa prepareCreate(ActionEvent event) {
        Empresa newEmpresa;
        newEmpresa = new Empresa();
        this.selected = newEmpresa;
        return newEmpresa;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.save(this.selected);
                tx.commit();
                JsfUtil.addSuccessMessage("Se ha registrado la empresa: " + this.selected + " con código: " + this.selected.getIdEmpresa());

                this.items.add(this.items.size() - 1, selected);

            } catch (HibernateException e) {
                System.err.println("SAVE:Empresa");
                tx.rollback();
                JsfUtil.addErrorMessage(e.getMessage());
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

                JsfUtil.addSuccessMessage("Se ha actualizado la empresa: " + this.selected + " con código: " + this.selected.getIdEmpresa());

            } catch (HibernateException e) {
                System.err.println("SAVE:Empresa");
                tx.rollback();
                JsfUtil.addErrorMessage(e.getMessage());
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
                
                /*
                this.log = new Log();
                this.log.setPrivilegio(priviliegio);
                this.log.setUsuario(user);
                this.log.setTipoAccion("Borrado");
                this.log.setFechaRegistroLog(new Date());
                this.log.setDescripcionLog("Guía Folio N°: " + this.selected.getFolio() + "  Proceso: " + this.procesoRecaudacion.getNombreProceso() + " F.Guía: " + format.format(this.selected.getFechaGuia()) + " F.Recaudación: " + format.format(this.selected.getFechaRecaudacion()));*/

                
                tx.commit();
                
                JsfUtil.addSuccessMessage("Se ha eliminado la empresa: " + this.selected.getNombreEmpresa());

                this.selected = null;
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Empresa");
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

}
