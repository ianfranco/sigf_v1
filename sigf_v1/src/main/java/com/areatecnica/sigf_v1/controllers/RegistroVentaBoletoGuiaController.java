/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.BoletoDao;
import com.areatecnica.sigf_v1.dao.BoletoDaoImpl;
import com.areatecnica.sigf_v1.dao.BusDao;
import com.areatecnica.sigf_v1.dao.GuiaDao;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.InventarioTerminalDao;
import com.areatecnica.sigf_v1.dao.InventarioTerminalDaoImpl;
import com.areatecnica.sigf_v1.dao.ServicioDao;
import com.areatecnica.sigf_v1.dao.ServicioDaoImpl;
import com.areatecnica.sigf_v1.dao.TerminalDao;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.dao.VentaBoletoGuiaDao;
import com.areatecnica.sigf_v1.dao.VentaBoletoGuiaDaoImpl;
import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.InventarioTerminal;
import com.areatecnica.sigf_v1.entities.Servicio;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.VentaBoletoGuia;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
@Named(value = "newVentaBoletoGuiaController")
@ViewScoped
public class RegistroVentaBoletoGuiaController implements Serializable {

    private List<VentaBoletoGuia> ventaBoletoGuiaItems;
    private List<InventarioTerminal> inventarioTerminalItems;
    private List<Servicio> servicioItems;
    private List<Boleto> boletoItems;
    private List<Bus> busItems;
    private List<Guia> guiaItems;

    private InventarioTerminalDao inventarioTerminalDao;
    private ServicioDao servicioDao;
    private TerminalDao terminalDao;
    private BoletoDao boletoDao;
    private BusDao busDao;
    private GuiaDao guiaDao;
    private VentaBoletoGuiaDao ventaBoletoGuiaDao;

    private InventarioTerminal inventarioTerminal;
    private Bus bus;
    private Boleto boleto;
    private Servicio servicio;
    private Terminal terminal;
    private VentaBoletoGuia ventaBoletoGuia;
    private int idTerminal;

    public RegistroVentaBoletoGuiaController() {
        
        this.idTerminal = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idTerminal");
        
        this.terminalDao = new TerminalDaoImpl();
        this.terminal = this.terminalDao.findById(idTerminal);
        
        this.boletoDao = new BoletoDaoImpl();
        this.guiaDao = new GuiaDaoImpl();
        this.servicioDao = new ServicioDaoImpl();
        this.inventarioTerminalDao = new InventarioTerminalDaoImpl();

        
        this.guiaItems = this.guiaDao.findByFechaAndTerminal(new Date(), terminal);
        System.err.println("Total Guías:"+this.guiaItems.size());
        this.busItems = new ArrayList<Bus>();
        for (Guia g: guiaItems){
            Bus b = g.getBus();
            this.busItems.add(b);
        }        
        
        this.servicioItems = this.servicioDao.findByIdTerminal(idTerminal);
        this.boletoItems = this.boletoDao.findAll();
        
        this.inventarioTerminalItems = this.inventarioTerminalDao.findByIdTerminal(idTerminal);

        this.ventaBoletoGuia = new VentaBoletoGuia();
        this.ventaBoletoGuia.setRecaudado(Boolean.TRUE);
        this.ventaBoletoGuiaItems = new ArrayList<VentaBoletoGuia>();

        initComponents();
    }

    public List<Boleto> getBoletoItems() {
        return boletoItems;
    }

    public List<Bus> getBusItems() {
        return busItems;
    }

    public List<InventarioTerminal> getInventarioTerminalItems() {
        return inventarioTerminalItems;
    }

    public List<Servicio> getServicioItems() {
        return servicioItems;
    }

    public String saveNew() {
        System.err.println("!!!!!");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        guiaDao = new GuiaDaoImpl();
        Guia guia = guiaDao.findByBusAndFecha(this.bus, new Date());

        try {

            if (guia != null) {
                this.ventaBoletoGuia.setGuia(guia);
                this.ventaBoletoGuia.setInventarioTerminal(this.inventarioTerminal);
                ventaBoletoGuiaItems.add(this.ventaBoletoGuia);
                System.err.println("GUIA nooooooooooooo NULAAAAA!!");
                session.persist(this.ventaBoletoGuia);

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva Venta Ingresada", "");
                FacesContext.getCurrentInstance().addMessage(null, message);

                this.inventarioTerminal.setEstado(Boolean.TRUE);
                session.saveOrUpdate(this.inventarioTerminal);
                
                this.boleto = null;
                this.inventarioTerminalItems = null;
                this.ventaBoletoGuia = new VentaBoletoGuia();
                this.bus = null;
                this.servicio = null;
                
            } else {
                System.err.println("GUIA NULAAAAA!!");
            }

            //
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
        return null;
    }

    public String initComponents() {
        System.err.println("SI INICIA LOS COMPONENTES");
        int idTerminal = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idTerminal");

        guiaDao = new GuiaDaoImpl();

        List<Guia> guiasItems = new ArrayList<Guia>();

        guiasItems = this.guiaDao.findByFechaAndTerminal(new Date(), terminal);

        if (guiasItems != null) {
            System.err.println("SIII SE HAN ENCONTRADO GUIAS PARA BUSCAR VENTAS DE BOLETOS, TAMAÑO DE ARRAY:"+guiasItems.size());
            this.ventaBoletoGuiaDao = new VentaBoletoGuiaDaoImpl();

            for (Guia g : guiasItems) {
                List<VentaBoletoGuia> ventaBoletoGuiaList = this.ventaBoletoGuiaDao.findByGuia(g);
                if (ventaBoletoGuiaList != null) {
                    for (VentaBoletoGuia v : ventaBoletoGuiaList) {
                        ventaBoletoGuiaItems.add(v);
                    }
                }else{
                    System.err.println("NO SE HAN ENCONTRADO VENTAS DE BOLETO POR LA GUIA N°:"+g.getIdGuia());
                }
            }
        }else{
            System.err.println("NO SE HAN ENCONTRADO GUIAS PARA BUSCAR VENTAS DE BOLETOS");
        }
        return null;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public InventarioTerminal getInventarioTerminal() {
        return inventarioTerminal;
    }

    public void setInventarioTerminal(InventarioTerminal inventarioTerminal) {
        this.inventarioTerminal = inventarioTerminal;
    }

    public VentaBoletoGuia getVentaBoletoGuia() {
        return ventaBoletoGuia;
    }

    public void setVentaBoletoGuia(VentaBoletoGuia ventaBoletoGuia) {
        this.ventaBoletoGuia = ventaBoletoGuia;
    }

    public List<VentaBoletoGuia> getVentaBoletoGuiaItems() {
        return ventaBoletoGuiaItems;
    }

    public void handleBusChange() {
        if (this.servicio != null) {
            this.busItems = this.busDao.findByTerminal(this.servicio.getTerminal());
        }
    }

    public void handleBoletoChange() {
        if (this.boleto != null) {
            this.inventarioTerminalItems = this.inventarioTerminalDao.findByBoletoAndIdTerminal(this.boleto, this.idTerminal);
        }
    }
    
    public String test(){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nueva Venta ingresada correctatemente", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return null;
    }
}
