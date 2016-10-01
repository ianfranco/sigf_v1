/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ianfr
 */
public class ContratosEmpresas implements Serializable{

    private Empresa empresa;
    private List<RelacionLaboral> contratos;

    public ContratosEmpresas() {
    }

    public ContratosEmpresas(Empresa empresa, List<RelacionLaboral> contratos) {
        this.empresa = empresa;
        this.contratos = contratos;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<RelacionLaboral> getContratos() {
        return contratos;
    }

    public void setContratos(List<RelacionLaboral> contratos) {
        this.contratos = contratos;
    }
}
