/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.util;


import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Ian Franco
 */
@Named(value = "appBean")
@ApplicationScoped
public class appBean {

    /**
     * Creates a new instance of appBean
     */
    public appBean() {
    }

    public String getBaseUrl() {
        return MyUtil.baseURL();
    }
    
    public String getBasePath() {
        return MyUtil.basePath();
    }
    
    public String name() {
        return MyUtil.getUserName();
    }
    
    public String getBasePathWEB() {
        return MyUtil.basePathWEBINF();
    }
}
