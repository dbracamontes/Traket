/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.traket.exceptions.IllegalOrphanException;
import com.traket.exceptions.NonexistentEntityException;
import com.traket.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.traket.entity.Ticket;
import java.util.ArrayList;
import java.util.Collection;
import com.traket.entity.Empleado;
import com.traket.entity.Empresa;
import com.traket.entity.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author dani
 */
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) throws RollbackFailureException, Exception {
        if (empresa.getTicketCollection() == null) {
            empresa.setTicketCollection(new ArrayList<Ticket>());
        }
        if (empresa.getEmpleadoCollection() == null) {
            empresa.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        if (empresa.getUsuarioCollection() == null) {
            empresa.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Ticket> attachedTicketCollection = new ArrayList<Ticket>();
            for (Ticket ticketCollectionTicketToAttach : empresa.getTicketCollection()) {
                ticketCollectionTicketToAttach = em.getReference(ticketCollectionTicketToAttach.getClass(), ticketCollectionTicketToAttach.getRid());
                attachedTicketCollection.add(ticketCollectionTicketToAttach);
            }
            empresa.setTicketCollection(attachedTicketCollection);
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : empresa.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getRid());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            empresa.setEmpleadoCollection(attachedEmpleadoCollection);
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : empresa.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getRid());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            empresa.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(empresa);
            for (Ticket ticketCollectionTicket : empresa.getTicketCollection()) {
                Empresa oldBelongsOfTicketCollectionTicket = ticketCollectionTicket.getBelongs();
                ticketCollectionTicket.setBelongs(empresa);
                ticketCollectionTicket = em.merge(ticketCollectionTicket);
                if (oldBelongsOfTicketCollectionTicket != null) {
                    oldBelongsOfTicketCollectionTicket.getTicketCollection().remove(ticketCollectionTicket);
                    oldBelongsOfTicketCollectionTicket = em.merge(oldBelongsOfTicketCollectionTicket);
                }
            }
            for (Empleado empleadoCollectionEmpleado : empresa.getEmpleadoCollection()) {
                Empresa oldBelongsOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getBelongs();
                empleadoCollectionEmpleado.setBelongs(empresa);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldBelongsOfEmpleadoCollectionEmpleado != null) {
                    oldBelongsOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldBelongsOfEmpleadoCollectionEmpleado = em.merge(oldBelongsOfEmpleadoCollectionEmpleado);
                }
            }
            for (Usuario usuarioCollectionUsuario : empresa.getUsuarioCollection()) {
                Empresa oldBelongsOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getBelongs();
                usuarioCollectionUsuario.setBelongs(empresa);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldBelongsOfUsuarioCollectionUsuario != null) {
                    oldBelongsOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldBelongsOfUsuarioCollectionUsuario = em.merge(oldBelongsOfUsuarioCollectionUsuario);
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

    public void edit(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getRid());
            Collection<Ticket> ticketCollectionOld = persistentEmpresa.getTicketCollection();
            Collection<Ticket> ticketCollectionNew = empresa.getTicketCollection();
            Collection<Empleado> empleadoCollectionOld = persistentEmpresa.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = empresa.getEmpleadoCollection();
            Collection<Usuario> usuarioCollectionOld = persistentEmpresa.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = empresa.getUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Ticket ticketCollectionOldTicket : ticketCollectionOld) {
                if (!ticketCollectionNew.contains(ticketCollectionOldTicket)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ticket " + ticketCollectionOldTicket + " since its belongs field is not nullable.");
                }
            }
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoCollectionOldEmpleado + " since its belongs field is not nullable.");
                }
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its belongs field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Ticket> attachedTicketCollectionNew = new ArrayList<Ticket>();
            for (Ticket ticketCollectionNewTicketToAttach : ticketCollectionNew) {
                ticketCollectionNewTicketToAttach = em.getReference(ticketCollectionNewTicketToAttach.getClass(), ticketCollectionNewTicketToAttach.getRid());
                attachedTicketCollectionNew.add(ticketCollectionNewTicketToAttach);
            }
            ticketCollectionNew = attachedTicketCollectionNew;
            empresa.setTicketCollection(ticketCollectionNew);
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getRid());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            empresa.setEmpleadoCollection(empleadoCollectionNew);
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getRid());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            empresa.setUsuarioCollection(usuarioCollectionNew);
            empresa = em.merge(empresa);
            for (Ticket ticketCollectionNewTicket : ticketCollectionNew) {
                if (!ticketCollectionOld.contains(ticketCollectionNewTicket)) {
                    Empresa oldBelongsOfTicketCollectionNewTicket = ticketCollectionNewTicket.getBelongs();
                    ticketCollectionNewTicket.setBelongs(empresa);
                    ticketCollectionNewTicket = em.merge(ticketCollectionNewTicket);
                    if (oldBelongsOfTicketCollectionNewTicket != null && !oldBelongsOfTicketCollectionNewTicket.equals(empresa)) {
                        oldBelongsOfTicketCollectionNewTicket.getTicketCollection().remove(ticketCollectionNewTicket);
                        oldBelongsOfTicketCollectionNewTicket = em.merge(oldBelongsOfTicketCollectionNewTicket);
                    }
                }
            }
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    Empresa oldBelongsOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getBelongs();
                    empleadoCollectionNewEmpleado.setBelongs(empresa);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldBelongsOfEmpleadoCollectionNewEmpleado != null && !oldBelongsOfEmpleadoCollectionNewEmpleado.equals(empresa)) {
                        oldBelongsOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldBelongsOfEmpleadoCollectionNewEmpleado = em.merge(oldBelongsOfEmpleadoCollectionNewEmpleado);
                    }
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Empresa oldBelongsOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getBelongs();
                    usuarioCollectionNewUsuario.setBelongs(empresa);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldBelongsOfUsuarioCollectionNewUsuario != null && !oldBelongsOfUsuarioCollectionNewUsuario.equals(empresa)) {
                        oldBelongsOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldBelongsOfUsuarioCollectionNewUsuario = em.merge(oldBelongsOfUsuarioCollectionNewUsuario);
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
                Long id = empresa.getRid();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
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
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getRid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ticket> ticketCollectionOrphanCheck = empresa.getTicketCollection();
            for (Ticket ticketCollectionOrphanCheckTicket : ticketCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Ticket " + ticketCollectionOrphanCheckTicket + " in its ticketCollection field has a non-nullable belongs field.");
            }
            Collection<Empleado> empleadoCollectionOrphanCheck = empresa.getEmpleadoCollection();
            for (Empleado empleadoCollectionOrphanCheckEmpleado : empleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Empleado " + empleadoCollectionOrphanCheckEmpleado + " in its empleadoCollection field has a non-nullable belongs field.");
            }
            Collection<Usuario> usuarioCollectionOrphanCheck = empresa.getUsuarioCollection();
            for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable belongs field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresa);
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

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
