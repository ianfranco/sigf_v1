/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EmpresaDao;
import com.areatecnica.sigf_v1.dao.EmpresaDaoImpl;
import com.areatecnica.sigf_v1.dao.FiniquitoDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.FiniquitoRelacionLaboral;
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
@Named(value = "historialFiniquitoEmpresaController")
@ViewScoped
public class HistorialFiniqitosEmpresaController implements Serializable {

    private EmpresaDao empresaDao;
    private List<Empresa> items;
    private Empresa selected;

    private FiniquitoDaoImpl relacionLaboralDaoImpl;
    private List<FiniquitoRelacionLaboral> finiquitos;

    private String header;

    public HistorialFiniqitosEmpresaController() {

        this.empresaDao = new EmpresaDaoImpl();
        this.items = this.empresaDao.findAllClean();

        this.relacionLaboralDaoImpl = new FiniquitoDaoImpl();
        this.finiquitos = new ArrayList<>();

    }

    public void init() {
        if (this.selected != null) {
            this.header = "Historial Finiquitos Empresa:" + this.selected.getNombreEmpresa();
            this.finiquitos = this.relacionLaboralDaoImpl.findAllByEmpresa(this.selected);
        }

    }

    public void resetParents() {

    }

    public void delete() {

    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<FiniquitoRelacionLaboral> getContratos() {
        return finiquitos;
    }

    public void setContratos(List<FiniquitoRelacionLaboral> contratos) {
        this.finiquitos = contratos;
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
