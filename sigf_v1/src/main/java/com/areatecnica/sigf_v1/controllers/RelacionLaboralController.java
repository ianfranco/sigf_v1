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
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "relacionLaboralController")
@ViewScoped
public class RelacionLaboralController implements Serializable {

    private EmpresaDao empresaDao;
    private RelacionLaboralDaoImpl relacionLaboralDaoImpl;
    private List<ContratosEmpresas> contratos;

    public RelacionLaboralController() {
        this.empresaDao = new EmpresaDaoImpl();
        this.relacionLaboralDaoImpl = new RelacionLaboralDaoImpl();
        this.contratos = new ArrayList<>();
        for (Empresa e : this.empresaDao.findAll()) {            
            List<RelacionLaboral> list = this.relacionLaboralDaoImpl.findByEmpresa(e);            
            if (!list.isEmpty()) {                                
                ContratosEmpresas ce = new ContratosEmpresas(e, list);
                this.contratos.add(ce);
            }
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

    public List<ContratosEmpresas> getContratos() {
        return contratos;
    }

    public void setContratos(List<ContratosEmpresas> contratos) {
        this.contratos = contratos;
    }

}
