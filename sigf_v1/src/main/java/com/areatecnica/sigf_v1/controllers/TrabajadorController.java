/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.TrabajadorDaoImpl;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
@Named(value = "trabajadorController")
@SessionScoped
public class TrabajadorController implements Serializable {

    private TrabajadorDao trabajadorDao;
    private List<Trabajador> items;
    private Trabajador selected;

    //helpers
    private String nacionalidad;
    private String sexo;
    private boolean regimen;
    private boolean fonasa;

    /**
     * Creates a new instance of TrabajadorController
     */
    public TrabajadorController() {
        this.trabajadorDao = new TrabajadorDaoImpl();
        this.items = this.trabajadorDao.findAll();
        this.selected = new Trabajador();
    }

    @PostConstruct
    public void init() {
        this.selected = new Trabajador();
        nacionalidad = "1";
        sexo = "1";
        fonasa = true;
        regimen = true;
    }

    public List<Trabajador> getItems() {
        return items;
    }

    public void setItems(List<Trabajador> items) {
        this.items = items;
    }

    public Trabajador getSelected() {
        return selected;
    }

    public void setSelected(Trabajador selected) {
        this.selected = selected;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    
    public boolean isFonasa() {
        return fonasa;
    }

    public void setFonasa(boolean fonasa) {
        this.fonasa = fonasa;
    }
    
    
    public boolean getRegimen() {
        return regimen;
    }

    public void setRegimen(boolean regimen) {
        this.regimen = regimen;
    }

    public Trabajador prepareCreate(ActionEvent event) {
        Trabajador newTrabajador;
        newTrabajador = new Trabajador();        
        newTrabajador.setNacionalidad(true);
        newTrabajador.setSexo(true);
        this.selected = newTrabajador;
        return newTrabajador;
    }

    public void saveNew() {
        System.err.println("ENTRA AL MÉTODO");
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            /*try {
                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:Trabajador");
            }*/
            System.err.println("TRABAJADOR"+this.selected.getApellidoMaternoTrabajador());
            System.err.println("TRABAJADOR"+this.selected.getNombreTrabajador());
            System.err.println("TRABAJADOR"+this.selected.getCodigoTrabajador());
            System.err.println("TRABAJADOR"+this.selected.getRutTrabajador());
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
                this.items.add(selected);

            } catch (HibernateException e) {
                System.err.println("NULL:Trabajador");
            }
        } else {

        }
    }

    public void resetParents() {

    }

    public void delete() {

    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

}
