/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.entities.InventarioInterno;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class InventarioInternoDaoImpl implements InventarioInternoDao{

    @Override
    public List<InventarioInterno> findAll() {
        
        List<InventarioInterno> list = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InventarioInterno";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } 
        return list;
    }

    @Override
    public InventarioInterno findById() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InventarioInterno> findByEstado(boolean estado) {
        List<InventarioInterno> list = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InventarioInterno i WHERE estado = "+estado;
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<InventarioInterno> findByBoleto(Boleto boleto) {
        List<InventarioInterno> list = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InventarioInterno i WHERE estado = "+false+ " AND boleto ="+boleto.getIdBoleto();
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public InventarioInterno findByIdentificador(String identificador, String serie) {
        InventarioInterno inventario = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "SELECT * FROM Inventario_Interno  WHERE identificador = '"+identificador +"' AND LEFT(serie, 4)='"+serie+"'";
        try {

            inventario = (InventarioInterno) session.createSQLQuery(sql).addEntity(InventarioInterno.class).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return inventario;
    }
    
    
    
}
