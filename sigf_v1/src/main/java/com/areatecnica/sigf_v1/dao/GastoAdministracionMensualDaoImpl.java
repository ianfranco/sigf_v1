/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.GastoAdministracionMensual;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class GastoAdministracionMensualDaoImpl implements GenericDao<GastoAdministracionMensual>{

    @Override
    public GastoAdministracionMensual findById(int id) {
        GastoAdministracionMensual gastoAdministracion = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM GastoAdministracionMensual WHERE idGastoAdministracionMensual=" + id;
        try {
            gastoAdministracion = (GastoAdministracionMensual) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return gastoAdministracion;
    }

    @Override
    public List<GastoAdministracionMensual> findAll() {
        List<GastoAdministracionMensual> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM GastoAdministracionMensual";
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
