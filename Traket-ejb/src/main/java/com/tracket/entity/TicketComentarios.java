/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracket.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dani
 */
@Entity
@Table(name = "ticket_comentarios", catalog = "traket", schema = "public")
@NamedQueries({
    @NamedQuery(name = "TicketComentarios.findAll", query = "SELECT t FROM TicketComentarios t")
    , @NamedQuery(name = "TicketComentarios.findByRid", query = "SELECT t FROM TicketComentarios t WHERE t.rid = :rid")
    , @NamedQuery(name = "TicketComentarios.findByComentario", query = "SELECT t FROM TicketComentarios t WHERE t.comentario = :comentario")
    , @NamedQuery(name = "TicketComentarios.findByFechaCreacion", query = "SELECT t FROM TicketComentarios t WHERE t.fechaCreacion = :fechaCreacion")})
public class TicketComentarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "ticket_comentarios_rid_seq")
    @Basic(optional = false)
    @Column(name = "rid", nullable = false)
    private Long rid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "comentario", nullable = false)
    private String comentario;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "rid_empleado", referencedColumnName = "rid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado ridEmpleado;
    @JoinColumn(name = "rid_ticket", referencedColumnName = "rid", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ticket ridTicket;
    @JoinColumn(name = "rid_usuario", referencedColumnName = "rid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario ridUsuario;

    public TicketComentarios() {
    }

    public TicketComentarios(Long rid) {
        this.rid = rid;
    }

    public TicketComentarios(Long rid, String comentario) {
        this.rid = rid;
        this.comentario = comentario;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Empleado getRidEmpleado() {
        return ridEmpleado;
    }

    public void setRidEmpleado(Empleado ridEmpleado) {
        this.ridEmpleado = ridEmpleado;
    }

    public Ticket getRidTicket() {
        return ridTicket;
    }

    public void setRidTicket(Ticket ridTicket) {
        this.ridTicket = ridTicket;
    }

    public Usuario getRidUsuario() {
        return ridUsuario;
    }

    public void setRidUsuario(Usuario ridUsuario) {
        this.ridUsuario = ridUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rid != null ? rid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketComentarios)) {
            return false;
        }
        TicketComentarios other = (TicketComentarios) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tracket.entity.TicketComentarios[ rid=" + rid + " ]";
    }
    
}
