package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.BoletoDaoImpl;
import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named(value = "boletoController")
@ViewScoped
public class BoletoController implements Serializable {

    private Boleto selected;
    private List<Boleto> items;
    private BoletoDaoImpl boletoDaoImpl;

    public BoletoController() {
        this.boletoDaoImpl = new BoletoDaoImpl();
        this.items = this.boletoDaoImpl.findAll();
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

    public Boleto getSelected() {
        return selected;
    }

    public void setSelected(Boleto selected) {
        this.selected = selected;
    }

    public List<Boleto> getItems() {
        return items;
    }

    public void setItems(List<Boleto> items) {
        this.items = items;
    }

    public Boleto prepareCreate(ActionEvent event) {
        Boleto newBoleto;
        newBoleto = new Boleto();
        this.selected = newBoleto;
        return newBoleto;
    }

    public void saveNew() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            try {
                session.save(this.selected);
                tx.commit();

                JsfUtil.addSuccessMessage("Se ha registrado un nuevo Boleto de tipo:" + this.selected.getNombreBoleto());
                this.items.add(selected);

            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Boleto");
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
                JsfUtil.addSuccessMessage("Se ha actualizado el Boleto de tipo:" + this.selected.getNombreBoleto());
            } catch (HibernateException e) {
                tx.rollback();
                System.err.println("NULL:Boleto");
            }
        } else {

        }
    }

}
