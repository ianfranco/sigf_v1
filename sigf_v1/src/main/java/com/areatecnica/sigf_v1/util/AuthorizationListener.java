/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ian Franco
 */
public class AuthorizationListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent pe) {
        FacesContext facesContext = pe.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1) ? true : false;
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Object usuario = session.getAttribute("usuario");

        if (!isLoginPage && usuario == null) {
            NavigationHandler handler = facesContext.getApplication().getNavigationHandler();
            handler.handleNavigation(facesContext, null, "/login.xhtml");
        }
    }

    @Override
    public void beforePhase(PhaseEvent pe) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
