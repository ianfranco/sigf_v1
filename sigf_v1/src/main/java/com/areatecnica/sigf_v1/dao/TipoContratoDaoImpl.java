/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.AsignacionFamiliar;
import com.areatecnica.sigf_v1.entities.TipoContrato;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class TipoContratoDaoImpl implements GenericDao<TipoContrato>{

    @Override
    public TipoContrato findById(int id) {
        TipoContrato tipoContrato = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM TipoContrato WHERE idTipoContrato=" + id;
        try {
            tipoContrato = (TipoContrato) session.createQuery(sql).uniqueResult();
            tx.commit();
            System.err.println("tipo de contrato obtenido:"+tipoContrato);
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return tipoContrato;
    }

    @Override
    public List<TipoContrato> findAll() {
        List<TipoContrato> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM TipoContrato";
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
