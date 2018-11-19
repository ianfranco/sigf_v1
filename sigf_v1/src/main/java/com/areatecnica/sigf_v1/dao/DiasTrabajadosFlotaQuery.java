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
public class DiasTrabajadosFlotaQuery {

    private ArrayList<LinkedHashMap> array;
    private String query;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    private Date fecha;

    public DiasTrabajadosFlotaQuery(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<LinkedHashMap> loadQuery() {
        this.array = new ArrayList<>();

        List list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        this.query = "SELECT \n"
                + "                flota.nombre_flota, \n"
                + "                bus.numero_bus, \n"                
                + "                unidad_negocio.numero_unidad_negocio,    \n"
                + "                COUNT(DISTINCT guia.fecha_guia) AS numeroGuia \n"
                + "                FROM guia \n"
                + "                LEFT JOIN bus ON guia.id_bus = bus.id_bus \n"
                + "                LEFT JOIN flota on bus.id_flota = flota.id_flota\n"
                + "                LEFT JOIN unidad_negocio ON bus.id_unidad_negocio = unidad_negocio.id_unidad_negocio \n"
                + "                WHERE guia.fecha_recaudacion BETWEEN '" + format.format(fecha) + "' AND LAST_DAY('" + format.format(fecha) + "') AND guia.total_ingresos > 0\n"
                + "                GROUP BY guia.id_bus \n"
                + "                ORDER BY flota.nombre_flota, unidad_negocio.numero_unidad_negocio, bus.numero_bus  ASC";
        try {

            list = session.createNativeQuery(query).list();

            for (int i = 0; i < list.size(); i++) {

                Object[] a = (Object[]) list.get(i);
                LinkedHashMap link = null;
                //for (int j = 0; j < a.length; j++) {
                link = new LinkedHashMap();
                link.put("Flota", a[0]);
                link.put("N°Bus", a[1]);                
                link.put("Unidad", a[2]);
                link.put("DT", a[3]);
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
                + "                unidad_negocio.numero_unidad_negocio,    \n"
                + "                COUNT(DISTINCT guia.fecha_guia) AS numeroGuia \n"
                + "                FROM guia \n"
                + "                LEFT JOIN bus ON guia.id_bus = bus.id_bus \n"
                + "                LEFT JOIN flota on bus.id_flota = flota.id_flota\n"
                + "                LEFT JOIN unidad_negocio ON bus.id_unidad_negocio = unidad_negocio.id_unidad_negocio \n"
                + "                WHERE guia.fecha_recaudacion BETWEEN '" + format.format(fecha) + "' AND LAST_DAY('" + format.format(fecha) + "') AND flota.id_flota = "+flota.getIdFlota()+" AND guia.total_ingresos > 0\n"
                + "                GROUP BY guia.id_bus \n"
                + "                ORDER BY flota.nombre_flota, unidad_negocio.numero_unidad_negocio, bus.numero_bus  ASC";
        
        
        try {

            list = session.createNativeQuery(query).list();

            for (int i = 0; i < list.size(); i++) {

                Object[] a = (Object[]) list.get(i);
                LinkedHashMap link = null;
                //for (int j = 0; j < a.length; j++) {
                link = new LinkedHashMap();
                link = new LinkedHashMap();
                link.put("Flota", a[0]);
                link.put("N°Bus", a[1]);                
                link.put("Unidad", a[2]);
                link.put("DT", a[3]);
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

}
