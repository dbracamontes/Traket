/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracket.beans;

import com.tracket.entity.TicketComentarios;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dani
 */
@Stateless
@LocalBean
public class TicketComentariosFacade extends AbstractFacade<TicketComentarios> {

    @PersistenceContext(unitName = "TraketPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketComentariosFacade() {
        super(TicketComentarios.class);
    }
    
}
