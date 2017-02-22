/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.BoletoDaoImpl;
import com.areatecnica.sigf_v1.dao.CompraBoletoDao;
import com.areatecnica.sigf_v1.dao.CompraBoletoDaoImpl;
import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.entities.CompraBoleto;
import com.areatecnica.sigf_v1.entities.DetalleCompraBoleto;
import com.areatecnica.sigf_v1.entities.InventarioInterno;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ian Franco
 */
@Named(value = "registroCompraBoletoController")
@ViewScoped
public class RegistroCompraBoletoController implements Serializable {
    
    private Boleto boleto;
    private List<Boleto> boletoItems;
    private BoletoDaoImpl boletoDaoImpl;
    private CompraBoleto compraBoleto;
    private DetalleCompraBoleto detalleCompraBoleto;
    private Set<DetalleCompraBoleto> list;
    private CompraBoletoDao dao;
    private int total;

    /**
     * Creates a new instance of newCompraBoletoController
     */
    public RegistroCompraBoletoController() {

        this.dao = new CompraBoletoDaoImpl();
        this.boletoDaoImpl = new BoletoDaoImpl();
        this.boletoItems = this.boletoDaoImpl.findAll();
        if (this.compraBoleto == null) {
            this.compraBoleto = new CompraBoleto();
            this.detalleCompraBoleto = new DetalleCompraBoleto();
            this.list = new HashSet<DetalleCompraBoleto>();
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
        
    public CompraBoleto getCompraBoleto() {
        return compraBoleto;
    }

    public Set<DetalleCompraBoleto> getList() {
        return list;
    }

    public DetalleCompraBoleto getDetalleCompraBoleto() {
        return detalleCompraBoleto;
    }

    public String addDetalleCompra(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;

        DetalleCompraBoleto boleto = new DetalleCompraBoleto();
        boleto.setCompraBoleto(this.compraBoleto);
        boleto.setBoleto(this.detalleCompraBoleto.getBoleto());
        boleto.setCantidadRollos(this.detalleCompraBoleto.getCantidadRollos());
        boleto.setSerie(this.detalleCompraBoleto.getSerie());
        int auxTotal = this.detalleCompraBoleto.getTotal()*this.detalleCompraBoleto.getCantidadRollos();
        boleto.setTotal(auxTotal);

        this.total +=auxTotal; 
        this.compraBoleto.setTotal(total);
        list.add(boleto);

        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se han agregado " + this.detalleCompraBoleto.getCantidadRollos() + " de boletos del tipo " + this.detalleCompraBoleto.getBoleto().getNombreBoleto() + "Por un Valor de: "+this.total, "");
        FacesContext.getCurrentInstance().addMessage(null, message);

        this.detalleCompraBoleto.setBoleto(null);
        this.detalleCompraBoleto.setSerie("0");
        this.detalleCompraBoleto.setCantidadRollos(100);

        return null;
    }

    public void saveNew(ActionEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva Compra REGISTRADA", "");
        FacesContext.getCurrentInstance().addMessage(null, message);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {

            System.err.println("TAMAÑO DE LA LISTA DE DETALLES" + this.compraBoleto.getDetalleCompraBoletos().size());
            this.compraBoleto.setDetalleCompraBoletos(list);
            System.err.println("TAMAÑO DE LA LISTA DE DETALLES DESPUÉS DE ACTUALIZAR:" + this.compraBoleto.getDetalleCompraBoletos().size());
            session.persist(this.compraBoleto);
            
            int a = 0;
            
            List<InventarioInterno> listInventario = new ArrayList<InventarioInterno>();
            
            for (DetalleCompraBoleto d : list) {
               
                session.persist(d);
                a++;
            }
            
            /*session.persist(listInventario);*/
            tx.commit();
            
            this.compraBoleto = new CompraBoleto();
            this.detalleCompraBoleto = new DetalleCompraBoleto();
            this.list = new HashSet<DetalleCompraBoleto>();
        } catch (HibernateException e) {
            tx.rollback();
        }

    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public List<Boleto> getBoletoItems() {
        return boletoItems;
    }

    public void setBoletoItems(List<Boleto> boletoItems) {
        this.boletoItems = boletoItems;
    }
    
    
}
