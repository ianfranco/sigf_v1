/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.BoletoDao;
import com.areatecnica.sigf_v1.dao.BoletoDaoImpl;
import com.areatecnica.sigf_v1.dao.InventarioTerminalDao;
import com.areatecnica.sigf_v1.dao.InventarioTerminalDaoImpl;
import com.areatecnica.sigf_v1.dao.TerminalDao;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.entities.InventarioTerminal;
import com.areatecnica.sigf_v1.entities.Terminal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Ian Franco
 */
@Named(value = "newInventarioTerminalController")
@ViewScoped
public class RegistroInventarioTerminalController implements Serializable {

    private List<InventarioTerminal> items;
    private List<Boleto> itemsBoletos;
    private List<Terminal> itemsTerminal;
    private InventarioTerminalDao inventarioDao;
    private BoletoDao boletoDao;
    private TerminalDao terminalDao;
    
    private InventarioTerminal selected;
    /**
     * Creates a new instance of newInventarioTerminalController
     */
    public RegistroInventarioTerminalController() {
        this.inventarioDao = new InventarioTerminalDaoImpl();
        this.boletoDao = new BoletoDaoImpl();
        this.terminalDao = new TerminalDaoImpl();
        this.items = this.inventarioDao.findByEstado(false);
        this.itemsBoletos = this.boletoDao.findAll();
        this.itemsTerminal = this.terminalDao.findAll();
    }

    public List<InventarioTerminal> getItems() {
        return items;
    }

    public List<Boleto> getItemsBoletos() {
        return itemsBoletos;
    }

    public List<Terminal> getItemsTerminal() {
        return itemsTerminal;
    }

    public InventarioTerminal getSelected() {
        return selected;
    }

    public void setSelected(InventarioTerminal selected) {
        this.selected = selected;
    }
    
    
    
}
