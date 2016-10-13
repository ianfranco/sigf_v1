/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.controllers;

import com.areatecnica.sigf_v1.controllers.util.JsfUtil;
import com.areatecnica.sigf_v1.dao.BusDao;
import com.areatecnica.sigf_v1.dao.BusDaoImpl;
import com.areatecnica.sigf_v1.dao.EgresoGuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.EgresoRecaudacionDao;
import com.areatecnica.sigf_v1.dao.EstadoGuiaDao;
import com.areatecnica.sigf_v1.dao.GuiaDaoImpl;
import com.areatecnica.sigf_v1.dao.ProcesoRecaudacionDaoImpl;
import com.areatecnica.sigf_v1.dao.TrabajadorDao;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDao;
import com.areatecnica.sigf_v1.dao.UnidadNegocioDaoImpl;
import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.EstadoGuia;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.ServicioProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ianfr
 */
@Named(value = "informeProduccionBusController")
@ViewScoped
public class InformeProduccionBusController implements Serializable {

    private GuiaDaoImpl guiaDao;
    private UnidadNegocioDao unidadNegocioDao;
    private ProcesoRecaudacionDaoImpl procesoRecaudacionDaoImpl;
    private EgresoRecaudacionDao egresoRecaudacionDao;
    private EstadoGuiaDao estadoGuiaDao;
    private BusDao busDao;
    private TrabajadorDao trabajadorDao;

    private List<Guia> items;
    private List<ProcesoRecaudacion> procesoRecaudacionItems;
    private List<UnidadNegocio> unidadNegocioItems;
    private ArrayList<EgresoRecaudacion> egresosRecaudacionItems;
    private ArrayList<EgresoGuia> egresosGuiaItems;
    private List<Bus> busItems;
    ArrayList<EgresoGuia> arrayEgresosGuias;
    private Set<ServicioProcesoRecaudacion> setServicioProcesoRecaudacion;
    private Map folios;

    private Guia selected;
    private LinkedHashMap selectedHashMap;

    private String numeroBus;
    private String numeroConductor;
    private Trabajador trabajador;
    private Terminal terminal;
    private Bus bus;
    private UnidadNegocio unidadNegocio;
    private int mes;
    private int anio;
    private Date fechaRecaudacion;
    private Date fechaGuiaDate;
    private String stringHeader;
    private SimpleDateFormat format;
    private ProcesoRecaudacion procesoRecaudacion;
    private EstadoGuia estadoGuia;
    private int codigoConductor;

    private boolean guiaIngresada;

    private ArrayList<LinkedHashMap> listOfMaps;
    private List<String> resultsHeader;// = service.getResultsValues(...);
    private List<String> resultsTotals;
    private LinkedHashMap totales;
    private Object header;

    /**
     * Creates a new instance of InstitucionPrevisionController
     */
    public InformeProduccionBusController() {
        this.guiaDao = new GuiaDaoImpl();
        this.busDao = new BusDaoImpl();
        this.unidadNegocioDao = new UnidadNegocioDaoImpl();

        this.unidadNegocioItems = this.unidadNegocioDao.findAll();

        this.stringHeader = "Datos Guías";
        if (this.selected == null) {
            this.selected = new Guia();
        }

        this.selected.setFechaGuia(this.fechaRecaudacion);
        this.listOfMaps = new ArrayList<LinkedHashMap>();

        LinkedHashMap link = new LinkedHashMap();
        //link.put("Datos", header);
        this.listOfMaps.add(link);

        Calendar calendar = GregorianCalendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1;
        this.anio = calendar.get(Calendar.YEAR);
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

    public List<EgresoRecaudacion> getEgresosRecaudacionItems() {
        return egresosRecaudacionItems;
    }

    public void setEgresosRecaudacionItems(ArrayList<EgresoRecaudacion> egresosRecaudacionItems) {
        this.egresosRecaudacionItems = egresosRecaudacionItems;
    }

    public List<EgresoGuia> getEgresosGuiaItems() {
        return egresosGuiaItems;
    }

    public void setEgresosGuiaItems(ArrayList<EgresoGuia> egresosGuiaItems) {
        this.egresosGuiaItems = egresosGuiaItems;
    }

    public String getNumeroBus() {
        return numeroBus;
    }

    public void setNumeroBus(String numeroBus) {
        this.numeroBus = numeroBus;
    }

    public String getNumeroConductor() {
        return numeroConductor;
    }

    public void setNumeroConductor(String numeroConductor) {
        this.numeroConductor = numeroConductor;
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

    public List<Bus> getBusItems() {
        return busItems;
    }

    public void setBusItems(List<Bus> busItems) {
        this.busItems = busItems;
    }

    public boolean isGuiaIngresada() {
        return guiaIngresada;
    }

    public void setGuiaIngresada(boolean guiaIngresada) {
        this.guiaIngresada = guiaIngresada;
    }

    public ArrayList<LinkedHashMap> getListOfMaps() {
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

    public LinkedHashMap getSelectedHashMap() {
        return selectedHashMap;
    }

    public void setSelectedHashMap(LinkedHashMap selectedHashMap) {
        this.selectedHashMap = selectedHashMap;
    }

    public List<String> getResultsTotals() {
        return resultsTotals;
    }

    public void setResultsTotals(List<String> resultsTotals) {
        this.resultsTotals = resultsTotals;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getStringMonth() {
        switch (this.mes) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
        }
        return "";
    }

    public void createDynamicColumns() {
        this.listOfMaps = new ArrayList<LinkedHashMap>();
        HashMap header = new HashMap();
        this.folios = new HashMap<Integer, Integer>();

        resultsHeader = new ArrayList<String>();
        resultsTotals = new ArrayList<>();
        totales = new LinkedHashMap();

        resultsHeader.add("Folio");
        resultsTotals.add("");

        resultsHeader.add("Fecha");
        resultsTotals.add("");

        resultsHeader.add("Recaudación");
        resultsTotals.add("");
        
        resultsHeader.add("Proceso");
        resultsTotals.add("");

        resultsHeader.add("Codigo");
        resultsTotals.add("");

        resultsHeader.add("Conductor");
        resultsTotals.add("Totales");
        

        resultsHeader.add("T.Ingresos");
        

        EgresoGuiaDaoImpl daoImpl = new EgresoGuiaDaoImpl();

        if (!items.isEmpty()) {
            for (Guia g : items) {
                LinkedHashMap hashMap = new LinkedHashMap();

                this.folios.put(g.getFolio(), g.getIdGuia());

                hashMap.put("Folio", g.getFolio());
                hashMap.put("Fecha", format.format(g.getFechaGuia()));
                hashMap.put("Recaudacion", format.format(g.getFechaRecaudacion()));
                hashMap.put("Proceso", g.getProcesoRecaudacion());
                hashMap.put("Codigo", g.getTrabajador().getCodigoTrabajador());
                hashMap.put("Nombre Conductor", g.getTrabajador());
                hashMap.put("Ingresos", g.getTotalIngresos());

                //Suma de total ingresos
                if (totales.containsKey("Ingresos")) {
                    int aux = (int) totales.get("Ingresos");
                    aux += g.getTotalIngresos();
                    totales.put("Ingresos", aux);
                }else{
                    totales.put("Ingresos", g.getTotalIngresos());
                }

                /*Por todos los egresos que estén asociados al proceso de recaudación*/
                for (EgresoRecaudacion er : g.getProcesoRecaudacion().getEgresoRecaudacions()) {
                    /*Pregunto si el egreso es recaudable o no*/
                    if (er.getEgreso().isActivo()) {
                        /*Si el egreso es recaudable, busco en la db el egreso asociado a la guía*/
                        EgresoGuia egresoGuia = daoImpl.findByGuiaAndEgreso(g.getIdGuia(), er);
                        /*Agrego el header*/
                        resultsHeader.add(er.getEgreso().getNombreEgreso());

                        if (egresoGuia != null) {
                            String key = er.getEgreso().getNombreEgreso();
                            hashMap.put(key, egresoGuia.getMonto());

                            if (totales.containsKey(key)) {
                                int aux = (int) totales.get(key);
                                aux += egresoGuia.getMonto();
                                totales.put(key, aux);
                            } else {
                                totales.put(key, egresoGuia.getMonto());
                            }
                        } else {
                            hashMap.put(er.getEgreso().getNombreEgreso(), 0);
                            totales.put(er.getEgreso().getNombreEgreso(), 0);
                        }
                    }
                }

                this.listOfMaps.add(hashMap);

            }

            for (Object i : totales.values()) {
                int totali = (int) i;
                resultsTotals.add(String.valueOf(totali));
            }

        } else {
            LinkedHashMap hashMap = new LinkedHashMap();

            hashMap.put("Folio", "");
            hashMap.put("Fecha", "");
            hashMap.put("Recaudación", "");
            hashMap.put("Proceso", "");
            hashMap.put("Codigo", "");
            hashMap.put("Nombre Conductor", "");
            hashMap.put("T.Ingresos", "");

            /*for (EgresoRecaudacion er : this.egresosRecaudacionItems) {
                //Pregunto si el egreso es recaudable o no
                if (er.getEgreso().isActivo()) {
                    resultsHeader.add(er.getEgreso().getNombreEgreso());
                    hashMap.put(er.getEgreso().getNombreEgreso(), "");
                }
            }*/
            resultsHeader.add("Administracion");
            hashMap.put("Administracion", "0");
            resultsTotals.add("0");
            
            resultsHeader.add("Licitación");
            hashMap.put("Licitación", "0");
            resultsTotals.add("0");
            
            resultsHeader.add("Taller");
            hashMap.put("Taller", "0");
            resultsTotals.add("0");
            
            resultsHeader.add("5%");
            hashMap.put("5%", "0");
            resultsTotals.add("0");
            
            resultsHeader.add("Aseo");
            hashMap.put("Aseo", "0");
            resultsTotals.add("0");

            this.listOfMaps.add(hashMap);
        }

    }

    public void init() {
        format = new SimpleDateFormat("dd/MM/yyyy");
        this.stringHeader = "Producción Bus N°:" + this.bus.getNumeroBus() + " Patente:" + this.bus.getPatente() + " Mes: " + getStringMonth() + " Año:" + anio;

        String from = "01/" + mes + "/" + anio;
        try {
            this.fechaRecaudacion = format.parse(from);
        } catch (ParseException p) {

        }

        //CARGAR LAS GUIAS OBTENIDAS AL ARRAY DE REGISTROGUIALIST
        this.items = this.guiaDao.findByBusBetweenDates(this.bus, fechaRecaudacion);

        createDynamicColumns();
    }

    private ArrayList<EgresoGuia> loadEgresosByGuia(int idGuia) {
        EgresoGuiaDaoImpl daoImpl = new EgresoGuiaDaoImpl();

        this.arrayEgresosGuias = new ArrayList<>();

        for (EgresoRecaudacion eg : this.egresosRecaudacionItems) {
            EgresoGuia egresoGuia = daoImpl.findByGuiaAndEgreso(idGuia, eg);
            if (egresoGuia != null) {
                this.arrayEgresosGuias.add(egresoGuia);
            }
        }
        return this.arrayEgresosGuias;
    }

    public void resetParents() {
        //JsfUtil.addSuccessMessage("Guía:" + this.selected.getFolio());
    }

    public void delete() {

    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    public LinkedHashMap getTotales() {
        return totales;
    }

    public void setTotales(LinkedHashMap totales) {
        this.totales = totales;
    }

    public List<UnidadNegocio> getUnidadNegocioItems() {
        return unidadNegocioItems;
    }

    public void setUnidadNegocioItems(List<UnidadNegocio> unidadNegocioItems) {
        this.unidadNegocioItems = unidadNegocioItems;
    }

    public UnidadNegocio getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public void handleUnidadChange() {
        if (this.unidadNegocio != null) {
            this.busDao = new BusDaoImpl();
            this.busItems = this.busDao.findByUnidad(this.unidadNegocio.getIdUnidadNegocio());
        }
    }

}
