/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.util;

import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ian Franco
 */
public class MyUtil {

    public static String baseURL() {
        return "http://localhost:8080/sigf_v1-1/";
    }
    
    public static String basePathLogin(){
        return "/sigf_v1-1/faces/";
    }
    
    public static String basePath(){
        return "/faces/views/";
    }
    
    public static String basePathWEBINF(){
        return "/WEB-INF/";
    }
    
    
    public static String getUserName(){
        RequestContext context = RequestContext.getCurrentInstance();
        String nombre = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return nombre;
    }

}
