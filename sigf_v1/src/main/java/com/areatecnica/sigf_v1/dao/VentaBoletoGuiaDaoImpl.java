/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Guia;
import com.areatecnica.sigf_v1.entities.VentaBoletoGuia;
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
public class VentaBoletoGuiaDaoImpl implements VentaBoletoGuiaDao {

    @Override
    public List<VentaBoletoGuia> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<VentaBoletoGuia> findByGuia(Guia guia) {
        List<VentaBoletoGuia> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM VentaBoletoGuia WHERE guia=" + guia.getIdGuia();
        try {

            list = session.createQuery(sql).list();
            for (VentaBoletoGuia v : list) {
                Hibernate.initialize(v.getGuia().getBus());
                Hibernate.initialize(v.getGuia().getTrabajador());
                Hibernate.initialize(v.getInventarioTerminal().getBoleto());
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

}
