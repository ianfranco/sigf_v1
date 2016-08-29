/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.ControlHorarioServicio;
import com.areatecnica.sigf_v1.entities.HorarioServicio;
import com.areatecnica.sigf_v1.entities.Servicio;
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
public class ControlHorarioServicioDaoImpl implements ControlHorarioServicioDao{

    @Override
    public List<ControlHorarioServicio> findByServicio(Servicio servicio) {
        List<ControlHorarioServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ControlHorarioServicio c WHERE c.horarioServicio.servicio = " + servicio.getIdServicio();
        try {

            list = session.createQuery(sql).list();
            System.err.println("TAMAÑO:"+list.size());
            for (ControlHorarioServicio c : list) {
                System.err.println("ELEMENTO n°1:"+c.toString());
                Hibernate.initialize(c.getControlServicio());
                Hibernate.initialize(c.getHorarioServicio());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ControlHorarioServicio> findAll() {
        List<ControlHorarioServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ControlHorarioServicio c";
        try {

            list = session.createQuery(sql).list();

            for (ControlHorarioServicio c : list) {
                Hibernate.initialize(c.getControlServicio());
                Hibernate.initialize(c.getHorarioServicio());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ControlHorarioServicio> findByHorarioServicio(HorarioServicio horarioServicio) {
        List<ControlHorarioServicio> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ControlHorarioServicio c WHERE c.horarioServicio= " + horarioServicio.getIdHorarioServicio();
        try {

            list = session.createQuery(sql).list();
            System.err.println("TAMAÑO:"+list.size());
            for (ControlHorarioServicio c : list) {
                System.err.println("ELEMENTO n°1:"+c.toString());
                Hibernate.initialize(c.getControlServicio().getControl());
                Hibernate.initialize(c.getHorarioServicio());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
