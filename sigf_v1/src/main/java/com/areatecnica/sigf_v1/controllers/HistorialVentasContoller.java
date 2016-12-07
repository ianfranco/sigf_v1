/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.TerminalDao;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.dao.VentaBoletoDao;
import com.areatecnica.sigf_v1.dao.VentaBoletoDaoImpl;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.VentaBoleto;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
@Named(value = "historialVentasContoller")
@SessionScoped
public class HistorialVentasContoller implements Serializable {

    private VentaBoleto selected;
    private VentaBoletoDao ventaBoletoDao;
    private List<VentaBoleto> list;
    private List<Terminal> filterListTerminal;
    private TerminalDao terminalDao;

    /**
     * Creates a new instance of HistorialVentasContoller
     */
    public HistorialVentasContoller() {

        this.ventaBoletoDao = new VentaBoletoDaoImpl();
        this.terminalDao = new TerminalDaoImpl();
        
        this.list = this.ventaBoletoDao.findAll();
        this.filterListTerminal = this.terminalDao.findAll();

    }

    public VentaBoleto getSelected() {
        return selected;
    }

    public void setSelected(VentaBoleto selected) {
        this.selected = selected;
    }

    public List<VentaBoleto> getList() {
        return list;
    }

    public void setList(List<VentaBoleto> list) {
        this.list = list;
    }

    public List<Terminal> getFilterListTerminal() {
        return filterListTerminal;
    }

    public void setFilterListTerminal(List<Terminal> filterListTerminal) {
        this.filterListTerminal = filterListTerminal;
    }

}
