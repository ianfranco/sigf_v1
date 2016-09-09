/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.EgresoGuia;
import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class EgresoGuiaDaoImpl implements GenericDao<EgresoGuia>{

    @Override
    public EgresoGuia findById(int id) {
        EgresoGuia egresoGuia = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM EgresoGuia WHERE idEgresoGuia=" + id;
        try {
            egresoGuia = (EgresoGuia) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return egresoGuia;
    }
    
    public EgresoGuia findByGuiaAndEgreso(Guia guia, EgresoRecaudacion egresoRecaudacion) {
        EgresoGuia egresoGuia = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "SELECT * FROM egreso_guia WHERE id_egreso_servicio="+egresoRecaudacion.getIdEgresoRecaudacion()+" AND id_guia=" + guia.getIdGuia();
        try {
            egresoGuia = (EgresoGuia) session.createSQLQuery(sql).addEntity(EgresoGuia.class).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return egresoGuia;
    }

    @Override
    public List<EgresoGuia> findAll() {
        List<EgresoGuia> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM EgresoGuia";
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
