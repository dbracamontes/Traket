/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.beans;


import com.traket.entity.CredencialesUsuario;
import com.traket.entity.Empleado;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dani
 */
@Stateless
@LocalBean
public class CredUsuarioFacade extends AbstractFacade<CredencialesUsuario> {
    
    @PersistenceContext(unitName = "TraketPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CredUsuarioFacade() {
        super(CredencialesUsuario.class);
    }
    
    /**
     * Get user's credential by user name
     * @param userName
     * @return 
     */
    /*public UserCredentials getUserCredByUserName(String userName){   
        Query qry = em.createNamedQuery("UserCredentials.getByName");
        qry.setParameter("userName", userName);
        
        List<UserCredentials> credList = qry.getResultList();
        
        return credList.isEmpty() ? null : credList.get(0) ;        
    }*/
    
}
