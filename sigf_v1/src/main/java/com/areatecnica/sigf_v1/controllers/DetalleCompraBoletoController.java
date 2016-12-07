package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.entities.DetalleCompraBoleto;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "detalleCompraBoletoController")
@ViewScoped
public class DetalleCompraBoletoController{

    public DetalleCompraBoletoController() {
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

}
