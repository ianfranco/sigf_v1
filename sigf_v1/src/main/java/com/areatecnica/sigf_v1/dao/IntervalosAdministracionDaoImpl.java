/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.IntervalosAdministracion;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class IntervalosAdministracionDaoImpl implements GenericDao<IntervalosAdministracion>{

    @Override
    public IntervalosAdministracion findById(int id) {
        IntervalosAdministracion intervalo = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM IntervalosAdministracion WHERE idIntervalosAdministracion=" + id;
        try {
            intervalo = (IntervalosAdministracion) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return intervalo;
    }

    @Override
    public List<IntervalosAdministracion> findAll() {
        List<IntervalosAdministracion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM IntervalosAdministracion";
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
