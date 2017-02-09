/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.DescuentoTrabajador;
import com.areatecnica.sigf_v1.entities.DescuentoTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class DescuentoTrabajadorLiquidacionDaoImpl implements GenericDao<DescuentoTrabajadorLiquidacion> {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public DescuentoTrabajadorLiquidacion findById(int id) {
        DescuentoTrabajadorLiquidacion descuentoTrabajador = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM DescuentoTrabajadorLiquidacion WHERE idDescuentoTrabajadorLiquidacion=" + id;
        try {
            descuentoTrabajador = (DescuentoTrabajadorLiquidacion) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return descuentoTrabajador;
    }

    public DescuentoTrabajadorLiquidacion findByTrabajadorAndDescuentoAndDate(Trabajador trabajador, DescuentoTrabajador descuento, Date fecha) {
        DescuentoTrabajadorLiquidacion descuentoTrabajador = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM DescuentoTrabajadorLiquidacion WHERE idDescuentoTrabajadorLiquidacion=" + trabajador.getIdTrabajador() + " AND descuentoTrabajador=" + descuento.getIdDescuentoTrabajador() + " fechaInicioDescuento BETWEEN '" + fecha + "' AND LAST_DAY('" + fecha + "') ";
        try {
            descuentoTrabajador = (DescuentoTrabajadorLiquidacion) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return descuentoTrabajador;
    }

    @Override
    public List<DescuentoTrabajadorLiquidacion> findAll() {
        List<DescuentoTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM DescuentoTrabajadorLiquidacion";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<DescuentoTrabajadorLiquidacion> findBy(Trabajador trabajador) {
        List<DescuentoTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM DescuentoTrabajadorLiquidacion WHERE trabajador = " + trabajador.getIdTrabajador();
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<DescuentoTrabajadorLiquidacion> findByTrabajadorAndDate(Trabajador trabajador, Date fecha) {
        List<DescuentoTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM DescuentoTrabajadorLiquidacion WHERE trabajador = " + trabajador.getIdTrabajador() + " AND fechaInicioDescuento BETWEEN '" + format.format(fecha) + "' AND LAST_DAY('" + format.format(fecha) + "')";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<DescuentoTrabajadorLiquidacion> findWithLimit() {
        List<DescuentoTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM DescuentoTrabajadorLiquidacion ORDER BY idDescuentoTrabajadorLiquidacion DESC";
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<DescuentoTrabajadorLiquidacion> findSaldoAnteriorWithLimit() {
        List<DescuentoTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM DescuentoTrabajadorLiquidacion WHERE descuentoTrabajador.idDescuentoTrabajador = 4 ORDER BY idDescuentoTrabajadorLiquidacion DESC";
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<DescuentoTrabajadorLiquidacion> findRetencionWithLimit() {
        List<DescuentoTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM DescuentoTrabajadorLiquidacion WHERE descuentoTrabajador.idDescuentoTrabajador = 1 ORDER BY idDescuentoTrabajadorLiquidacion DESC";
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public int findCincoPorciento(Trabajador trabajador, Date fecha) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        BigInteger totalCinco = BigInteger.ZERO;
        String query = "SELECT \n"
                + "			CAST((IFNULL(SUM(egreso_guia.monto), 0)) AS SIGNED) \n"
                + "		FROM egreso_guia \n"
                + "			LEFT JOIN guia ON egreso_guia.id_guia = guia.id_guia \n"
                + "			LEFT JOIN egreso_recaudacion ON egreso_guia.id_egreso_servicio = egreso_recaudacion.id_egreso_recaudacion \n"
                + "            WHERE guia.id_trabajador = " + trabajador.getIdTrabajador() + " AND  guia.fecha_recaudacion BETWEEN '" + format.format(fecha) + "' AND LAST_DAY('" + format.format(fecha) + "') AND egreso_recaudacion.id_egreso = 12";
        try {
            totalCinco = (BigInteger) session.createSQLQuery(query).uniqueResult();
            tx.commit();

        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
            return 99;
        }

        return totalCinco.intValue();
    }

    public int findMontoBruto(Trabajador trabajador, Date fechaDesdeContrato, Date fechaHastaContrato, Date fechaDesdeFeriado, Date fechaHastaFeriado, Date fechaDesdeLicencia, Date fechaHastaLicencia) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        BigInteger montoBruto = BigInteger.ZERO;
        String query = "SELECT CAST(MONTO_BRUTO(" + trabajador.getIdTrabajador() + ", '" + format.format(fechaDesdeContrato) + "', '" + format.format(fechaHastaContrato) + "', '" + format.format(fechaDesdeFeriado) + "', '" + format.format(fechaHastaFeriado) + "', '" + format.format(fechaDesdeLicencia) + "', '" + format.format(fechaHastaLicencia) + "')) AS SIGNED) ";
        try {
            montoBruto = (BigInteger) session.createSQLQuery(query).uniqueResult();
            tx.commit();

        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
            return 99;
        }

        return montoBruto.intValue();
    }

}
