/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.entities.InventarioTerminal;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ian Franco
 */
public class InventarioTerminalDaoImpl implements InventarioTerminalDao{

    @Override
    public List<InventarioTerminal> findAll() {
        List<InventarioTerminal> list = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InventarioTerminal";
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
    public List<InventarioTerminal> findByEstado(boolean estado) {
        List<InventarioTerminal> list = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InventarioTerminal i WHERE estado = "+estado;
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
    public List<InventarioTerminal> findByBoleto(Boleto boleto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InventarioTerminal> findByBoletoANDTerminal(Boleto boleto, Terminal terminal) {
        List<InventarioTerminal> list = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InventarioTerminal i WHERE estado = "+false+ " AND boleto ="+boleto.getIdBoleto()+" AND terminal="+terminal.getIdTerminal();
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
    public List<InventarioTerminal> findByIdTerminal(int idTerminal) {
        List<InventarioTerminal> list = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InventarioTerminal i WHERE estado = "+false+ " AND terminal="+idTerminal;
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
    public List<InventarioTerminal> findByBoletoAndIdTerminal(Boleto boleto, int idTerminal) {
        List<InventarioTerminal> list = null;
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM InventarioTerminal i WHERE estado = "+false+ " AND boleto ="+boleto.getIdBoleto()+" AND terminal="+idTerminal;
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
    public InventarioTerminal findByIdentificador(String identificador, String serie) {
        InventarioTerminal inventario = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "SELECT * FROM Inventario_Terminal WHERE identificador = '"+identificador +"' AND LEFT(serie, 4)='"+serie+"'";
        try {

            inventario = (InventarioTerminal) session.createSQLQuery(sql).addEntity(InventarioTerminal.class).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return inventario;
    }
    
}
