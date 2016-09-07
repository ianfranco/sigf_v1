/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import com.areatecnica.sigf_v1.entities.ServicioProcesoRecaudacion;
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
public class ServicioProcesoRecaudacionDaoImpl implements GenericDao<ServicioProcesoRecaudacion> {

    @Override
    public ServicioProcesoRecaudacion findById(int id) {
        ServicioProcesoRecaudacion asignacionFamiliar = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ServicioProcesoRecaudacion WHERE idServicioProcesoRecaudacion=" + id;
        try {
            asignacionFamiliar = (ServicioProcesoRecaudacion) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return asignacionFamiliar;
    }

    @Override
    public List<ServicioProcesoRecaudacion> findAll() {
        List<ServicioProcesoRecaudacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ServicioProcesoRecaudacion";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<ServicioProcesoRecaudacion> findByProceso(ProcesoRecaudacion procesoRecaudacion) {
        List<ServicioProcesoRecaudacion> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM ServicioProcesoRecaudacion WHERE procesoRecaudacion = " + procesoRecaudacion.getIdProcesoRecaudacion();
        try {

            list = session.createQuery(sql).list();
            
            for(ServicioProcesoRecaudacion s:list){
                Hibernate.initialize(s.getServicio());                
            }
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

}
