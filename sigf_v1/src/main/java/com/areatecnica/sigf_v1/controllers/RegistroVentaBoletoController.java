/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.InventarioInternoDao;
import com.areatecnica.sigf_v1.dao.InventarioInternoDaoImpl;
import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.entities.DetalleVentaBoleto;
import com.areatecnica.sigf_v1.entities.InventarioInterno;
import com.areatecnica.sigf_v1.entities.InventarioTerminal;
import com.areatecnica.sigf_v1.entities.VentaBoleto;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ian Franco
 */
@Named(value = "newVentaBoletoController")
@SessionScoped
public class RegistroVentaBoletoController implements Serializable {

    private VentaBoleto selected;
    private List<DetalleVentaBoleto> items;
    private List<InventarioInterno> inventarioItems;
    private InventarioInterno inventarioInternoSelected;
    private DetalleVentaBoleto detalleVentaBoleto;
    private Boleto boleto;
    private int valorRollo;
    private int suma;

    /**
     * Creates a new instance of newVentaBoletoController
     */
    public RegistroVentaBoletoController() {
        if (this.selected == null) {
            this.selected = new VentaBoleto();
            this.items = new ArrayList<DetalleVentaBoleto>();
            this.detalleVentaBoleto = new DetalleVentaBoleto();
            this.inventarioInternoSelected = new InventarioInterno();
            this.boleto = new Boleto();
            this.suma = 0;
        }
    }

    public VentaBoleto getSelected() {
        return selected;
    }

    public List<DetalleVentaBoleto> getItems() {
        return items;
    }

    public DetalleVentaBoleto getDetalleVentaBoleto() {
        return detalleVentaBoleto;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public List<InventarioInterno> getInventarioItems() {
        return inventarioItems;
    }

    public InventarioInterno getInventarioInternoSelected() {
        return inventarioInternoSelected;
    }

    public void setInventarioInternoSelected(InventarioInterno inventarioInternoSelected) {
        this.inventarioInternoSelected = inventarioInternoSelected;
    }

    public int getValorRollo() {
        return valorRollo;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public void setValorRollo(int valorRollo) {
        this.valorRollo = valorRollo;
    }

    public String addDetalleVenta(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;

        DetalleVentaBoleto detalle = new DetalleVentaBoleto();

        detalle.setInventarioInterno(this.inventarioInternoSelected);
        detalle.setVentaBoleto(this.selected);
        detalle.setTotal(this.inventarioInternoSelected.getCantidadRollos() * this.valorRollo);

        this.suma += detalle.getTotal();
        
        items.add(detalle);

        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se han agregado " + detalle.getInventarioInterno().getCantidadRollos() + " de rollos del tipo " + detalle.getInventarioInterno().getBoleto().getNombreBoleto(), "");
        FacesContext.getCurrentInstance().addMessage(null, message);

        this.detalleVentaBoleto.setInventarioInterno(null);
        this.detalleVentaBoleto.setTotal(0);
        this.boleto = null;

        
        this.selected.setTotalCompra(this.suma);

        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "TOTAL VENTA:" + this.selected.getTotalCompra(), "");
        FacesContext.getCurrentInstance().addMessage(null, message);

        return null;
    }

    public void saveNew() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva Venta ingresada satisfactoriamente", "");
        FacesContext.getCurrentInstance().addMessage(null, message);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.persist(this.selected);
            List<InventarioTerminal> inventarioTerminal = new ArrayList<InventarioTerminal>();
            System.err.println("PROCESO DE REGISTRO DE ENTIDADES EN LA BASE DE DATOS");
            for (DetalleVentaBoleto d : items) {

                /*int originalSerie = d.getInventarioInterno().getSerie();

                for (int i = 0; i < d.getInventarioInterno().getCantidadRollos(); i++) {
                    InventarioTerminal it = new InventarioTerminal();

                    it.setBoleto(d.getInventarioInterno().getBoleto());
                    it.setEstado(false);
                    it.setTerminal(d.getVentaBoleto().getTerminal());
                    it.setIdentificador(d.getInventarioInterno().getIdentificador());
                    it.setSerie(originalSerie);

                    inventarioTerminal.add(it);
                    originalSerie = originalSerie + 1000;
                }*/

                session.persist(d);
            }

            /*for (InventarioTerminal t : inventarioTerminal) {
                session.persist(t);
            }*/

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }

        this.selected = new VentaBoleto();
        this.items = new ArrayList<DetalleVentaBoleto>();
        this.detalleVentaBoleto = new DetalleVentaBoleto();
        this.inventarioInternoSelected = new InventarioInterno();
        this.boleto = new Boleto();
        this.valorRollo = 0;
        this.suma = 0;

    }

    public void handleBoletoChange() {
        if (this.boleto != null) {
            InventarioInternoDao inventarioDao = new InventarioInternoDaoImpl();
            this.inventarioItems = inventarioDao.findByBoleto(this.boleto);
        }
    }

    

}
