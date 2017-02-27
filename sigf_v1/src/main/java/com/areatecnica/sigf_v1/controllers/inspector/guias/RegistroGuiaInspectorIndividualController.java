/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers.inspector.guias;

import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.EstadoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.EstadoGuia;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.Trabajador;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "registroGuiaInspectorIndividualController")
@ViewScoped
public class RegistroGuiaInspectorIndividualController implements Serializable{

    private Guia selected;
    
    private List<Guia> items;
    private List<Trabajador> itemsTrabajador;
    private List<Bus> itemsBus;
    private List<EstadoGuia> itemsEstadoGuia;
    
    private EstadoGuiaDaoImpl estadoGuiaDaoImpl;
    private GuiaDaoImpl guiaDaoImpl;
    private BusDaoImpl busDaoImpl;
    private TrabajadorDaoImpl trabajadorDaoImpl;
    
    private Date maxDate;
    private Date minDate;
    
    /**
     * Creates a new instance of RegistroGuiaInspectorIndividualController
     */
    public RegistroGuiaInspectorIndividualController() {
        this.minDate = new Date();
        this.guiaDaoImpl = new GuiaDaoImpl();
        
        this.items = this.guiaDaoImpl.findByFechaAndTerminal(maxDate, null);
    }
    
    
    
}
