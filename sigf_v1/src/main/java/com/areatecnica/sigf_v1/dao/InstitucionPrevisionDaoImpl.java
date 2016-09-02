/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

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
public class InstitucionPrevisionDaoImpl implements GenericDao<InstitucionPrevision>{

    @Override
    public InstitucionPrevision findById(int id) {
        InstitucionPrevision institucionPrevision = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InstitucionPrevision WHERE idInstitucionPrevision=" + id;
        try {
            institucionPrevision = (InstitucionPrevision) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return institucionPrevision;
    }

    @Override
    public List<InstitucionPrevision> findAll() {
        List<InstitucionPrevision> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InstitucionPrevision WHERE idInstitucionPrevision < 99";
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
