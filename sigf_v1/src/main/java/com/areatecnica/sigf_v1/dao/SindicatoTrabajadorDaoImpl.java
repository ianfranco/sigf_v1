/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.SindicatoTrabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class SindicatoTrabajadorDaoImpl implements GenericDao<SindicatoTrabajador>{

    @Override
    public SindicatoTrabajador findById(int id) {
        SindicatoTrabajador sindicatoTrabajador = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM SindicatoTrabajador WHERE idSindicatoTrabajador=" + id;
        try {
            sindicatoTrabajador = (SindicatoTrabajador) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return sindicatoTrabajador;
    }

    @Override
    public List<SindicatoTrabajador> findAll() {
        List<SindicatoTrabajador> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM SindicatoTrabajador ORDER BY sindicato.nombreSindicato, trabajador.apellidoPaternoTrabajador ";
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
