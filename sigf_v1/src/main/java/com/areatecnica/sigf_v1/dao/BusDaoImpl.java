/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.Flota;
import com.areatecnica.sigf_v1.entities.Servicio;
import com.areatecnica.sigf_v1.entities.Terminal;
import com.areatecnica.sigf_v1.entities.UnidadNegocio;
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
public class BusDaoImpl implements BusDao {

    @Override
    public List<Bus> findAll() {
        List<Bus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus b";
        try {

            list = session.createQuery(sql).list();

            for (Bus b : list) {
                Hibernate.initialize(b.getEmpresa());
                Hibernate.initialize(b.getTerminal());
                Hibernate.initialize(b.getUnidadNegocio());
                Hibernate.initialize(b.getFlota());
                Hibernate.initialize(b.getModeloMarcaBus());
                //Hibernate.initialize(b.getModeloMarcaBus().getMarcaBus());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<Bus> findAllClean() {
        List<Bus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus b";
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
    public List<Bus> findByTerminal(Terminal terminal) {
        List<Bus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus WHERE terminal=" + terminal.getIdTerminal();
        try {

            list = session.createQuery(sql).list();

            for (Bus b : list) {
                Hibernate.initialize(b.getEmpresa());
                Hibernate.initialize(b.getTerminal());
                Hibernate.initialize(b.getUnidadNegocio());
                Hibernate.initialize(b.getFlota());
                Hibernate.initialize(b.getModeloMarcaBus());
                Hibernate.initialize(b.getModeloMarcaBus().getMarcaBus());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Bus> findByServicio(Servicio servicio) {
        List<Bus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus WHERE terminal=" + servicio.getTerminal().getIdTerminal();
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
    public List<Bus> findByUnidad(int idUnidad) {
        List<Bus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus WHERE unidadNegocio=" +idUnidad;
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
    public List<Bus> findByEmpresa(Empresa empresa) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Bus> findByFlota(Flota flota) {
        List<Bus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus WHERE flota=" +flota.getIdFlota();
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<Bus> findByFlotaVinabus(Flota flota) {
        List<Bus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus WHERE flota=" +flota.getIdFlota()+" AND unidadNegocio<>3";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<Bus> findByFlotaAndUnidad(Flota flota, UnidadNegocio unidadNegocio) {
        List<Bus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus WHERE flota=" +flota.getIdFlota()+" AND unidadNegocio="+unidadNegocio.getIdUnidadNegocio();
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
    public Bus findByNumero(int numeroBus) {
        Bus bus = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus WHERE idBus=" + numeroBus;
        try {

            bus = (Bus) session.createQuery(sql).uniqueResult();

            System.err.println("BUS ENCONTRADO:" + bus.getPatente() + " NUMERO:" + bus.getNumeroMotor());
            Hibernate.initialize(bus.getEmpresa());
            Hibernate.initialize(bus.getTerminal());
            Hibernate.initialize(bus.getUnidadNegocio());
            Hibernate.initialize(bus.getFlota());
            Hibernate.initialize(bus.getModeloMarcaBus());
            Hibernate.initialize(bus.getModeloMarcaBus().getMarcaBus());

            tx.commit();
        } catch (HibernateException e) {
            System.err.println("BUS NO ENCONTRADO!!!");
            tx.rollback();
            e.printStackTrace();
        }
        return bus;
    }

    @Override
    public Bus findById(int id) {
        Bus bus = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus WHERE idBus=" + id;
        try {

            bus = (Bus) session.createQuery(sql).uniqueResult();

            
            Hibernate.initialize(bus.getEmpresa());
            Hibernate.initialize(bus.getTerminal());
            Hibernate.initialize(bus.getUnidadNegocio());
            Hibernate.initialize(bus.getFlota());
            Hibernate.initialize(bus.getModeloMarcaBus());
            Hibernate.initialize(bus.getModeloMarcaBus().getMarcaBus());

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return bus;
    }
    
    @Override
    public List<Bus> findByIdTerminal(int idTerminal) {
        List<Bus> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM Bus WHERE terminal=" + idTerminal;
        try {

            list = session.createQuery(sql).list();

            for (Bus b : list) {
                Hibernate.initialize(b.getEmpresa());
                Hibernate.initialize(b.getTerminal());
                Hibernate.initialize(b.getUnidadNegocio());
                Hibernate.initialize(b.getFlota());
                Hibernate.initialize(b.getModeloMarcaBus());
                Hibernate.initialize(b.getModeloMarcaBus().getMarcaBus());
            }

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

}
