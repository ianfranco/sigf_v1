/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EmpresaDao;
import com.areatecnica.sigf_v1.dao.EmpresaDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "historialContratoEmpresaController")
@ViewScoped
public class HistorialContratoEmpresaController implements Serializable {

    private EmpresaDao empresaDao;
    private List<Empresa> items;
    private Empresa selected;

    private RelacionLaboralDaoImpl relacionLaboralDaoImpl;
    private List<RelacionLaboral> contratos;

    private String header;

    public HistorialContratoEmpresaController() {

        this.empresaDao = new EmpresaDaoImpl();
        this.items = this.empresaDao.findAllClean();

        this.relacionLaboralDaoImpl = new RelacionLaboralDaoImpl();
        this.contratos = new ArrayList<>();

    }

    public void init() {
        if (this.selected != null) {
            this.header = "Historial Contratos Empresa:" + this.selected.getNombreEmpresa();
            this.contratos = this.relacionLaboralDaoImpl.findAllByEmpresa(this.selected);
        }

    }

    

    /*public RelacionLaboral prepareCreate(ActionEvent event) {
        RelacionLaboral newRelacionLaboral;
        newRelacionLaboral = new RelacionLaboral();
        this.selected = newRelacionLaboral;
        return newRelacionLaboral;
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
                session.merge(this.selected);
                tx.commit();

            } catch (HibernateException e) {
                System.err.println("SAVE:Empresa");
                tx.rollback();
                JsfUtil.addErrorMessage(e.getMessage());
            }
        } else {

        }
    }*/
    public void resetParents() {

    }

    public void delete() {

    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<RelacionLaboral> getContratos() {
        return contratos;
    }

    public void setContratos(List<RelacionLaboral> contratos) {
        this.contratos = contratos;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

}
