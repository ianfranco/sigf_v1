/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.InstitucionSalud;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class InstitucionSaludDaoImpl implements GenericDao<InstitucionSalud>{

    @Override
    public InstitucionSalud findById(int id) {
        InstitucionSalud institucionSalud = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InstitucionSalud WHERE idInstitucionSalud=" + id;
        try {
            institucionSalud = (InstitucionSalud) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return institucionSalud;
    }

    @Override
    public List<InstitucionSalud> findAll() {
        List<InstitucionSalud> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InstitucionSalud WHERE nombreInstitucionSalud NOT LIKE '%fonasa'";
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
