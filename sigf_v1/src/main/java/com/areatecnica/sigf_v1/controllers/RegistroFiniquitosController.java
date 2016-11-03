/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.CausalFiniquitoDaoImpl;
import com.areatecnica.sigf_v1.dao.FiniquitoDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.CausalFiniquito;
import com.areatecnica.sigf_v1.entities.FiniquitoRelacionLaboral;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
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
@Named(value = "registroFiniquitosController")
@ViewScoped
public class RegistroFiniquitosController implements Serializable {

    private FiniquitoDaoImpl finiquitoDaoImpl;
    private TrabajadorDao trabajadorDao;
    private RelacionLaboralDaoImpl relacionLaboralDao;
    private CausalFiniquitoDaoImpl causalFiniquitoDao;

    //datalist 
    private List<FiniquitoRelacionLaboral> items;

    private List<RelacionLaboral> relacionLaboralItems;
    private List<Trabajador> trabajadorItems;
    private List<CausalFiniquito> causalFiniquitoItems;

    private FiniquitoRelacionLaboral selected;
    private Trabajador trabajador;
    private CausalFiniquito causalFiniquito;
    private RelacionLaboral relacionLaboral;

    public RegistroFiniquitosController() {
        this.finiquitoDaoImpl = new FiniquitoDaoImpl();
        this.items = this.finiquitoDaoImpl.findWithLimit();

        this.trabajadorDao = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDao.findAll();

        this.causalFiniquitoDao = new CausalFiniquitoDaoImpl();
        this.causalFiniquitoItems = this.causalFiniquitoDao.findAll();

        this.causalFiniquito = this.causalFiniquitoItems.get(0);
        this.selected = prepareCreate();

    }

    public FiniquitoRelacionLaboral prepareCreate() {
        FiniquitoRelacionLaboral newRelacionLaboral;
        newRelacionLaboral = new FiniquitoRelacionLaboral();

        return newRelacionLaboral;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            this.selected.setCausalFiniquito(this.causalFiniquito);
            this.selected.setRelacionLaboral(this.relacionLaboral);

            try {

                System.err.println("SI LLEGA " + causalFiniquito);

                this.selected.setCausalFiniquito(this.causalFiniquito);
                this.selected.setRelacionLaboral(this.relacionLaboral);
                this.selected.setFechaRegistroFiniquito(new Date());
                this.selected.setAniosAdicionales(0);
                this.selected.setAniosServicio(0);

                session.save(this.selected);
                
                this.relacionLaboral.setEstado(Boolean.FALSE);
                this.relacionLaboral.setFechaFin(this.selected.getFechaFiniquito());
                
                session.update(this.relacionLaboral);
                
                tx.commit();

                this.items.add(0, selected);
                
                JsfUtil.addSuccessMessage("Se registrado un nuevo finiquito al trabajador: "+this.selected.getRelacionLaboral().getTrabajador());
                
                this.selected = new FiniquitoRelacionLaboral();
                this.trabajador = null;
                this.relacionLaboral = null;
                

                this.selected = new FiniquitoRelacionLaboral();
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

    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void handleConductorChange() {
        this.relacionLaboralDao = new RelacionLaboralDaoImpl();
        this.relacionLaboralItems = this.relacionLaboralDao.findByTrabajador(this.trabajador);
        if (!this.relacionLaboralItems.isEmpty()) {
            this.relacionLaboral = this.relacionLaboralItems.get(0);
        }
    }

    public List<RelacionLaboral> getRelacionLaboralItems() {
        return relacionLaboralItems;
    }

    public void setRelacionLaboralItems(List<RelacionLaboral> relacionLaboralItems) {
        this.relacionLaboralItems = relacionLaboralItems;
    }

    public FiniquitoRelacionLaboral getSelected() {
        return selected;
    }

    public void setSelected(FiniquitoRelacionLaboral selected) {
        this.selected = selected;
    }

    public List<FiniquitoRelacionLaboral> getItems() {
        return items;
    }

    public void setItems(List<FiniquitoRelacionLaboral> items) {
        this.items = items;
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public List<CausalFiniquito> getCausalFiniquitoItems() {
        return causalFiniquitoItems;
    }

    public void setCausalFiniquitoItems(List<CausalFiniquito> causalFiniquitoItems) {
        this.causalFiniquitoItems = causalFiniquitoItems;
    }

    public CausalFiniquito getCausalFiniquito() {
        return causalFiniquito;
    }

    public void setCausalFiniquito(CausalFiniquito causalFiniquito) {
        this.causalFiniquito = causalFiniquito;
    }

    public RelacionLaboral getRelacionLaboral() {
        return relacionLaboral;
    }

    public void setRelacionLaboral(RelacionLaboral relacionLaboral) {
        this.relacionLaboral = relacionLaboral;
    }

}
