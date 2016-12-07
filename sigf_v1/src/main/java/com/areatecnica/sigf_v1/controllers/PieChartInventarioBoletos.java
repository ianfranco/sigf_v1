/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Ian Franco
 */
@Named(value = "pieChartInventarioBoletos")
@ViewScoped
public class PieChartInventarioBoletos implements Serializable{

    private PieChartModel model;
    
    /**
     * Creates a new instance of PieChartInventarioBoletos
     */
    public PieChartInventarioBoletos() {
    }

    public PieChartModel getModel() {
        return model;
    }

    public void setModel(PieChartModel model) {
        this.model = model;
    }
    
}
