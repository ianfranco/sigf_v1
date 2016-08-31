/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.CajaCompensacion;
import com.areatecnica.sigf_v1.entities.MonedaPactadaInstitucionSalud;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class MonedaPactadaInstitucionSaludImpl implements GenericDao<MonedaPactadaInstitucionSalud> {

    @Override
    public MonedaPactadaInstitucionSalud findById(int id) {
        MonedaPactadaInstitucionSalud monedaPactada = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM MonedaPactadaInstitucionSalud WHERE idMonedaPactadaInstitucionSalud=" + id;
        try {
            monedaPactada = (MonedaPactadaInstitucionSalud) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return monedaPactada;
    }

    @Override
    public List<MonedaPactadaInstitucionSalud> findAll() {
        List<MonedaPactadaInstitucionSalud> list = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM MonedaPactadaInstitucionSalud";
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
