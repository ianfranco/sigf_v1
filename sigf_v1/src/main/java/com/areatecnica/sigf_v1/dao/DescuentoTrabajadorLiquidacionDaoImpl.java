/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.DescuentoTrabajadorLiquidacion;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class DescuentoTrabajadorLiquidacionDaoImpl implements GenericDao<DescuentoTrabajadorLiquidacion>{

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
    
}
