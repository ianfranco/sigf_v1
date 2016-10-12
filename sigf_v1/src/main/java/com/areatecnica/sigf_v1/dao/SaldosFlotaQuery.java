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
public class SaldosFlotaQuery {

    private ArrayList<LinkedHashMap> array;
    private String query;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    private Date fecha;

    public SaldosFlotaQuery(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<LinkedHashMap> loadQuery() {
        this.array = new ArrayList<>();

        List list = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        this.query = "SELECT \n"
                + "flota.nombre_flota, \n"
                + "bus.numero_bus, \n"
                + "unidad_negocio.numero_unidad_negocio,    \n"
                + "bus.patente, \n"
                + "administracion=sum_egreso(bus.id_bus,'2016-09-01',1 ) AS Administracion, \n"
                + "licitacion=sum_egreso(bus.id_bus,'2016-09-01',7 ) AS Licitacion, \n"
                + "cargos(bus.id_bus) AS cargos, \n"
                + "Administracion+Licitacion As totalIngresos,\n"
                + "(Administracion+Licitacion)-cargos AS saldo\n"
                + "FROM cargo_bus \n"
                + "LEFT JOIN bus ON cargo_bus.id_bus = bus.id_bus \n"
                + "LEFT JOIN flota on bus.id_flota = flota.id_flota\n"
                + "LEFT JOIN unidad_negocio ON bus.id_unidad_negocio = unidad_negocio.id_unidad_negocio \n"
                + "WHERE cargo_bus.fecha_inicio_cargo_bus BETWEEN '2016-09-01' AND LAST_DAY('2016-09-01') \n"
                + "GROUP BY bus.id_bus \n"
                + "ORDER BY flota.nombre_flota, unidad_negocio.numero_unidad_negocio, bus.numero_bus  ASC";
        try {

            list = session.createSQLQuery(query).list();

            for (int i = 0; i < list.size(); i++) {

                Object[] a = (Object[]) list.get(i);
                LinkedHashMap link = null;
                //for (int j = 0; j < a.length; j++) {
                link = new LinkedHashMap();
                link.put("Flota", a[0]);
                link.put("N°Bus", a[1]);
                link.put("Unidad", a[2]);
                link.put("Patente", a[3]);
                link.put("Administracion", a[4]);
                link.put("Licitacion", a[5]);
                link.put("Cargos", a[6]);
                link.put("TotalIngresos", a[7]);                
                link.put("Saldo", a[8]);
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

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        this.query = "SELECT \n"
                + "flota.nombre_flota, \n"
                + "bus.numero_bus, \n"
                + "unidad_negocio.numero_unidad_negocio,    \n"
                + "bus.patente, \n"
                + "@administracion:=sum_egreso(bus.id_bus,'2016-09-01',1 ) AS Administracion, \n"
                + "@licitacion:=sum_egreso(bus.id_bus,'2016-09-01',7 ) AS Licitacion, \n"
                + "@cargos:=cargos(bus.id_bus) AS cargos, \n"
                + "@totalIngresos:=@administracion+@licitacion As totalIngresos,\n"
                + "@saldo:=(@administracion+@licitacion)-@cargos AS saldo\n"
                + "FROM cargo_bus \n"
                + "LEFT JOIN bus ON cargo_bus.id_bus = bus.id_bus \n"
                + "LEFT JOIN flota on bus.id_flota = flota.id_flota\n"
                + "LEFT JOIN unidad_negocio ON bus.id_unidad_negocio = unidad_negocio.id_unidad_negocio \n"
                + "WHERE cargo_bus.fecha_inicio_cargo_bus BETWEEN '2016-09-01' AND LAST_DAY('2016-09-01') AND flota.id_flota="+flota.getIdFlota()+"\n"
                + "GROUP BY bus.id_bus \n"
                + "ORDER BY flota.nombre_flota, unidad_negocio.numero_unidad_negocio, bus.numero_bus  ASC";
        try {

            list = session.createSQLQuery(query).list();

            for (int i = 0; i < list.size(); i++) {

                Object[] a = (Object[]) list.get(i);
                LinkedHashMap link = null;
                //for (int j = 0; j < a.length; j++) {
                link = new LinkedHashMap();
                link.put("Flota", a[0]);
                link.put("N°Bus", a[1]);
                link.put("Unidad", a[2]);
                link.put("Patente", a[3]);
                link.put("Administracion", a[4]);
                link.put("Licitacion", a[5]);
                link.put("Cargos", a[6]);
                link.put("TotalIngresos", a[7]);                
                link.put("Saldo", a[8]);
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
