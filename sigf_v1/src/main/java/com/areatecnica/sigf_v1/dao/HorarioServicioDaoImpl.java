/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

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
public class HorarioServicioDaoImpl implements HorarioServicioDao{

    @Override
    public List<HorarioServicio> findAll() {
        List<HorarioServicio> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM HorarioServicio ";
        try {

            list = session.createQuery(sql).list();
            
            for(HorarioServicio h: list){
                Hibernate.initialize(h.getServicio());
            }
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
        
    @Override
    public List<HorarioServicio> findByServicio(Servicio servicio) {
        List<HorarioServicio> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM HorarioServicio WHERE servicio="+servicio.getIdServicio();
        try {

            list = session.createQuery(sql).list();
            
            for(HorarioServicio h: list){
                Hibernate.initialize(h.getServicio());
            }
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
}
