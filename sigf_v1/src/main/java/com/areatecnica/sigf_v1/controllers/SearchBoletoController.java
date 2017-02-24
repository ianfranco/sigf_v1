/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.dao.InventarioTerminalDao;
import com.areatecnica.sigf_v1.dao.InventarioTerminalDaoImpl;
import com.areatecnica.sigf_v1.dao.VentaBoletoDao;
import com.areatecnica.sigf_v1.dao.VentaBoletoGuiaDao;
import com.areatecnica.sigf_v1.entities.InventarioTerminal;
import com.areatecnica.sigf_v1.entities.VentaBoleto;
import com.areatecnica.sigf_v1.entities.VentaBoletoGuia;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
@Named(value = "searchBoletoController")
@ViewScoped
public class SearchBoletoController implements Serializable {

    private String serie;
    private String identificador;
    private String estado;
    private InventarioTerminal inventarioTerminal;
    private InventarioTerminalDao inventarioTerminalDao;
    private VentaBoleto ventaBoleto;
    private VentaBoletoDao ventaBoletoDao;
    private VentaBoletoGuia ventaBoletoGuia;
    private VentaBoletoGuiaDao ventaBoletoGuiaDao;

    /**
     * Creates a new instance of searchBoletoController
     */
    public SearchBoletoController() {
        this.inventarioTerminal = new InventarioTerminal();
        this.estado = "";
    }

    public String searchBoleto() {

        if (!this.serie.equals("")) {

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            try {
                this.inventarioTerminalDao = new InventarioTerminalDaoImpl();
                this.inventarioTerminal = this.inventarioTerminalDao.findByIdentificador(identificador, this.serie);

                if (this.inventarioTerminal != null) {
                    if (this.inventarioTerminal.getEstado()) {
                        this.estado = "VENDIDO";
                    } else {
                        this.estado = "EN TERMINAL";
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha encontrado un Boleto con la serie: " + this.serie, "");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }

                tx.commit();
            } catch (HibernateException | NullPointerException e) {
                e.printStackTrace();

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha encontrado un Boleto con la serie: " + this.serie, "");
                FacesContext.getCurrentInstance().addMessage(null, message);

                tx.rollback();
            }

        } else {

        }

        return null;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public InventarioTerminal getInventarioInterno() {
        return inventarioTerminal;
    }

    public void setInventarioInterno(InventarioTerminal inventarioTerminal) {
        this.inventarioTerminal = inventarioTerminal;
    }

    public VentaBoleto getVentaBoleto() {
        return ventaBoleto;
    }

    public void setVentaBoleto(VentaBoleto ventaBoleto) {
        this.ventaBoleto = ventaBoleto;
    }

    public VentaBoletoGuia getVentaBoletoGuia() {
        return ventaBoletoGuia;
    }

    public void setVentaBoletoGuia(VentaBoletoGuia ventaBoletoGuia) {
        this.ventaBoletoGuia = ventaBoletoGuia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

}
