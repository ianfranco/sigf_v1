/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.GastoAdministracionMensual;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class GastoAdministracionMensualDaoImpl implements GenericDao<GastoAdministracionMensual> {

    @Override
    public GastoAdministracionMensual findById(int id) {
        GastoAdministracionMensual gastoAdministracion = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM GastoAdministracionMensual WHERE idGastoAdministracionMensual=" + id;
        try {
            gastoAdministracion = (GastoAdministracionMensual) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return gastoAdministracion;
    }

    @Override
    public List<GastoAdministracionMensual> findAll() {
        List<GastoAdministracionMensual> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM GastoAdministracionMensual ORDER BY idGastoAdministracionMensual DESC";
        try {

            list = session.createQuery(sql).list();
            for (GastoAdministracionMensual g : list) {
                Hibernate.initialize(g.getDepartamento());
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<GastoAdministracionMensual> findByMonthAndYear(int month, int year) {
        List<GastoAdministracionMensual> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM GastoAdministracionMensual WHERE mes = " + month + " AND anio = " + year + " ORDER BY fechaGastoAdministracion ASC";
        try {

            list = session.createQuery(sql).list();
            for (GastoAdministracionMensual g : list) {
                Hibernate.initialize(g.getDepartamento());
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public int findTotalByMonthAndYear(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        BigInteger dt = BigInteger.ZERO;
        String query = "SELECT \n"
                + " CAST((IFNULL(SUM(gasto_administracion_mensual.valor ), 0)) AS SIGNED) \n"
                + " FROM gasto_administracion_mensual \n"
                + " WHERE gasto_administracion_mensual.fecha_gasto_administracion BETWEEN '"+format.format(fecha)+"' AND LAST_DAY('"+format.format(fecha)+"')";

        try {

            dt = (BigInteger) session.createSQLQuery(query).uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return dt.intValue();
    }

}
