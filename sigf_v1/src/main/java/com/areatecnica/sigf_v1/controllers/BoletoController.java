package com.areatecnica.sigf_v1.controllers;


import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "boletoController")
@ViewScoped
public class BoletoController implements Serializable{

    
    public BoletoController() {
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

    

}