/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;


import com.areatecnica.sigf_v1.dao.BusDao;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.BusServicioDao;
import com.areatecnica.sigf_v1.dao.BusServicioDaoImpl;
import com.areatecnica.sigf_v1.dao.ServicioDao;
import com.areatecnica.sigf_v1.dao.ServicioDaoImpl;
import com.areatecnica.sigf_v1.dao.TerminalDao;
import com.areatecnica.sigf_v1.dao.TerminalDaoImpl;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDao;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.BusServicio;
import com.areatecnica.sigf_v1.entities.Servicio;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Ian
 */
@Named(value = "busesXServicioController")
@SessionScoped
public class BusesXServicioController implements Serializable {
    
    private List<UnidadNegocio> itemsUnidadNegocio;    
    private List<Bus> items;
    private List<Servicio> itemsServicio;
    private List<Terminal> itemsTerminal;

    private BusDao busDao;
    private ServicioDao servicioDao;
    private TerminalDao terminalDao;
    private UnidadNegocioDao unidadNegocioDao;

    private List<ColumnModel> columns;

    private ArrayList<LinkedHashMap> listOfMaps;

    private Bus selected;
    private UnidadNegocio unidadNegocio;

    private List<String> resultsHeader;// = service.getResultsValues(...);
    private Object header;
    /**
     * Creates a new instance of BusesXServicioController
     */
    public BusesXServicioController() {

        this.busDao = new BusDaoImpl();
        this.servicioDao = new ServicioDaoImpl();
        this.terminalDao = new TerminalDaoImpl();

        this.items = this.busDao.findAll();
        this.itemsServicio = this.servicioDao.findAll();
        this.itemsTerminal = this.terminalDao.findAll();

        this.listOfMaps = new ArrayList<LinkedHashMap>();
        
        createDynamicColumns();
        this.header = this.resultsHeader.toArray();
    }

    public void createDynamicColumns() {
        HashMap header = new HashMap();
        BusServicioDao busServicioDao = new BusServicioDaoImpl();
        
        resultsHeader = new ArrayList<String>();
        
        resultsHeader.add("N° Bus");
        resultsHeader.add("Patente");
        
        for (Bus b : items) {
            LinkedHashMap hashMap = new LinkedHashMap();

            hashMap.put("Bus", b.getNumeroBus());
            hashMap.put("Patente", b.getPatente());

            List<BusServicio> lista = busServicioDao.findByBus(b);
            System.err.println("BUS N°:"+b.getNumeroBus()+" PATENTE:"+b.getPatente());
            
            for (BusServicio bs : lista) {
                hashMap.put(bs.getServicio().getNumeroServicio(), "X");
                System.err.print(bs.getServicio().getNumeroServicio()+" ");
                
                String key = String.valueOf(bs.getServicio().getNumeroServicio());
                 
                if(!header.containsKey(key)){
                    header.put(key, key);
                    resultsHeader.add(key);
                }
            }
            System.err.println("");

            this.listOfMaps.add(hashMap);
            
            Object[] a = listOfMaps.get(0).keySet().toArray();
            
            for(Object c:a){
                System.err.println("IMPRIMIENDO VALORES:"+c);
            }
        }        
        System.err.println("REVISANDO VALORES");
        /*for(String s:resultsHeader){
            System.err.println("HEADER:"+s);
        }*/
    }

    public List<LinkedHashMap> getListOfMaps() {
        return listOfMaps;
    }

    public void setListOfMaps(ArrayList<LinkedHashMap> listOfMaps) {
        this.listOfMaps = listOfMaps;
    }

    public List<String> getResultsHeader() {
        return resultsHeader;
    }

    public void setResultsHeader(List<String> resultsHeader) {
        this.resultsHeader = resultsHeader;
    }

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public List<Servicio> getItemsServicio() {
        return itemsServicio;
    }

    public void setItemsServicio(List<Servicio> itemsServicio) {
        this.itemsServicio = itemsServicio;
    }

    static public class ColumnModel implements Serializable {

        private String header;
        private String property;

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

}
