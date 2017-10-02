/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.traket.exceptions.IllegalOrphanException;
import com.traket.exceptions.NonexistentEntityException;
import com.traket.exceptions.RollbackFailureException;
import com.traket.entity.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.traket.entity.Empresa;
import com.traket.entity.TicketComentarios;
import java.util.ArrayList;
import java.util.Collection;
import com.traket.entity.Ticket;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author dani
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws RollbackFailureException, Exception {
        if (empleado.getTicketComentariosCollection() == null) {
            empleado.setTicketComentariosCollection(new ArrayList<TicketComentarios>());
        }
        if (empleado.getTicketCollection() == null) {
            empleado.setTicketCollection(new ArrayList<Ticket>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empresa belongs = empleado.getBelongs();
            if (belongs != null) {
                belongs = em.getReference(belongs.getClass(), belongs.getRid());
                empleado.setBelongs(belongs);
            }
            Collection<TicketComentarios> attachedTicketComentariosCollection = new ArrayList<TicketComentarios>();
            for (TicketComentarios ticketComentariosCollectionTicketComentariosToAttach : empleado.getTicketComentariosCollection()) {
                ticketComentariosCollectionTicketComentariosToAttach = em.getReference(ticketComentariosCollectionTicketComentariosToAttach.getClass(), ticketComentariosCollectionTicketComentariosToAttach.getRid());
                attachedTicketComentariosCollection.add(ticketComentariosCollectionTicketComentariosToAttach);
            }
            empleado.setTicketComentariosCollection(attachedTicketComentariosCollection);
            Collection<Ticket> attachedTicketCollection = new ArrayList<Ticket>();
            for (Ticket ticketCollectionTicketToAttach : empleado.getTicketCollection()) {
                ticketCollectionTicketToAttach = em.getReference(ticketCollectionTicketToAttach.getClass(), ticketCollectionTicketToAttach.getRid());
                attachedTicketCollection.add(ticketCollectionTicketToAttach);
            }
            empleado.setTicketCollection(attachedTicketCollection);
            em.persist(empleado);
            if (belongs != null) {
                belongs.getEmpleadoCollection().add(empleado);
                belongs = em.merge(belongs);
            }
            for (TicketComentarios ticketComentariosCollectionTicketComentarios : empleado.getTicketComentariosCollection()) {
                Empleado oldRidEmpleadoOfTicketComentariosCollectionTicketComentarios = ticketComentariosCollectionTicketComentarios.getRidEmpleado();
                ticketComentariosCollectionTicketComentarios.setRidEmpleado(empleado);
                ticketComentariosCollectionTicketComentarios = em.merge(ticketComentariosCollectionTicketComentarios);
                if (oldRidEmpleadoOfTicketComentariosCollectionTicketComentarios != null) {
                    oldRidEmpleadoOfTicketComentariosCollectionTicketComentarios.getTicketComentariosCollection().remove(ticketComentariosCollectionTicketComentarios);
                    oldRidEmpleadoOfTicketComentariosCollectionTicketComentarios = em.merge(oldRidEmpleadoOfTicketComentariosCollectionTicketComentarios);
                }
            }
            for (Ticket ticketCollectionTicket : empleado.getTicketCollection()) {
                Empleado oldRidEmpleadoOfTicketCollectionTicket = ticketCollectionTicket.getRidEmpleado();
                ticketCollectionTicket.setRidEmpleado(empleado);
                ticketCollectionTicket = em.merge(ticketCollectionTicket);
                if (oldRidEmpleadoOfTicketCollectionTicket != null) {
                    oldRidEmpleadoOfTicketCollectionTicket.getTicketCollection().remove(ticketCollectionTicket);
                    oldRidEmpleadoOfTicketCollectionTicket = em.merge(oldRidEmpleadoOfTicketCollectionTicket);
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

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getRid());
            Empresa belongsOld = persistentEmpleado.getBelongs();
            Empresa belongsNew = empleado.getBelongs();
            Collection<TicketComentarios> ticketComentariosCollectionOld = persistentEmpleado.getTicketComentariosCollection();
            Collection<TicketComentarios> ticketComentariosCollectionNew = empleado.getTicketComentariosCollection();
            Collection<Ticket> ticketCollectionOld = persistentEmpleado.getTicketCollection();
            Collection<Ticket> ticketCollectionNew = empleado.getTicketCollection();
            List<String> illegalOrphanMessages = null;
            for (Ticket ticketCollectionOldTicket : ticketCollectionOld) {
                if (!ticketCollectionNew.contains(ticketCollectionOldTicket)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ticket " + ticketCollectionOldTicket + " since its ridEmpleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (belongsNew != null) {
                belongsNew = em.getReference(belongsNew.getClass(), belongsNew.getRid());
                empleado.setBelongs(belongsNew);
            }
            Collection<TicketComentarios> attachedTicketComentariosCollectionNew = new ArrayList<TicketComentarios>();
            for (TicketComentarios ticketComentariosCollectionNewTicketComentariosToAttach : ticketComentariosCollectionNew) {
                ticketComentariosCollectionNewTicketComentariosToAttach = em.getReference(ticketComentariosCollectionNewTicketComentariosToAttach.getClass(), ticketComentariosCollectionNewTicketComentariosToAttach.getRid());
                attachedTicketComentariosCollectionNew.add(ticketComentariosCollectionNewTicketComentariosToAttach);
            }
            ticketComentariosCollectionNew = attachedTicketComentariosCollectionNew;
            empleado.setTicketComentariosCollection(ticketComentariosCollectionNew);
            Collection<Ticket> attachedTicketCollectionNew = new ArrayList<Ticket>();
            for (Ticket ticketCollectionNewTicketToAttach : ticketCollectionNew) {
                ticketCollectionNewTicketToAttach = em.getReference(ticketCollectionNewTicketToAttach.getClass(), ticketCollectionNewTicketToAttach.getRid());
                attachedTicketCollectionNew.add(ticketCollectionNewTicketToAttach);
            }
            ticketCollectionNew = attachedTicketCollectionNew;
            empleado.setTicketCollection(ticketCollectionNew);
            empleado = em.merge(empleado);
            if (belongsOld != null && !belongsOld.equals(belongsNew)) {
                belongsOld.getEmpleadoCollection().remove(empleado);
                belongsOld = em.merge(belongsOld);
            }
            if (belongsNew != null && !belongsNew.equals(belongsOld)) {
                belongsNew.getEmpleadoCollection().add(empleado);
                belongsNew = em.merge(belongsNew);
            }
            for (TicketComentarios ticketComentariosCollectionOldTicketComentarios : ticketComentariosCollectionOld) {
                if (!ticketComentariosCollectionNew.contains(ticketComentariosCollectionOldTicketComentarios)) {
                    ticketComentariosCollectionOldTicketComentarios.setRidEmpleado(null);
                    ticketComentariosCollectionOldTicketComentarios = em.merge(ticketComentariosCollectionOldTicketComentarios);
                }
            }
            for (TicketComentarios ticketComentariosCollectionNewTicketComentarios : ticketComentariosCollectionNew) {
                if (!ticketComentariosCollectionOld.contains(ticketComentariosCollectionNewTicketComentarios)) {
                    Empleado oldRidEmpleadoOfTicketComentariosCollectionNewTicketComentarios = ticketComentariosCollectionNewTicketComentarios.getRidEmpleado();
                    ticketComentariosCollectionNewTicketComentarios.setRidEmpleado(empleado);
                    ticketComentariosCollectionNewTicketComentarios = em.merge(ticketComentariosCollectionNewTicketComentarios);
                    if (oldRidEmpleadoOfTicketComentariosCollectionNewTicketComentarios != null && !oldRidEmpleadoOfTicketComentariosCollectionNewTicketComentarios.equals(empleado)) {
                        oldRidEmpleadoOfTicketComentariosCollectionNewTicketComentarios.getTicketComentariosCollection().remove(ticketComentariosCollectionNewTicketComentarios);
                        oldRidEmpleadoOfTicketComentariosCollectionNewTicketComentarios = em.merge(oldRidEmpleadoOfTicketComentariosCollectionNewTicketComentarios);
                    }
                }
            }
            for (Ticket ticketCollectionNewTicket : ticketCollectionNew) {
                if (!ticketCollectionOld.contains(ticketCollectionNewTicket)) {
                    Empleado oldRidEmpleadoOfTicketCollectionNewTicket = ticketCollectionNewTicket.getRidEmpleado();
                    ticketCollectionNewTicket.setRidEmpleado(empleado);
                    ticketCollectionNewTicket = em.merge(ticketCollectionNewTicket);
                    if (oldRidEmpleadoOfTicketCollectionNewTicket != null && !oldRidEmpleadoOfTicketCollectionNewTicket.equals(empleado)) {
                        oldRidEmpleadoOfTicketCollectionNewTicket.getTicketCollection().remove(ticketCollectionNewTicket);
                        oldRidEmpleadoOfTicketCollectionNewTicket = em.merge(oldRidEmpleadoOfTicketCollectionNewTicket);
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
                Long id = empleado.getRid();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getRid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ticket> ticketCollectionOrphanCheck = empleado.getTicketCollection();
            for (Ticket ticketCollectionOrphanCheckTicket : ticketCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Ticket " + ticketCollectionOrphanCheckTicket + " in its ticketCollection field has a non-nullable ridEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresa belongs = empleado.getBelongs();
            if (belongs != null) {
                belongs.getEmpleadoCollection().remove(empleado);
                belongs = em.merge(belongs);
            }
            Collection<TicketComentarios> ticketComentariosCollection = empleado.getTicketComentariosCollection();
            for (TicketComentarios ticketComentariosCollectionTicketComentarios : ticketComentariosCollection) {
                ticketComentariosCollectionTicketComentarios.setRidEmpleado(null);
                ticketComentariosCollectionTicketComentarios = em.merge(ticketComentariosCollectionTicketComentarios);
            }
            em.remove(empleado);
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

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
