/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;

import com.areatecnica.sigf_v1.entities.Boleto;
import com.areatecnica.sigf_v1.entities.InventarioInterno;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface InventarioInternoDao {

    public List<InventarioInterno> findAll();

    public InventarioInterno findById();
    
    public List<InventarioInterno> findByEstado(boolean estado);
    
    public List<InventarioInterno> findByBoleto(Boleto boleto);
    
    public InventarioInterno findByIdentificador(String identificador, String serie);
    
}
