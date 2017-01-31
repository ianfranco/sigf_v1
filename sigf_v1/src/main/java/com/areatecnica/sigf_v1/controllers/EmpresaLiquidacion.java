/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.LiquidacionSueldo;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public class EmpresaLiquidacion {
    
    private Empresa empresa;
    private List<LiquidacionSueldo> liquidaciones;

    public EmpresaLiquidacion() {
    }

    public EmpresaLiquidacion(Empresa empresa, List<LiquidacionSueldo> liquidaciones) {
        this.empresa = empresa;
        this.liquidaciones = liquidaciones;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<LiquidacionSueldo> getLiquidaciones() {
        return liquidaciones;
    }

    public void setLiquidaciones(List<LiquidacionSueldo> liquidaciones) {
        this.liquidaciones = liquidaciones;
    }
    
    
    
}
