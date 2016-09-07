/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.EgresoRecaudacionDao;
import com.areatecnica.sigf_v1.dao.EgresoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.dao.EstadoGuiaDao;
import com.areatecnica.sigf_v1.dao.EstadoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.ProcesoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.EstadoGuia;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "registroGuiaController")
@SessionScoped
public class RegistroGuiaController implements Serializable {

    private GuiaDaoImpl guiaDao;
    private ProcesoRecaudacionDaoImpl procesoRecaudacionDaoImpl;
    private EgresoRecaudacionDao egresoRecaudacionDao;
    private EstadoGuiaDao estadoGuiaDao;

    private List<Guia> items;
    private List<ProcesoRecaudacion> procesoRecaudacionItems;
    private List<EgresoRecaudacion> egresosRecaudacionItems;
    private List<EgresoGuia> egresosGuiaItems;
    private List<PorcentajeHelper> porcentajesList;
    
    private Guia selected;

    private Date fechaRecaudacion;
    private String fechaGuia;
    private String stringHeader;
    private SimpleDateFormat format;
    private ProcesoRecaudacion procesoRecaudacion;
    private EstadoGuia estadoGuia;
    private boolean estado;
    private int codigoConductor;
    private int numeroBus;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroGuiaController() {
        this.guiaDao = new GuiaDaoImpl();
        this.procesoRecaudacionDaoImpl = new ProcesoRecaudacionDaoImpl();

        this.procesoRecaudacionItems = this.procesoRecaudacionDaoImpl.findAll();

        this.estado = Boolean.TRUE;
        this.stringHeader = "Datos Guías";
        if(this.selected == null){
            this.selected = new Guia();
        }
    }

    public List<Guia> getItems() {
        return items;
    }

    public void setItems(List<Guia> items) {
        this.items = items;
    }

    public Guia getSelected() {
        return selected;
    }

    public void setSelected(Guia selected) {
        this.selected = selected;
    }

    public Date getFechaRecaudacion() {
        return fechaRecaudacion;
    }

    public void setFechaRecaudacion(Date fechaRecaudacion) {
        this.fechaRecaudacion = fechaRecaudacion;
    }

    public ProcesoRecaudacion getProcesoRecaudacion() {
        return procesoRecaudacion;
    }

    public void setProcesoRecaudacion(ProcesoRecaudacion procesoRecaudacion) {
        this.procesoRecaudacion = procesoRecaudacion;
    }

    public List<ProcesoRecaudacion> getProcesoRecaudacionItems() {
        return procesoRecaudacionItems;
    }

    public void setProcesoRecaudacionItems(List<ProcesoRecaudacion> procesoRecaudacionItems) {
        this.procesoRecaudacionItems = procesoRecaudacionItems;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getStringHeader() {
        return stringHeader;
    }

    public void setStringHeader(String stringHeader) {
        this.stringHeader = stringHeader;
    }

    public int getCodigoConductor() {
        return codigoConductor;
    }

    public void setCodigoConductor(int codigoConductor) {
        this.codigoConductor = codigoConductor;
    }

    public int getNumeroBus() {
        return numeroBus;
    }

    public void setNumeroBus(int numeroBus) {
        this.numeroBus = numeroBus;
    }

    public List<EgresoRecaudacion> getEgresosRecaudacionItems() {
        return egresosRecaudacionItems;
    }

    public void setEgresosRecaudacionItems(List<EgresoRecaudacion> egresosRecaudacionItems) {
        this.egresosRecaudacionItems = egresosRecaudacionItems;
    }

    public List<EgresoGuia> getEgresosGuiaItems() {
        return egresosGuiaItems;
    }

    public void setEgresosGuiaItems(List<EgresoGuia> egresosGuiaItems) {
        this.egresosGuiaItems = egresosGuiaItems;
    }

    public List<PorcentajeHelper> getPorcentajesList() {
        return porcentajesList;
    }

    public void setPorcentajesList(List<PorcentajeHelper> porcentajesList) {
        this.porcentajesList = porcentajesList;
    }
        
    public String getFechaGuia() {
        return fechaGuia;
    }

    public void setFechaGuia(String fechaGuia) {
        this.fechaGuia = fechaGuia;
    }
    
    public Guia prepareCreate(ActionEvent event) {
        Guia newGuia;
        newGuia = new Guia();
        this.selected = newGuia;
        this.estadoGuiaDao = new EstadoGuiaDaoImpl();
        this.estadoGuia = this.estadoGuiaDao.findById(1);
        return newGuia;
    }

    public void init() {
        this.estado = Boolean.FALSE;
        System.err.println("HOLA");
        format = new SimpleDateFormat("dd/MM/yyyy");
        this.stringHeader = "Fecha Recaudación:" + format.format(fechaRecaudacion) + " Proceso:" + this.procesoRecaudacion.getNombreProceso();

        this.items = this.guiaDao.findByFechaAndProceso(fechaRecaudacion, procesoRecaudacion);
        
        this.egresoRecaudacionDao = new EgresoRecaudacionDaoImpl();
        this.egresosRecaudacionItems = this.egresoRecaudacionDao.findByProceso(procesoRecaudacion);
        System.err.println("cantidad egresos:"+this.egresosRecaudacionItems.size());
        System.err.println("cantidad de guias:"+this.items.size());
        setPorcentajes();

    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                
                this.selected.setEstadoGuia(estadoGuia);
                this.selected.setFechaIngresoGuia(new Date());
                this.selected.setFechaRecaudacion(fechaRecaudacion);
                this.selected.setProcesoRecaudacion(procesoRecaudacion);
                //this.selected.set
                
                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(selected);
                this.selected = new Guia();

            } catch (HibernateException e) {
                System.err.println("NULL:Guia");
            }
        } else {

        }
    }

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.saveOrUpdate(this.selected);
                tx.commit();

            } catch (HibernateException e) {
                System.err.println("NULL:Guia");
            }
        } else {

        }
    }

    public void resetParents() {
        JsfUtil.addSuccessMessage("Guía:"+this.selected.getFolio());
    }

    public void delete() {

    }

    private void setPorcentajes() {
        this.egresosGuiaItems = new ArrayList<EgresoGuia>();
        this.porcentajesList = new ArrayList<PorcentajeHelper>();
        BigDecimal bd = new BigDecimal("0");
        int i = 0;
        for (EgresoRecaudacion e : this.egresosRecaudacionItems) {

            EgresoGuia egreso = new EgresoGuia();
            egreso.setEgresoRecaudacion(e);
            egreso.setMonto(0);

            if (e.getPorcentaje().compareTo(bd) == 1) {
                porcentajesList.add(new PorcentajeHelper(e.getPorcentaje(), i));
            }

            this.egresosGuiaItems.add(egreso);
            i++;
        }

    }
    
    public String setValoresVariables() {
        int i = 0;
        for (PorcentajeHelper a : porcentajesList) {
            egresosGuiaItems.get(a.getIndex()).setMonto((int) (a.getBd().floatValue() / 100 * this.selected.getTotalIngresos()));
            i++;
        }
        return null;
    }
    
    public int setTotal() {
        int total = 0;
        for (EgresoGuia e : egresosGuiaItems) {
            total += e.getMonto();
        }
        //list.get(getRowCount()-1).setMonto(total);
        this.selected.setTotalEgresos(total);
        return total;
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
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
