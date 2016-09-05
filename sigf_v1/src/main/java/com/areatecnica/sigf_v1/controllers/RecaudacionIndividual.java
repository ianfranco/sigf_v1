/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.BusDao;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.EgresoRecaudacionDao;
import com.areatecnica.sigf_v1.dao.EgresoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDao;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.TerminalDao;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ian Franco
 */
@Named(value = "recaudacionIndividual")
@SessionScoped
public class RecaudacionIndividual implements Serializable {

    private Bus selectedBus;
    private Guia selectedGuia;
    private Terminal terminal;
    private Date fechaRecaudacion;
    private ProcesoRecaudacion procesoRecaudacion;

    private BusDao busDao;
    private TerminalDao terminalDao;
    private GuiaDao guiaDao;
    private EgresoRecaudacionDao egresoTerminalDao;

    private List<Bus> itemsBus;
    private List<Guia> itemsGuia;
    private List<EgresoRecaudacion> itemsEgresoRecaudacion;
    private List<EgresoGuia> itemsEgresoGuia;
    private List<PorcentajeHelper> porcentajesList;

    /**
     * Creates a new instance of RecaudacionIndividual
     */
    public RecaudacionIndividual() {
        this.busDao = new BusDaoImpl();
        this.itemsBus = this.busDao.findAll();
        this.fechaRecaudacion = new Date();
        /*this.egresoTerminalDao = new EgresoRecaudacionDaoImpl();
        this.itemsEgresoRecaudacion = this.egresoTerminalDao.findByProceso(procesoRecaudacion);*/

        //System.err.println("tamaño archivo de egresos:" + this.itemsEgresoRecaudacion);

        

        //setPorcentajes();
        this.selectedGuia = new Guia();

    }

    private void setPorcentajes() {
        this.itemsEgresoGuia = new ArrayList<EgresoGuia>();
        this.porcentajesList = new ArrayList<PorcentajeHelper>();
        BigDecimal bd = new BigDecimal("0");
        int i = 0;
        for (EgresoRecaudacion e : this.itemsEgresoRecaudacion) {

            EgresoGuia egreso = new EgresoGuia();
            egreso.setEgresoRecaudacion(e);
            egreso.setMonto(0);

            if (e.getPorcentaje().compareTo(bd) == 1) {
                porcentajesList.add(new PorcentajeHelper(e.getPorcentaje(), i));
            }

            this.itemsEgresoGuia.add(egreso);
            i++;
        }

    }

    public Bus getSelectedBus() {
        return selectedBus;
    }

    public void setSelectedBus(Bus selectedBus) {
        this.selectedBus = selectedBus;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public List<Bus> getItemsBus() {
        return itemsBus;
    }

    public void setItemsBus(List<Bus> itemsBus) {
        this.itemsBus = itemsBus;
    }

    public Guia getSelectedGuia() {
        return selectedGuia;
    }

    public void setSelectedGuia(Guia selectedGuia) {
        this.selectedGuia = selectedGuia;
    }

    public List<Guia> getItemsGuia() {
        return itemsGuia;
    }

    public void setItemsGuia(List<Guia> itemsGuia) {
        this.itemsGuia = itemsGuia;
    }

    public Date getFechaRecaudacion() {
        return fechaRecaudacion;
    }

    public String getFormatFechaRecaudacion() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fechaRecaudacion);
    }

    public void setFechaRecaudacion(Date fechaRecaudacion) {
        this.fechaRecaudacion = fechaRecaudacion;
    }

    public void handleBusChange() {
        if (this.selectedBus != null) {
            this.guiaDao = new GuiaDaoImpl();
            this.itemsGuia = this.guiaDao.findByBusAndEstado(this.selectedBus, false);
        }
    }

    public void handleGuiaChange() {
        if (this.selectedGuia != null) {

        }
    }

    public String setValoresVariables() {

        int i = 0;
        for (PorcentajeHelper a : porcentajesList) {
            itemsEgresoGuia.get(a.getIndex()).setMonto((int) (a.getBd().floatValue() / 100 * this.selectedGuia.getTotalIngresos()));
            i++;
        }

        return null;
    }

    public int setTotal() {
        int total = 0;
        for (EgresoGuia e : itemsEgresoGuia) {
            total += e.getMonto();
        }
        //list.get(getRowCount()-1).setMonto(total);
        this.selectedGuia.setTotalEgresos(total);
        return total;
    }

    public void save() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        System.err.println("GUIA BUS: " + this.selectedGuia.getBus().getPatente());
        this.selectedGuia.setRecaudada(Boolean.TRUE);
        try {

            //this.selectedGuia.setEgresoGuias((Set) this.itemsGuia);
            session.merge(this.selectedGuia);
            System.err.println("SI LLEGA POR ACa ");

            for (EgresoGuia e : this.itemsEgresoGuia) {
                e.setGuia(selectedGuia);
                session.saveOrUpdate(e);
            }

            tx.commit();

            this.itemsGuia = null;
            setPorcentajes();
            this.selectedGuia = new Guia();
            this.selectedBus = new Bus();
            this.selectedGuia.setTotalEgresos(0);
            this.selectedGuia.setTotalIngresos(0);

        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }

    }

    public String muestra() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;

        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "VALINDANDO GUIA", null);

        return null;
    }

    public String cancelar() {

        this.selectedGuia = null;
        for (int i = 0; i < this.itemsEgresoGuia.size(); i++) {
            this.itemsEgresoGuia.get(i).setMonto(0);
        }
        return null;
    }

    public String getEgresos() {

        return null;
    }

    public List<EgresoRecaudacion> getItemsEgresoRecaudacion() {
        return itemsEgresoRecaudacion;
    }

    public void setItemsEgresoTerminal(List<EgresoRecaudacion> itemsEgresoRecaudacion) {
        this.itemsEgresoRecaudacion = itemsEgresoRecaudacion;
    }

    public List<EgresoGuia> getItemsEgresoGuia() {
        return itemsEgresoGuia;
    }

    public void setItemsEgresoGuia(List<EgresoGuia> itemsEgresoGuia) {
        this.itemsEgresoGuia = itemsEgresoGuia;
    }

    private class PorcentajeHelper {

        private BigDecimal bd;
        private Integer index;

        public PorcentajeHelper(BigDecimal bd, Integer index) {
            this.bd = bd;
            this.index = index;
        }

        public void setBd(BigDecimal bd) {
            this.bd = bd;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public BigDecimal getBd() {
            return bd;
        }

        public Integer getIndex() {
            return index;
        }

    }

}
