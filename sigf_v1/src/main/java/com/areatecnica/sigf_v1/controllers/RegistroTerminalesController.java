/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.TerminalDao;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Ian Franco
 */
@Named(value = "registroTerminalesController")
@SessionScoped
public class RegistroTerminalesController implements Serializable {

    private List<Terminal> items;
    private TerminalDao terminalDao;
    private Terminal selected;
    private MapModel emptyModel;
    private MapModel draggableModel;
    private Marker marker;
    private double lat;
    private double lng;

    /**
     * Creates a new instance of RegistroTerminalesController
     */
    public RegistroTerminalesController() {

        this.terminalDao = new TerminalDaoImpl();
        this.items = this.terminalDao.findAll();
    }

    @PostConstruct
    public void init() {
        this.draggableModel = new DefaultMapModel();

        LatLng coord1 = new LatLng(-33.07640382976732, -71.45355834960935);

        draggableModel.addOverlay(new Marker(coord1));

        for (Marker premarker : draggableModel.getMarkers()) {
            premarker.setDraggable(true);
        }
    }

    public Terminal prepareCreate(ActionEvent event) {
        Terminal newTerminal;

        newTerminal = new Terminal();
        this.selected = newTerminal;
        //init();
        this.selected.setUbicacionLongitud(-33.07640382976732);
        this.selected.setUbicacionLatitud(-71.45355834960935);
        return newTerminal;
    }

    public String delete() {
        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            try {

                session.delete(this.selected);
                tx.commit();

                this.items.remove(this.selected);
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            }
        }
        return null;
    }

    public void saveNew() {

        if (this.selected != null) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();
            try {

                session.saveOrUpdate(this.selected);
                tx.commit();
                this.items.add(this.selected);
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            }
        } else {
            System.err.println("ES NULO EL VALOR SELECTED!!!");
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
                e.printStackTrace();
                tx.rollback();
            }
        }
    }

    public List<Terminal> getItems() {
        return items;
    }

    public void setItems(List<Terminal> items) {
        this.items = items;
    }

    public Terminal getSelected() {
        return selected;
    }

    public void setSelected(Terminal selected) {
        this.selected = selected;
    }

    public void onMarkerDrag(MarkerDragEvent event) {
        marker = event.getMarker();
        LatLng latlng = marker.getLatlng();
        this.lat = latlng.getLat();
        this.lng = latlng.getLng();
        this.selected.setUbicacionLatitud(lat);
        this.selected.setUbicacionLongitud(lng);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha movido el marcador\n Lat:" + this.lat + "\nLng:" + this.lng, "Lat:" + this.lat + ", Lng:" + this.lng));

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public MapModel getDraggableModel() {
        return draggableModel;
    }

    public void setDraggableModel(MapModel draggableModel) {
        this.draggableModel = draggableModel;
    }

    public void setMarker() {
        this.draggableModel = new DefaultMapModel();

        LatLng coord1 = new LatLng(this.selected.getUbicacionLatitud(), this.selected.getUbicacionLongitud());
        this.draggableModel.addOverlay(new Marker(coord1));

        for (Marker premarker : draggableModel.getMarkers()) {
            premarker.setDraggable(true);
        }
    }

    public MapModel getEmptyModel() {
        return emptyModel;
    }

    public void resetParents(){
        
    }
    
}
