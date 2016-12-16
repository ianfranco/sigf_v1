/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.HaberTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class HaberTrabajadorLiquidacionDaoImpl implements GenericDao<HaberTrabajadorLiquidacion>{

    @Override
    public HaberTrabajadorLiquidacion findById(int id) {
        HaberTrabajadorLiquidacion descuentoTrabajador = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM HaberTrabajadorLiquidacion WHERE idHaberTrabajadorLiquidacion=" + id;
        try {
            descuentoTrabajador = (HaberTrabajadorLiquidacion) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return descuentoTrabajador;
    }

    @Override
    public List<HaberTrabajadorLiquidacion> findAll() {
        List<HaberTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM HaberTrabajadorLiquidacion";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<HaberTrabajadorLiquidacion> findWithLimit() {
        List<HaberTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM HaberTrabajadorLiquidacion ORDER BY idHaberTrabajadorLiquidacion DESC";
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<HaberTrabajadorLiquidacion> findAllById(int id) {
        List<HaberTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM HaberTrabajadorLiquidacion WHERE haberTrabajador = "+id+" ORDER BY trabajador.apellidoPaternoTrabajador ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    public List<HaberTrabajadorLiquidacion> findSaldoAnteriorWithLimit() {
        List<HaberTrabajadorLiquidacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM HaberTrabajadorLiquidacion WHERE haberTrabajador.idHaberTrabajador = 4 ORDER BY idHaberTrabajadorLiquidacion DESC";
        try {

            list = session.createQuery(sql).setMaxResults(100).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
