/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EmpresaDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoContratoDaoImpl;
import com.areatecnica.sigf_v1.dao.TipoTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.TipoContrato;
import com.areatecnica.sigf_v1.entities.TipoTrabajador;
import com.areatecnica.sigf_v1.entities.Trabajador;
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
@Named(value = "registroContratosController")
@ViewScoped
public class RegistroContratosController implements Serializable {

    private RelacionLaboralDaoImpl relacionLaboralDaoImpl;
    private TrabajadorDao trabajadorDao;
    private EmpresaDaoImpl empresaDao;
    private TipoContratoDaoImpl tipoContratoDaoImpl;
    private TipoTrabajadorDaoImpl tipoTrabajadorDaoImpl;
    private TerminalDaoImpl terminalDaoImpl;
    
    private List<RelacionLaboral> items;
    private List<Trabajador> trabajadorItems;
    private List<Empresa> empresaItems;
    private List<TipoContrato> tiposContratosItems;
    private List<Terminal> terminalItems;

    private RelacionLaboral selected;
    private Trabajador trabajador;
    private Empresa empresa;
    private TipoTrabajador tipoTrabajador;

    public RegistroContratosController() {
        this.relacionLaboralDaoImpl = new RelacionLaboralDaoImpl();
        this.items = this.relacionLaboralDaoImpl.findWithLimit();

        this.trabajadorDao = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDao.findAll();

        this.empresaDao = new EmpresaDaoImpl();
        this.empresaItems = this.empresaDao.findAllCleanOrderById();

        this.tipoContratoDaoImpl = new TipoContratoDaoImpl();
        this.tiposContratosItems = this.tipoContratoDaoImpl.findAll();

        this.tipoTrabajadorDaoImpl = new TipoTrabajadorDaoImpl();
        this.tipoTrabajador = this.tipoTrabajadorDaoImpl.findById(1);
        
        this.terminalDaoImpl = new TerminalDaoImpl();
        this.terminalItems = this.terminalDaoImpl.findAll();

        prepareCreate();

        if (this.tipoTrabajador != null) {
            System.err.println("SE ENCONTRÓ EL CONDUCTOR");

        }

        this.selected.setTipoTrabajador(this.tipoTrabajador);
        
    }

    public RelacionLaboral prepareCreate() {
        RelacionLaboral newRelacionLaboral;
        newRelacionLaboral = new RelacionLaboral();
        this.selected = newRelacionLaboral;
        this.selected.setSueldoBase(257500);
        this.selected.setIdOperador(0);
        return newRelacionLaboral;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {

                Date fechaContrato = this.selected.getFechaInicio();
                this.selected.setTipoTrabajador(this.tipoTrabajador);
                this.selected.setFechaFin(this.selected.getFechaInicio());
                this.selected.setEstado(Boolean.TRUE);

                session.save(this.selected);
                tx.commit();

                this.items.add(0, selected);
                
                JsfUtil.addSuccessMessage("Se ha ingresado un nuevo contrato al trabajador:"+this.selected.getTrabajador()+" con la empresa: "+this.selected.getEmpresa()+" ");
                
                this.selected = new RelacionLaboral();
                this.selected.setFechaInicio(fechaContrato);
                this.selected.setSueldoBase(257500);
            } catch (HibernateException e) {
                System.err.println("SAVE:Relacion Laboral");
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
                this.selected.setFechaFin(this.selected.getFechaInicio());
                session.update(this.selected);
                tx.commit();
                JsfUtil.addSuccessMessage("Se ha actualizado el contrato");
                this.selected = null;
            } catch (HibernateException e) {
                System.err.println("SAVE:Relacion Laboral");
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
                tx.commit();
                
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                System.err.println("DELETE:Egreso");
                tx.rollback();
            }
        } else {

        }
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<RelacionLaboral> getItems() {
        return items;
    }

    public void setItems(List<RelacionLaboral> items) {
        this.items = items;
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public RelacionLaboral getSelected() {
        return selected;
    }

    public void setSelected(RelacionLaboral selected) {
        this.selected = selected;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void findConductor() {

    }

    public List<Empresa> getEmpresaItems() {
        return empresaItems;
    }

    public void setEmpresaItems(List<Empresa> empresaItems) {
        this.empresaItems = empresaItems;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<TipoContrato> getTiposContratosItems() {
        return tiposContratosItems;
    }

    public void setTiposContratosItems(List<TipoContrato> tiposContratosItems) {
        this.tiposContratosItems = tiposContratosItems;
    }

    public List<Terminal> getTerminalItems() {
        return terminalItems;
    }

    public void setTerminalItems(List<Terminal> terminalItems) {
        this.terminalItems = terminalItems;
    }

}
