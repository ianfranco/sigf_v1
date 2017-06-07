/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers.remuneraciones.procesos;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.DescuentoTrabajadorLiquidacionDaoImpl;
import com.areatecnica.sigf_v1.dao.HaberTrabajadorDaoImpl;
import com.areatecnica.sigf_v1.dao.HaberTrabajadorLiquidacionDaoImpl;
import com.areatecnica.sigf_v1.dao.RelacionLaboralDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajador;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.entities.HaberTrabajador;
import com.areatecnica.sigf_v1.entities.HaberTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "registroDescuentoTrabajadorLiquidacionController")
@ViewScoped
public class RegistroDescuentoTrabajadorLiquidacionController implements Serializable {

    private DescuentoTrabajadorDaoImpl descuentoTrabajadorDaoImpl;
    private DescuentoTrabajadorLiquidacionDaoImpl descuentoTrabajadorLiquidacionDaoImpl;
    private TrabajadorDaoImpl trabajadorDaoImpl;
    private RelacionLaboralDaoImpl relacionLaboralDaoImpl;

    private DescuentoTrabajador descuentoTrabajador;
    private DescuentoTrabajadorLiquidacion selected;
    private RelacionLaboral relacionLaboral;

    private List<DescuentoTrabajadorLiquidacion> items;
    
    private List<Trabajador> trabajadorItems;
    private List<RelacionLaboral> relacionLaboralItems;
    
    private Date fecha;
    private int mes;
    private int anio;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public RegistroDescuentoTrabajadorLiquidacionController() {
        this.descuentoTrabajadorDaoImpl = new DescuentoTrabajadorDaoImpl();
        this.descuentoTrabajador = this.descuentoTrabajadorDaoImpl.findById(7);

        this.trabajadorDaoImpl = new TrabajadorDaoImpl();
        this.trabajadorItems = this.trabajadorDaoImpl.findAllClean();

        this.descuentoTrabajadorLiquidacionDaoImpl = new DescuentoTrabajadorLiquidacionDaoImpl();
        this.items = this.descuentoTrabajadorLiquidacionDaoImpl.findWithLimit();

        this.selected = prepareCreate();

    }

    public DescuentoTrabajador getDescuentoTrabajador() {
        return descuentoTrabajador;
    }

    public void setDescuentoTrabajador(DescuentoTrabajador haberTrabajador) {
        this.descuentoTrabajador = haberTrabajador;
    }

    public DescuentoTrabajadorLiquidacion prepareCreate() {
        DescuentoTrabajadorLiquidacion newHaberTrabajadorLiquidacion;
        newHaberTrabajadorLiquidacion = new DescuentoTrabajadorLiquidacion();
        newHaberTrabajadorLiquidacion.setMonto(0);
        newHaberTrabajadorLiquidacion.setNumeroCuotas(0);
        newHaberTrabajadorLiquidacion.setFechaIngresoDescuento(new Date());
        newHaberTrabajadorLiquidacion.setFechaInicioDescuento(new Date());
        return newHaberTrabajadorLiquidacion;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                this.selected.setFechaIngresoDescuento(new Date());
                this.selected.setActivoDescuentoTrabajador(Boolean.TRUE);
                this.selected.setIdRelacionLaboral(this.relacionLaboral.getIdRelacionLaboral());
                this.selected.setDescuentoTrabajador(this.descuentoTrabajador);
                
                DescuentoTrabajador haberAux = this.selected.getDescuentoTrabajador();

                session.save(this.selected);
                tx.commit();
                this.items.add(0, selected);

                JsfUtil.addSuccessMessage("Se ha registrado un abono por:" + this.selected.getMonto() + " al Trabajador:" + this.selected.getTrabajador());
                Date fecha = this.selected.getFechaInicioDescuento();

                this.selected = null;
                this.selected = new DescuentoTrabajadorLiquidacion();
                
                this.selected.setMonto(0);
                this.selected.setNumeroCuotas(0);
                this.selected.setFechaInicioDescuento(fecha);
                this.selected.setDescuentoTrabajador(haberAux);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:selected");
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
                tx.rollback();
                System.err.println("NULL:selected");
            }
        } else {

        }
    }

    public void resetParents() {

    }

    public void delete() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.delete(this.selected);
                tx.commit();
                this.items.remove(this.selected);
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:selected");
            }
        } else {

        }
    }

    public void findEmpresa() {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        this.mes = 5;
        this.anio = 2017;
        
        String from = "01/" + mes + "/" + anio;
        try {
            this.fecha = format.parse(from);
            
        } catch (ParseException p) {

        }

        this.relacionLaboralDaoImpl = new RelacionLaboralDaoImpl();
        this.relacionLaboralItems = this.relacionLaboralDaoImpl.findActivasByTrabajador(fecha, this.selected.getTrabajador());
        
        

    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public List<DescuentoTrabajadorLiquidacion> getItems() {
        return items;
    }

    public void setItems(List<DescuentoTrabajadorLiquidacion> items) {
        this.items = items;
    }

    public DescuentoTrabajadorLiquidacion getSelected() {
        return selected;
    }

    public void setSelected(DescuentoTrabajadorLiquidacion selected) {
        this.selected = selected;
    }

    public List<Trabajador> getTrabajadorItems() {
        return trabajadorItems;
    }

    public void setTrabajadorItems(List<Trabajador> trabajadorItems) {
        this.trabajadorItems = trabajadorItems;
    }

    public RelacionLaboral getRelacionLaboral() {
        return relacionLaboral;
    }

    public void setRelacionLaboral(RelacionLaboral relacionLaboral) {
        this.relacionLaboral = relacionLaboral;
    }

    public List<RelacionLaboral> getRelacionLaboralItems() {
        return relacionLaboralItems;
    }

    public void setRelacionLaboralItems(List<RelacionLaboral> relacionLaboralItems) {
        this.relacionLaboralItems = relacionLaboralItems;
    }
}
