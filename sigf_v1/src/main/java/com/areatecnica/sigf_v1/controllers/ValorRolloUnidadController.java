package com.areatecnica.sigf_v1.controllers;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "valorRolloUnidadController")
@ViewScoped
public class ValorRolloUnidadController {

    @Inject
    private UnidadNegocioController idUnidadController;

    public ValorRolloUnidadController() {
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

}
