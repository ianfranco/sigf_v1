/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.BusDao;
import com.areatecnica.sigf_v1.dao.GuiaDao;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDao;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "fichaGuiaController")
@ViewScoped
public class FichaGuiaController implements Serializable {

    private GuiaDao guiaDao;
    private TrabajadorDao trabajadorDao;
    private BusDao busDao;
    private UnidadNegocioDao unidadNegocioDao;

    private Guia selected;
    private Trabajador trabajador;
    private Bus bus;
    private UnidadNegocio unidad;

    private int codigo;
    private String rut;

    /**
     * Creates a new instance of FichaTrabajadorController
     */
    public FichaGuiaController() {
        this.selected = new Guia();
        this.guiaDao = new GuiaDaoImpl();

    }

    @PostConstruct
    public void init() {

    }

    public Guia getSelected() {
        return selected;
    }

    public void setSelected(Guia selected) {
        this.selected = selected;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void save() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {

                session.saveOrUpdate(this.selected);
                tx.commit();
                //this.items.add(selected);

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Guia");
            }
        } else {
            JsfUtil.addErrorMessage("ERROR AL GUARDAR" + codigo);
            System.err.println("El trabajador está en NULL:Trabajador");
        }
    }

    public void findByCodigo() {
        try {
            this.selected = this.guiaDao.findByFolio(codigo);
            if (this.selected == null) {
                JsfUtil.addErrorMessage("No se ha encontrado un trabajador con el código: " + codigo);
            } else {

            }
        } catch (NullPointerException | NumberFormatException e) {
            
            JsfUtil.addErrorMessage("No se ha encontrado un trabajador con el código: ");
        }

    }

    public void setMessage() {
        JsfUtil.addSuccessMessage("VALOR DEL CODIGO:" + codigo);
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public UnidadNegocio getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadNegocio unidad) {
        this.unidad = unidad;
    }

}
