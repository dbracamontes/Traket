/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.traket.exceptions.NonexistentEntityException;
import com.traket.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.traket.entity.Empleado;
import com.traket.entity.Ticket;
import com.traket.entity.TicketComentarios;
import com.traket.entity.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author dani
 */
public class TicketComentariosJpaController implements Serializable {

    public TicketComentariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TicketComentarios ticketComentarios) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleado ridEmpleado = ticketComentarios.getRidEmpleado();
            if (ridEmpleado != null) {
                ridEmpleado = em.getReference(ridEmpleado.getClass(), ridEmpleado.getRid());
                ticketComentarios.setRidEmpleado(ridEmpleado);
            }
            Ticket ridTicket = ticketComentarios.getRidTicket();
            if (ridTicket != null) {
                ridTicket = em.getReference(ridTicket.getClass(), ridTicket.getRid());
                ticketComentarios.setRidTicket(ridTicket);
            }
            Usuario ridUsuario = ticketComentarios.getRidUsuario();
            if (ridUsuario != null) {
                ridUsuario = em.getReference(ridUsuario.getClass(), ridUsuario.getRid());
                ticketComentarios.setRidUsuario(ridUsuario);
            }
            em.persist(ticketComentarios);
            if (ridEmpleado != null) {
                ridEmpleado.getTicketComentariosCollection().add(ticketComentarios);
                ridEmpleado = em.merge(ridEmpleado);
            }
            if (ridTicket != null) {
                ridTicket.getTicketComentariosCollection().add(ticketComentarios);
                ridTicket = em.merge(ridTicket);
            }
            if (ridUsuario != null) {
                ridUsuario.getTicketComentariosCollection().add(ticketComentarios);
                ridUsuario = em.merge(ridUsuario);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TicketComentarios ticketComentarios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TicketComentarios persistentTicketComentarios = em.find(TicketComentarios.class, ticketComentarios.getRid());
            Empleado ridEmpleadoOld = persistentTicketComentarios.getRidEmpleado();
            Empleado ridEmpleadoNew = ticketComentarios.getRidEmpleado();
            Ticket ridTicketOld = persistentTicketComentarios.getRidTicket();
            Ticket ridTicketNew = ticketComentarios.getRidTicket();
            Usuario ridUsuarioOld = persistentTicketComentarios.getRidUsuario();
            Usuario ridUsuarioNew = ticketComentarios.getRidUsuario();
            if (ridEmpleadoNew != null) {
                ridEmpleadoNew = em.getReference(ridEmpleadoNew.getClass(), ridEmpleadoNew.getRid());
                ticketComentarios.setRidEmpleado(ridEmpleadoNew);
            }
            if (ridTicketNew != null) {
                ridTicketNew = em.getReference(ridTicketNew.getClass(), ridTicketNew.getRid());
                ticketComentarios.setRidTicket(ridTicketNew);
            }
            if (ridUsuarioNew != null) {
                ridUsuarioNew = em.getReference(ridUsuarioNew.getClass(), ridUsuarioNew.getRid());
                ticketComentarios.setRidUsuario(ridUsuarioNew);
            }
            ticketComentarios = em.merge(ticketComentarios);
            if (ridEmpleadoOld != null && !ridEmpleadoOld.equals(ridEmpleadoNew)) {
                ridEmpleadoOld.getTicketComentariosCollection().remove(ticketComentarios);
                ridEmpleadoOld = em.merge(ridEmpleadoOld);
            }
            if (ridEmpleadoNew != null && !ridEmpleadoNew.equals(ridEmpleadoOld)) {
                ridEmpleadoNew.getTicketComentariosCollection().add(ticketComentarios);
                ridEmpleadoNew = em.merge(ridEmpleadoNew);
            }
            if (ridTicketOld != null && !ridTicketOld.equals(ridTicketNew)) {
                ridTicketOld.getTicketComentariosCollection().remove(ticketComentarios);
                ridTicketOld = em.merge(ridTicketOld);
            }
            if (ridTicketNew != null && !ridTicketNew.equals(ridTicketOld)) {
                ridTicketNew.getTicketComentariosCollection().add(ticketComentarios);
                ridTicketNew = em.merge(ridTicketNew);
            }
            if (ridUsuarioOld != null && !ridUsuarioOld.equals(ridUsuarioNew)) {
                ridUsuarioOld.getTicketComentariosCollection().remove(ticketComentarios);
                ridUsuarioOld = em.merge(ridUsuarioOld);
            }
            if (ridUsuarioNew != null && !ridUsuarioNew.equals(ridUsuarioOld)) {
                ridUsuarioNew.getTicketComentariosCollection().add(ticketComentarios);
                ridUsuarioNew = em.merge(ridUsuarioNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ticketComentarios.getRid();
                if (findTicketComentarios(id) == null) {
                    throw new NonexistentEntityException("The ticketComentarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TicketComentarios ticketComentarios;
            try {
                ticketComentarios = em.getReference(TicketComentarios.class, id);
                ticketComentarios.getRid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ticketComentarios with id " + id + " no longer exists.", enfe);
            }
            Empleado ridEmpleado = ticketComentarios.getRidEmpleado();
            if (ridEmpleado != null) {
                ridEmpleado.getTicketComentariosCollection().remove(ticketComentarios);
                ridEmpleado = em.merge(ridEmpleado);
            }
            Ticket ridTicket = ticketComentarios.getRidTicket();
            if (ridTicket != null) {
                ridTicket.getTicketComentariosCollection().remove(ticketComentarios);
                ridTicket = em.merge(ridTicket);
            }
            Usuario ridUsuario = ticketComentarios.getRidUsuario();
            if (ridUsuario != null) {
                ridUsuario.getTicketComentariosCollection().remove(ticketComentarios);
                ridUsuario = em.merge(ridUsuario);
            }
            em.remove(ticketComentarios);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TicketComentarios> findTicketComentariosEntities() {
        return findTicketComentariosEntities(true, -1, -1);
    }

    public List<TicketComentarios> findTicketComentariosEntities(int maxResults, int firstResult) {
        return findTicketComentariosEntities(false, maxResults, firstResult);
    }

    private List<TicketComentarios> findTicketComentariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TicketComentarios.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TicketComentarios findTicketComentarios(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TicketComentarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTicketComentariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TicketComentarios> rt = cq.from(TicketComentarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
