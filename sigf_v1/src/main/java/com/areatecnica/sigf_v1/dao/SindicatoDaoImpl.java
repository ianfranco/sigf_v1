/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Sindicato;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class SindicatoDaoImpl implements GenericDao<Sindicato>{

    @Override
    public Sindicato findById(int id) {
        Sindicato sindicato = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        Transaction tx = session.beginTransaction();
        String sql = "FROM Sindicato WHERE idSindicato=" + id;
        try {
            sindicato = (Sindicato) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return sindicato;
    }

    @Override
    public List<Sindicato> findAll() {
        List<Sindicato> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Sindicato";
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
