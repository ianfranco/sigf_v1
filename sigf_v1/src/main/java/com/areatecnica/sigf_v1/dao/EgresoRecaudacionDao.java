/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.EgresoRecaudacion;
import com.areatecnica.sigf_v1.entities.ProcesoRecaudacion;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface EgresoRecaudacionDao {
    
    public List<EgresoRecaudacion> findAll();
    public List<EgresoRecaudacion> findByProceso(ProcesoRecaudacion procesoRecaudacion);
}
