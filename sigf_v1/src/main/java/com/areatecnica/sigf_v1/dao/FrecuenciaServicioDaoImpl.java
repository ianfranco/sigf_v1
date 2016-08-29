/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.FrecuenciaServicio;
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
public class FrecuenciaServicioDaoImpl implements FrecuenciaServicioDao{

    @Override
    public List<FrecuenciaServicio> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FrecuenciaServicio> findByServicio(Servicio servicio) {
        List<FrecuenciaServicio> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM FrecuenciaServicio WHERE servicio="+servicio.getIdServicio();
        try {

            list = session.createQuery(sql).list();
            
            for(FrecuenciaServicio f: list){
                Hibernate.initialize(f.getServicio());
                Hibernate.initialize(f.getPeriodoFrecuencia());
                Hibernate.initialize(f.getTipoDiaFrecuencia());
            }
            
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
}
