/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.BoletoDao;
import com.areatecnica.sigf_v1.dao.BoletoDaoImpl;
import com.areatecnica.sigf_v1.dao.InventarioInternoDao;
import com.areatecnica.sigf_v1.dao.InventarioInternoDaoImpl;
import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.entities.InventarioInterno;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
@Named(value = "newInventarioInternoController")
@SessionScoped
public class RegistroInventarioInternoController implements Serializable {

    private List<InventarioInterno> items;
    private List<Boleto> itemsBoletos;
    private InventarioInternoDao inventarioDao;
    private BoletoDao boletoDao;
    
    private InventarioInterno selected;
    
    /**
     * Creates a new instance of newInventarioInternoController
     */
    public RegistroInventarioInternoController() {
        
        this.inventarioDao = new InventarioInternoDaoImpl();
        this.boletoDao = new BoletoDaoImpl();
        this.items = this.inventarioDao.findByEstado(false);
        this.itemsBoletos = this.boletoDao.findAll();
    }

    public List<InventarioInterno> getItems() {
        return items;
    }

    public InventarioInterno getSelected() {
        return selected;
    }
    
    public List<Boleto> getBoletos() {
        return this.itemsBoletos;
    }

    public void setSelected(InventarioInterno selected) {
        this.selected = selected;
    }
    
}
