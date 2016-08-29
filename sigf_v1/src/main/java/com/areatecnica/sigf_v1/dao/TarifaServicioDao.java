/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;



import com.areatecnica.sigf_v1.entities.Servicio;
import com.areatecnica.sigf_v1.entities.TarifaServicio;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface TarifaServicioDao {
    
    public List<TarifaServicio> findAll();
    public List<TarifaServicio> findByServicio(Servicio servicio);
    public List<TarifaServicio> findByServicioMonthYear(Servicio servicio, int month, int year);
    public List<TarifaServicio> findByServicioYear(Servicio servicio, int year);
    
}
