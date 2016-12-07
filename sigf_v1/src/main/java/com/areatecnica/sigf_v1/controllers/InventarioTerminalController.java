package com.areatecnica.sigf_v1.controllers;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "inventarioTerminalController")
@ViewScoped
public class InventarioTerminalController implements Serializable{

    public InventarioTerminalController() {
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

}
