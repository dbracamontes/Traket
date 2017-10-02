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
import com.traket.entity.Empresa;
import com.traket.entity.TicketComentarios;
import java.util.ArrayList;
import java.util.Collection;
import com.traket.entity.Ticket;
import com.traket.entity.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author dani
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws RollbackFailureException, Exception {
        if (usuario.getTicketComentariosCollection() == null) {
            usuario.setTicketComentariosCollection(new ArrayList<TicketComentarios>());
        }
        if (usuario.getTicketCollection() == null) {
            usuario.setTicketCollection(new ArrayList<Ticket>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empresa belongs = usuario.getBelongs();
            if (belongs != null) {
                belongs = em.getReference(belongs.getClass(), belongs.getRid());
                usuario.setBelongs(belongs);
            }
            Collection<TicketComentarios> attachedTicketComentariosCollection = new ArrayList<TicketComentarios>();
            for (TicketComentarios ticketComentariosCollectionTicketComentariosToAttach : usuario.getTicketComentariosCollection()) {
                ticketComentariosCollectionTicketComentariosToAttach = em.getReference(ticketComentariosCollectionTicketComentariosToAttach.getClass(), ticketComentariosCollectionTicketComentariosToAttach.getRid());
                attachedTicketComentariosCollection.add(ticketComentariosCollectionTicketComentariosToAttach);
            }
            usuario.setTicketComentariosCollection(attachedTicketComentariosCollection);
            Collection<Ticket> attachedTicketCollection = new ArrayList<Ticket>();
            for (Ticket ticketCollectionTicketToAttach : usuario.getTicketCollection()) {
                ticketCollectionTicketToAttach = em.getReference(ticketCollectionTicketToAttach.getClass(), ticketCollectionTicketToAttach.getRid());
                attachedTicketCollection.add(ticketCollectionTicketToAttach);
            }
            usuario.setTicketCollection(attachedTicketCollection);
            em.persist(usuario);
            if (belongs != null) {
                belongs.getUsuarioCollection().add(usuario);
                belongs = em.merge(belongs);
            }
            for (TicketComentarios ticketComentariosCollectionTicketComentarios : usuario.getTicketComentariosCollection()) {
                Usuario oldRidUsuarioOfTicketComentariosCollectionTicketComentarios = ticketComentariosCollectionTicketComentarios.getRidUsuario();
                ticketComentariosCollectionTicketComentarios.setRidUsuario(usuario);
                ticketComentariosCollectionTicketComentarios = em.merge(ticketComentariosCollectionTicketComentarios);
                if (oldRidUsuarioOfTicketComentariosCollectionTicketComentarios != null) {
                    oldRidUsuarioOfTicketComentariosCollectionTicketComentarios.getTicketComentariosCollection().remove(ticketComentariosCollectionTicketComentarios);
                    oldRidUsuarioOfTicketComentariosCollectionTicketComentarios = em.merge(oldRidUsuarioOfTicketComentariosCollectionTicketComentarios);
                }
            }
            for (Ticket ticketCollectionTicket : usuario.getTicketCollection()) {
                Usuario oldRidUsuarioOfTicketCollectionTicket = ticketCollectionTicket.getRidUsuario();
                ticketCollectionTicket.setRidUsuario(usuario);
                ticketCollectionTicket = em.merge(ticketCollectionTicket);
                if (oldRidUsuarioOfTicketCollectionTicket != null) {
                    oldRidUsuarioOfTicketCollectionTicket.getTicketCollection().remove(ticketCollectionTicket);
                    oldRidUsuarioOfTicketCollectionTicket = em.merge(oldRidUsuarioOfTicketCollectionTicket);
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

    public void edit(Usuario usuario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getRid());
            Empresa belongsOld = persistentUsuario.getBelongs();
            Empresa belongsNew = usuario.getBelongs();
            Collection<TicketComentarios> ticketComentariosCollectionOld = persistentUsuario.getTicketComentariosCollection();
            Collection<TicketComentarios> ticketComentariosCollectionNew = usuario.getTicketComentariosCollection();
            Collection<Ticket> ticketCollectionOld = persistentUsuario.getTicketCollection();
            Collection<Ticket> ticketCollectionNew = usuario.getTicketCollection();
            if (belongsNew != null) {
                belongsNew = em.getReference(belongsNew.getClass(), belongsNew.getRid());
                usuario.setBelongs(belongsNew);
            }
            Collection<TicketComentarios> attachedTicketComentariosCollectionNew = new ArrayList<TicketComentarios>();
            for (TicketComentarios ticketComentariosCollectionNewTicketComentariosToAttach : ticketComentariosCollectionNew) {
                ticketComentariosCollectionNewTicketComentariosToAttach = em.getReference(ticketComentariosCollectionNewTicketComentariosToAttach.getClass(), ticketComentariosCollectionNewTicketComentariosToAttach.getRid());
                attachedTicketComentariosCollectionNew.add(ticketComentariosCollectionNewTicketComentariosToAttach);
            }
            ticketComentariosCollectionNew = attachedTicketComentariosCollectionNew;
            usuario.setTicketComentariosCollection(ticketComentariosCollectionNew);
            Collection<Ticket> attachedTicketCollectionNew = new ArrayList<Ticket>();
            for (Ticket ticketCollectionNewTicketToAttach : ticketCollectionNew) {
                ticketCollectionNewTicketToAttach = em.getReference(ticketCollectionNewTicketToAttach.getClass(), ticketCollectionNewTicketToAttach.getRid());
                attachedTicketCollectionNew.add(ticketCollectionNewTicketToAttach);
            }
            ticketCollectionNew = attachedTicketCollectionNew;
            usuario.setTicketCollection(ticketCollectionNew);
            usuario = em.merge(usuario);
            if (belongsOld != null && !belongsOld.equals(belongsNew)) {
                belongsOld.getUsuarioCollection().remove(usuario);
                belongsOld = em.merge(belongsOld);
            }
            if (belongsNew != null && !belongsNew.equals(belongsOld)) {
                belongsNew.getUsuarioCollection().add(usuario);
                belongsNew = em.merge(belongsNew);
            }
            for (TicketComentarios ticketComentariosCollectionOldTicketComentarios : ticketComentariosCollectionOld) {
                if (!ticketComentariosCollectionNew.contains(ticketComentariosCollectionOldTicketComentarios)) {
                    ticketComentariosCollectionOldTicketComentarios.setRidUsuario(null);
                    ticketComentariosCollectionOldTicketComentarios = em.merge(ticketComentariosCollectionOldTicketComentarios);
                }
            }
            for (TicketComentarios ticketComentariosCollectionNewTicketComentarios : ticketComentariosCollectionNew) {
                if (!ticketComentariosCollectionOld.contains(ticketComentariosCollectionNewTicketComentarios)) {
                    Usuario oldRidUsuarioOfTicketComentariosCollectionNewTicketComentarios = ticketComentariosCollectionNewTicketComentarios.getRidUsuario();
                    ticketComentariosCollectionNewTicketComentarios.setRidUsuario(usuario);
                    ticketComentariosCollectionNewTicketComentarios = em.merge(ticketComentariosCollectionNewTicketComentarios);
                    if (oldRidUsuarioOfTicketComentariosCollectionNewTicketComentarios != null && !oldRidUsuarioOfTicketComentariosCollectionNewTicketComentarios.equals(usuario)) {
                        oldRidUsuarioOfTicketComentariosCollectionNewTicketComentarios.getTicketComentariosCollection().remove(ticketComentariosCollectionNewTicketComentarios);
                        oldRidUsuarioOfTicketComentariosCollectionNewTicketComentarios = em.merge(oldRidUsuarioOfTicketComentariosCollectionNewTicketComentarios);
                    }
                }
            }
            for (Ticket ticketCollectionOldTicket : ticketCollectionOld) {
                if (!ticketCollectionNew.contains(ticketCollectionOldTicket)) {
                    ticketCollectionOldTicket.setRidUsuario(null);
                    ticketCollectionOldTicket = em.merge(ticketCollectionOldTicket);
                }
            }
            for (Ticket ticketCollectionNewTicket : ticketCollectionNew) {
                if (!ticketCollectionOld.contains(ticketCollectionNewTicket)) {
                    Usuario oldRidUsuarioOfTicketCollectionNewTicket = ticketCollectionNewTicket.getRidUsuario();
                    ticketCollectionNewTicket.setRidUsuario(usuario);
                    ticketCollectionNewTicket = em.merge(ticketCollectionNewTicket);
                    if (oldRidUsuarioOfTicketCollectionNewTicket != null && !oldRidUsuarioOfTicketCollectionNewTicket.equals(usuario)) {
                        oldRidUsuarioOfTicketCollectionNewTicket.getTicketCollection().remove(ticketCollectionNewTicket);
                        oldRidUsuarioOfTicketCollectionNewTicket = em.merge(oldRidUsuarioOfTicketCollectionNewTicket);
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
                Long id = usuario.getRid();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getRid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Empresa belongs = usuario.getBelongs();
            if (belongs != null) {
                belongs.getUsuarioCollection().remove(usuario);
                belongs = em.merge(belongs);
            }
            Collection<TicketComentarios> ticketComentariosCollection = usuario.getTicketComentariosCollection();
            for (TicketComentarios ticketComentariosCollectionTicketComentarios : ticketComentariosCollection) {
                ticketComentariosCollectionTicketComentarios.setRidUsuario(null);
                ticketComentariosCollectionTicketComentarios = em.merge(ticketComentariosCollectionTicketComentarios);
            }
            Collection<Ticket> ticketCollection = usuario.getTicketCollection();
            for (Ticket ticketCollectionTicket : ticketCollection) {
                ticketCollectionTicket.setRidUsuario(null);
                ticketCollectionTicket = em.merge(ticketCollectionTicket);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
