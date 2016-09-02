/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.InstitucionApv;
import com.areatecnica.sigf_v1.entities.InstitucionPrevision;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class InstitucionAPVDaoImpl implements GenericDao<InstitucionApv>{

    @Override
    public InstitucionApv findById(int id) {
        InstitucionApv institucionApv = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InstitucionApv WHERE idInstitucionApv=" + id;
        try {
            institucionApv = (InstitucionApv) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return institucionApv;
    }

    @Override
    public List<InstitucionApv> findAll() {
        List<InstitucionApv> list = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InstitucionApv";
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
