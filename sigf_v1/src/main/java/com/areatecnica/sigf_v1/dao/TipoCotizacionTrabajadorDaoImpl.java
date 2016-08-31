/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.TipoCotizacionTrabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class TipoCotizacionTrabajadorDaoImpl implements GenericDao<TipoCotizacionTrabajador>{

    @Override
    public TipoCotizacionTrabajador findById(int id) {
        TipoCotizacionTrabajador tipoCotizacion = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM TipoCotizacionTrabajador WHERE idTipoCotizacionTrabajador=" + id;
        try {
            tipoCotizacion = (TipoCotizacionTrabajador) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return tipoCotizacion;
    }

    @Override
    public List<TipoCotizacionTrabajador> findAll() {
        List<TipoCotizacionTrabajador> list = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM TipoCotizacionTrabajador";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
