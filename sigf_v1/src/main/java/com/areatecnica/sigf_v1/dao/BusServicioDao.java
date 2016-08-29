/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;



import com.areatecnica.sigf_v1.entities.Bus;
import com.areatecnica.sigf_v1.entities.BusServicio;
import java.util.List;

/**
 *
 * @author Ian
 */
public interface BusServicioDao {
    
    public List<BusServicio> findAll();
    
    public List<BusServicio> findByBus(Bus bus);
    
}
