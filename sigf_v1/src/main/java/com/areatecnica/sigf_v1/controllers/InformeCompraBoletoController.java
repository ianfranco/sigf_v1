/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.CompraBoletoDao;
import com.areatecnica.sigf_v1.dao.CompraBoletoDaoImpl;
import com.areatecnica.sigf_v1.entities.CompraBoleto;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ian Franco
 */
@Named(value = "informeCompraBoletoController")
@SessionScoped
public class InformeCompraBoletoController implements Serializable {

    
    private Date desde;
    private Date hasta;
    private CompraBoleto selected;
    private List<CompraBoleto> items;
    private CompraBoletoDao dao;
    
    /**
     * Creates a new instance of InformeCompraBoletoController
     */
    public InformeCompraBoletoController() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        
        String desdeS = "01/"+month+"/"+year;
        
        try {
            this.desde = format.parse(desdeS);
        } catch (ParseException ex) {
            Logger.getLogger(InformeCompraBoletoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.hasta = new Date();
        
        
    }
    
    private String loadData(){        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Buscando datos", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        this.dao = new CompraBoletoDaoImpl();
        
        this.items = this.dao.findByFechas(this.desde, this.hasta);
        
        this.selected = new CompraBoleto();
        
        return null;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public CompraBoleto getSelected() {
        return selected;
    }

    public void setSelected(CompraBoleto selected) {
        this.selected = selected;
    }

    public List<CompraBoleto> getItems() {
        return items;
    }

    public void setItems(List<CompraBoleto> items) {
        this.items = items;
    }

    
    
}
