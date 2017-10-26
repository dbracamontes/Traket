/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.beans;

import com.traket.entity.CredencialesUsuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dani
 */
public class CredecialesFacade extends AbstractFacade<CredencialesUsuario> {

     @PersistenceContext(unitName = "TraketPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CredecialesFacade() {
        super(CredencialesUsuario.class);
    }
    
}
