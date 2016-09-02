/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
import com.areatecnica.sigf_v1.entities.Banco;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class AsignacionFamiliarDaoImpl implements GenericDao<AsignacionFamiliar>{

    @Override
    public AsignacionFamiliar findById(int id) {
        AsignacionFamiliar asignacionFamiliar = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM AsignacionFamiliar WHERE idAsignacionFamiliar=" + id;
        try {
            asignacionFamiliar = (AsignacionFamiliar) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return asignacionFamiliar;
    }

    @Override
    public List<AsignacionFamiliar> findAll() {
        List<AsignacionFamiliar> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM AsignacionFamiliar";
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
