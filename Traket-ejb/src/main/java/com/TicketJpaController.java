/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.exceptions.IllegalOrphanException;
import com.exceptions.NonexistentEntityException;
import com.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tracket.entity.Empleado;
import com.tracket.entity.Empresa;
import com.tracket.entity.Ticket;
import com.tracket.entity.Usuario;
import com.tracket.entity.TicketComentarios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author dani
 */
public class TicketJpaController implements Serializable {

    public TicketJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ticket ticket) throws RollbackFailureException, Exception {
        if (ticket.getTicketComentariosCollection() == null) {
            ticket.setTicketComentariosCollection(new ArrayList<TicketComentarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleado ridEmpleado = ticket.getRidEmpleado();
            if (ridEmpleado != null) {
                ridEmpleado = em.getReference(ridEmpleado.getClass(), ridEmpleado.getRid());
                ticket.setRidEmpleado(ridEmpleado);
            }
            Empresa belongs = ticket.getBelongs();
            if (belongs != null) {
                belongs = em.getReference(belongs.getClass(), belongs.getRid());
                ticket.setBelongs(belongs);
            }
            Usuario ridUsuario = ticket.getRidUsuario();
            if (ridUsuario != null) {
                ridUsuario = em.getReference(ridUsuario.getClass(), ridUsuario.getRid());
                ticket.setRidUsuario(ridUsuario);
            }
            Collection<TicketComentarios> attachedTicketComentariosCollection = new ArrayList<TicketComentarios>();
            for (TicketComentarios ticketComentariosCollectionTicketComentariosToAttach : ticket.getTicketComentariosCollection()) {
                ticketComentariosCollectionTicketComentariosToAttach = em.getReference(ticketComentariosCollectionTicketComentariosToAttach.getClass(), ticketComentariosCollectionTicketComentariosToAttach.getRid());
                attachedTicketComentariosCollection.add(ticketComentariosCollectionTicketComentariosToAttach);
            }
            ticket.setTicketComentariosCollection(attachedTicketComentariosCollection);
            em.persist(ticket);
            if (ridEmpleado != null) {
                ridEmpleado.getTicketCollection().add(ticket);
                ridEmpleado = em.merge(ridEmpleado);
            }
            if (belongs != null) {
                belongs.getTicketCollection().add(ticket);
                belongs = em.merge(belongs);
            }
            if (ridUsuario != null) {
                ridUsuario.getTicketCollection().add(ticket);
                ridUsuario = em.merge(ridUsuario);
            }
            for (TicketComentarios ticketComentariosCollectionTicketComentarios : ticket.getTicketComentariosCollection()) {
                Ticket oldRidTicketOfTicketComentariosCollectionTicketComentarios = ticketComentariosCollectionTicketComentarios.getRidTicket();
                ticketComentariosCollectionTicketComentarios.setRidTicket(ticket);
                ticketComentariosCollectionTicketComentarios = em.merge(ticketComentariosCollectionTicketComentarios);
                if (oldRidTicketOfTicketComentariosCollectionTicketComentarios != null) {
                    oldRidTicketOfTicketComentariosCollectionTicketComentarios.getTicketComentariosCollection().remove(ticketComentariosCollectionTicketComentarios);
                    oldRidTicketOfTicketComentariosCollectionTicketComentarios = em.merge(oldRidTicketOfTicketComentariosCollectionTicketComentarios);
                }
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

    public void edit(Ticket ticket) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ticket persistentTicket = em.find(Ticket.class, ticket.getRid());
            Empleado ridEmpleadoOld = persistentTicket.getRidEmpleado();
            Empleado ridEmpleadoNew = ticket.getRidEmpleado();
            Empresa belongsOld = persistentTicket.getBelongs();
            Empresa belongsNew = ticket.getBelongs();
            Usuario ridUsuarioOld = persistentTicket.getRidUsuario();
            Usuario ridUsuarioNew = ticket.getRidUsuario();
            Collection<TicketComentarios> ticketComentariosCollectionOld = persistentTicket.getTicketComentariosCollection();
            Collection<TicketComentarios> ticketComentariosCollectionNew = ticket.getTicketComentariosCollection();
            List<String> illegalOrphanMessages = null;
            for (TicketComentarios ticketComentariosCollectionOldTicketComentarios : ticketComentariosCollectionOld) {
                if (!ticketComentariosCollectionNew.contains(ticketComentariosCollectionOldTicketComentarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TicketComentarios " + ticketComentariosCollectionOldTicketComentarios + " since its ridTicket field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ridEmpleadoNew != null) {
                ridEmpleadoNew = em.getReference(ridEmpleadoNew.getClass(), ridEmpleadoNew.getRid());
                ticket.setRidEmpleado(ridEmpleadoNew);
            }
            if (belongsNew != null) {
                belongsNew = em.getReference(belongsNew.getClass(), belongsNew.getRid());
                ticket.setBelongs(belongsNew);
            }
            if (ridUsuarioNew != null) {
                ridUsuarioNew = em.getReference(ridUsuarioNew.getClass(), ridUsuarioNew.getRid());
                ticket.setRidUsuario(ridUsuarioNew);
            }
            Collection<TicketComentarios> attachedTicketComentariosCollectionNew = new ArrayList<TicketComentarios>();
            for (TicketComentarios ticketComentariosCollectionNewTicketComentariosToAttach : ticketComentariosCollectionNew) {
                ticketComentariosCollectionNewTicketComentariosToAttach = em.getReference(ticketComentariosCollectionNewTicketComentariosToAttach.getClass(), ticketComentariosCollectionNewTicketComentariosToAttach.getRid());
                attachedTicketComentariosCollectionNew.add(ticketComentariosCollectionNewTicketComentariosToAttach);
            }
            ticketComentariosCollectionNew = attachedTicketComentariosCollectionNew;
            ticket.setTicketComentariosCollection(ticketComentariosCollectionNew);
            ticket = em.merge(ticket);
            if (ridEmpleadoOld != null && !ridEmpleadoOld.equals(ridEmpleadoNew)) {
                ridEmpleadoOld.getTicketCollection().remove(ticket);
                ridEmpleadoOld = em.merge(ridEmpleadoOld);
            }
            if (ridEmpleadoNew != null && !ridEmpleadoNew.equals(ridEmpleadoOld)) {
                ridEmpleadoNew.getTicketCollection().add(ticket);
                ridEmpleadoNew = em.merge(ridEmpleadoNew);
            }
            if (belongsOld != null && !belongsOld.equals(belongsNew)) {
                belongsOld.getTicketCollection().remove(ticket);
                belongsOld = em.merge(belongsOld);
            }
            if (belongsNew != null && !belongsNew.equals(belongsOld)) {
                belongsNew.getTicketCollection().add(ticket);
                belongsNew = em.merge(belongsNew);
            }
            if (ridUsuarioOld != null && !ridUsuarioOld.equals(ridUsuarioNew)) {
                ridUsuarioOld.getTicketCollection().remove(ticket);
                ridUsuarioOld = em.merge(ridUsuarioOld);
            }
            if (ridUsuarioNew != null && !ridUsuarioNew.equals(ridUsuarioOld)) {
                ridUsuarioNew.getTicketCollection().add(ticket);
                ridUsuarioNew = em.merge(ridUsuarioNew);
            }
            for (TicketComentarios ticketComentariosCollectionNewTicketComentarios : ticketComentariosCollectionNew) {
                if (!ticketComentariosCollectionOld.contains(ticketComentariosCollectionNewTicketComentarios)) {
                    Ticket oldRidTicketOfTicketComentariosCollectionNewTicketComentarios = ticketComentariosCollectionNewTicketComentarios.getRidTicket();
                    ticketComentariosCollectionNewTicketComentarios.setRidTicket(ticket);
                    ticketComentariosCollectionNewTicketComentarios = em.merge(ticketComentariosCollectionNewTicketComentarios);
                    if (oldRidTicketOfTicketComentariosCollectionNewTicketComentarios != null && !oldRidTicketOfTicketComentariosCollectionNewTicketComentarios.equals(ticket)) {
                        oldRidTicketOfTicketComentariosCollectionNewTicketComentarios.getTicketComentariosCollection().remove(ticketComentariosCollectionNewTicketComentarios);
                        oldRidTicketOfTicketComentariosCollectionNewTicketComentarios = em.merge(oldRidTicketOfTicketComentariosCollectionNewTicketComentarios);
                    }
                }
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
                Long id = ticket.getRid();
                if (findTicket(id) == null) {
                    throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ticket ticket;
            try {
                ticket = em.getReference(Ticket.class, id);
                ticket.getRid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TicketComentarios> ticketComentariosCollectionOrphanCheck = ticket.getTicketComentariosCollection();
            for (TicketComentarios ticketComentariosCollectionOrphanCheckTicketComentarios : ticketComentariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ticket (" + ticket + ") cannot be destroyed since the TicketComentarios " + ticketComentariosCollectionOrphanCheckTicketComentarios + " in its ticketComentariosCollection field has a non-nullable ridTicket field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empleado ridEmpleado = ticket.getRidEmpleado();
            if (ridEmpleado != null) {
                ridEmpleado.getTicketCollection().remove(ticket);
                ridEmpleado = em.merge(ridEmpleado);
            }
            Empresa belongs = ticket.getBelongs();
            if (belongs != null) {
                belongs.getTicketCollection().remove(ticket);
                belongs = em.merge(belongs);
            }
            Usuario ridUsuario = ticket.getRidUsuario();
            if (ridUsuario != null) {
                ridUsuario.getTicketCollection().remove(ticket);
                ridUsuario = em.merge(ridUsuario);
            }
            em.remove(ticket);
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

    public List<Ticket> findTicketEntities() {
        return findTicketEntities(true, -1, -1);
    }

    public List<Ticket> findTicketEntities(int maxResults, int firstResult) {
        return findTicketEntities(false, maxResults, firstResult);
    }

    private List<Ticket> findTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ticket.class));
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

    public Ticket findTicket(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
    }

    public int getTicketCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ticket> rt = cq.from(Ticket.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
