package com.areatecnica.sigf_v1.controllers;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "inventarioInternoController")
@ViewScoped
class InventarioInternoController implements Serializable{

    public InventarioInternoController() {
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

}
