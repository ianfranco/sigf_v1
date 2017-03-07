/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Flota;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class RecaudacionDiariaQuery {

    private ArrayList<LinkedHashMap> array;
    private String query;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    private Date fecha;
    private LinkedHashMap selectecHashMap;

    public RecaudacionDiariaQuery(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<LinkedHashMap> loadQuery() {
        this.array = new ArrayList<>();

        List list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        this.query = "(SELECT \n"
                + "\n"
                + "\n"
                + "proceso_recaudacion.nombre_general, \n"
                + " \n"
                + "dineros_recaudados(proceso_recaudacion.id_general, '" + format.format(fecha) + "', 1) AS administracion, \n"
                + "dineros_recaudados(proceso_recaudacion.id_general, '" + format.format(fecha) + "', 7) AS licitacion, \n"
                + "dineros_recaudados(proceso_recaudacion.id_general, '" + format.format(fecha) + "', 4) AS taller, \n"
                + "dineros_recaudados(proceso_recaudacion.id_general, '" + format.format(fecha) + "', 12) AS '5%', \n"
                + "dineros_recaudados(proceso_recaudacion.id_general, '" + format.format(fecha) + "', 8) AS nochero, \n"
                + "dineros_recaudados(proceso_recaudacion.id_general, '" + format.format(fecha) + "', 14) AS ahorro\n"
                + "FROM proceso_recaudacion \n"
                + "GROUP BY proceso_recaudacion.id_general\n"
                + "ORDER BY proceso_recaudacion.nombre_general ASC)";
        try {

            list = session.createNativeQuery(query).list();

            for (int i = 0; i < list.size(); i++) {

                Object[] a = (Object[]) list.get(i);
                LinkedHashMap link = null;
                //for (int j = 0; j < a.length; j++) {
                link = new LinkedHashMap();
                link.put("nombre_general", a[0]);
                link.put("administracion", a[1]);
                link.put("licitacion", a[2]);
                link.put("taller", a[3]);
                link.put("5%", a[4]);
                link.put("nochero", a[5]);
                link.put("ahorro", a[6]);
                
                //}
                this.array.add(link);
            }

            System.err.println("TERMINÓOOOOOOO");
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }

        return array;
    }

    public ArrayList<LinkedHashMap> loadQueryByFlota(Flota flota) {
        this.array = new ArrayList<>();

        List list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        this.query = "SELECT \n"
                + "                flota.nombre_flota, \n"
                + "                bus.numero_bus, \n"
                + "                bus.patente, \n"
                + "                unidad_negocio.numero_unidad_negocio,    \n"
                + "                COUNT(guia.folio) AS numeroGuia, \n"
                + "                sum_egreso(bus.id_bus, '" + format.format(fecha) + "', 1) AS Administracion, \n"
                + "                sum_egreso(bus.id_bus, '" + format.format(fecha) + "', 7) AS Licitacion, \n"
                + "                sum_egreso(bus.id_bus, '" + format.format(fecha) + "', 4) AS Taller, \n"
                + "                sum_saldos(bus.id_bus, '" + format.format(fecha) + "') AS Total \n"
                + "                FROM guia \n"
                + "                LEFT JOIN bus ON guia.id_bus = bus.id_bus \n"
                + "                LEFT JOIN flota on bus.id_flota = flota.id_flota\n"
                + "                LEFT JOIN unidad_negocio ON bus.id_unidad_negocio = unidad_negocio.id_unidad_negocio \n"
                + "                WHERE guia.fecha_recaudacion BETWEEN '" + format.format(fecha) + "' AND LAST_DAY('" + format.format(fecha) + "') AND flota.id_flota = " + flota.getIdFlota() + "\n"
                + "                GROUP BY guia.id_bus \n"
                + "                ORDER BY flota.nombre_flota, unidad_negocio.numero_unidad_negocio, bus.numero_bus  ASC";
        try {

            list = session.createNativeQuery(query).list();

            for (int i = 0; i < list.size(); i++) {

                Object[] a = (Object[]) list.get(i);
                LinkedHashMap link = null;
                //for (int j = 0; j < a.length; j++) {
                link = new LinkedHashMap();
                link.put("nombre_general", a[0]);
                link.put("administracion", a[1]);
                link.put("licitacion", a[2]);
                link.put("taller", a[3]);
                link.put("5%", a[4]);
                link.put("nochero", a[5]);
                link.put("ahorro", a[6]);
                //}
                this.array.add(link);
            }

            System.err.println("TERMINÓOOOOOOO");
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }

        return array;
    }

    public ArrayList<LinkedHashMap> getArray() {
        return array;
    }

    public void setArray(ArrayList<LinkedHashMap> array) {
        this.array = array;
    }

    public LinkedHashMap getSelectecHashMap() {
        return selectecHashMap;
    }

    public void setSelectecHashMap(LinkedHashMap selectecHashMap) {
        this.selectecHashMap = selectecHashMap;
    }

}
