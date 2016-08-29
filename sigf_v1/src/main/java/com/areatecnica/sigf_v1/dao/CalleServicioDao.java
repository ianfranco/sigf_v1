/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.CalleServicio;
import com.areatecnica.sigf_v1.entities.Servicio;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface CalleServicioDao {
    
    public List<CalleServicio> findAll();
    public List<CalleServicio> findByServicio(Servicio servicio);
    
}
