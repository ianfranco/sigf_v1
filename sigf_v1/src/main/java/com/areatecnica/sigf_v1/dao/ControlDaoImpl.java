/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Control;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class ControlDaoImpl implements ControlDao{

    @Override
    public List<Control> findAll() {
        List<Control> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Control c";
        try {

            list = session.createQuery(sql).list();

            for (Control c : list) {                
                Hibernate.initialize(c.getTipoControl());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Control findById(int id) {
        Control control = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Control c WHERE idControl=" + id;
        try {

            control = (Control) session.createQuery(sql).uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            System.err.println("BUS NO ENCONTRADO!!!");
            tx.rollback();
            e.printStackTrace();
        }
        return control;
    }
    
}
