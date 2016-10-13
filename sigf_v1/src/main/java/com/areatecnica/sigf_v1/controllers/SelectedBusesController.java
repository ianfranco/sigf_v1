/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.BusDao;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.EstadoBusDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.EstadoBus;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Ian Franco
 */
@Named(value = "selectedBusesController")
@ViewScoped
public class SelectedBusesController implements Serializable {

    private List<Bus> items;

    private Bus selected;
    private EstadoBus estadoBus;

    private BusDao busDao;
    private EstadoBusDaoImpl estadoBusDao;

    /**
     * Creates a new instance of RegistroBusesController
     */
    public SelectedBusesController() {
        this.busDao = new BusDaoImpl();
        this.estadoBusDao = new EstadoBusDaoImpl();
        this.items = this.busDao.findAllClean();
    }

    public List<Bus> getItems() {
        return items;
    }

    public void setItems(List<Bus> items) {
        this.items = items;
    }

    public Bus getSelected() {
        return selected;
    }

    public void setSelected(Bus selected) {
        this.selected = selected;
    }

}
