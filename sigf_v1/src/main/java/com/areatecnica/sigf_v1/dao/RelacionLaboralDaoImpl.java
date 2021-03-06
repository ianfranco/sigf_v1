/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Empresa;
import com.areatecnica.sigf_v1.entities.RelacionLaboral;
import com.areatecnica.sigf_v1.entities.Trabajador;
import com.areatecnica.sigf_v1.util.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static javax.print.attribute.Size2DSyntax.MM;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ianfr
 */
public class RelacionLaboralDaoImpl implements GenericDao<RelacionLaboral> {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public RelacionLaboral findById(int id) {
        RelacionLaboral relacionLaboral = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE idRelacionLaboral=" + id;
        try {
            relacionLaboral = (RelacionLaboral) session.createQuery(sql).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return relacionLaboral;
    }

    @Override
    public List<RelacionLaboral> findAll() {
        List<RelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE estado = 1 ORDER BY empresa.nombreEmpresa ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findActivasByTrabajador(Date fechaMes, Trabajador trabajador) {
        List<RelacionLaboral> list = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "SELECT * FROM relacion_laboral LEFT JOIN empresa ON relacion_laboral.id_empresa = empresa.id_empresa LEFT JOIN trabajador ON relacion_laboral.id_trabajador = trabajador.id_trabajador "
                + "WHERE fecha_inicio <= LAST_DAY('" + format.format(fechaMes) + "') AND fecha_fin BETWEEN IF(fecha_inicio = fecha_fin, fecha_fin, '" + format.format(fechaMes) + "')"
                + " AND IF(fecha_fin>LAST_DAY('" + format.format(fechaMes) + "'), fecha_fin, LAST_DAY('" + format.format(fechaMes) + "')) AND relacion_laboral.id_trabajador = " + trabajador.getIdTrabajador() + " ORDER BY empresa.nombre_empresa ASC, trabajador.apellido_paterno_trabajador ASC";

        try {

            list = (List<RelacionLaboral>) session.createSQLQuery(sql).addEntity(RelacionLaboral.class).list();

            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findActivas(Date fechaMes, int idOperador) {
        List<RelacionLaboral> list = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "SELECT * FROM relacion_laboral LEFT JOIN empresa ON relacion_laboral.id_empresa = empresa.id_empresa LEFT JOIN trabajador ON relacion_laboral.id_trabajador = trabajador.id_trabajador "
                + "WHERE fecha_inicio <= LAST_DAY('" + format.format(fechaMes) + "') AND fecha_fin BETWEEN IF(fecha_inicio = fecha_fin, fecha_fin, '" + format.format(fechaMes) + "')"
                + " AND IF(fecha_fin>LAST_DAY('" + format.format(fechaMes) + "'), fecha_fin, LAST_DAY('" + format.format(fechaMes) + "')) AND id_operador = " + idOperador + " ORDER BY empresa.nombre_empresa ASC, trabajador.apellido_paterno_trabajador ASC";

        try {

            list = (List<RelacionLaboral>) session.createSQLQuery(sql).addEntity(RelacionLaboral.class).list();

            /*for (RelacionLaboral r : list) {
                Hibernate.initialize(r.getTrabajador());
            }*/
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findEmpresasActivas(Date fechaMes, int idOperador) {
        List<RelacionLaboral> list = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "SELECT * FROM relacion_laboral LEFT JOIN empresa ON relacion_laboral.id_empresa = empresa.id_empresa LEFT JOIN trabajador ON relacion_laboral.id_trabajador = trabajador.id_trabajador "
                + "WHERE fecha_inicio <= LAST_DAY('" + format.format(fechaMes) + "') AND fecha_fin BETWEEN IF(fecha_inicio = fecha_fin, fecha_fin, '" + format.format(fechaMes) + "')"
                + " AND IF(fecha_fin>LAST_DAY('" + format.format(fechaMes) + "'), fecha_fin, LAST_DAY('" + format.format(fechaMes) + "')) AND id_operador = " + idOperador + " GROUP BY empresa.id_empresa ORDER BY empresa.nombre_empresa ASC";

        try {

            list = (List<RelacionLaboral>) session.createSQLQuery(sql).addEntity(RelacionLaboral.class).list();

            /*for (RelacionLaboral r : list) {
                Hibernate.initialize(r.getTrabajador());
            }*/
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findAllVinaBus() {
        List<RelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE estado = 1 AND idOperador = 0 ORDER BY empresa.nombreEmpresa ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findAllSolyMar() {
        List<RelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE estado = 1 AND idOperador = 1 ORDER BY empresa.nombreEmpresa ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findWithLimit() {
        List<RelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral ORDER BY idRelacionLaboral DESC";
        try {

            list = session.createQuery(sql).setMaxResults(200).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findByTrabajador(Trabajador trabajador) {
        List<RelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE estado = true AND trabajador=" + trabajador.getIdTrabajador() + " ORDER BY empresa.nombreEmpresa ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findHistoricoByTrabajador(Trabajador trabajador) {
        List<RelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE trabajador=" + trabajador.getIdTrabajador() + " ORDER BY estado DESC, fecha_inicio DESC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findByTrabajador2(Trabajador trabajador) {
        List<RelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE estado = true AND trabajador=" + trabajador.getIdTrabajador() + " ORDER BY empresa.nombreEmpresa ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findByEmpresa(Empresa empresa) {
        List<RelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE estado = 1 AND empresa=" + empresa.getIdEmpresa() + " ORDER BY empresa.nombreEmpresa ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findAllByEmpresa(Empresa empresa) {
        List<RelacionLaboral> list = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE empresa=" + empresa.getIdEmpresa() + " ORDER BY trabajador.apellidoPaternoTrabajador ASC";
        try {

            list = session.createQuery(sql).list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    public List<RelacionLaboral> findByDate(Date date) {
        List<RelacionLaboral> list = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String sql = "FROM RelacionLaboral WHERE fechaInicio BETWEEN '" + format.format(date) + "' AND LAST_DAY('" + format.format(date) + "')  ORDER BY fechaInicio ASC";
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
