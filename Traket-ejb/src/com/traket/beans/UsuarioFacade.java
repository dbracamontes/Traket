/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.beans;

import com.traket.entity.Usuario;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dani
 */
@Stateless
@LocalBean
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "TraketPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario getUsuarioByEmail(String email) {
        Usuario usuario = new Usuario();
        try {
            Query query = em.createNamedQuery("Usuario.findByEmail");
            query.setParameter("email", email);

            usuario = (Usuario) query.getResultList();
        } catch (NoResultException ex) {

        }
        return usuario;
    }

}
