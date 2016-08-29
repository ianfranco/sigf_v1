/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.dao;


import com.areatecnica.sigf_v1.entities.CompraBoleto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ian Franco
 */
public interface CompraBoletoDao {
    
    public CompraBoleto findByFolio(int folio);
    
    public List<CompraBoleto> findAll();
    
    public List<CompraBoleto> findByFechas(Date desde, Date hasta);
    
}
