/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
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
@Table(name = "ticket", catalog = "traket", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t")
    , @NamedQuery(name = "Ticket.findByRid", query = "SELECT t FROM Ticket t WHERE t.rid = :rid")
    , @NamedQuery(name = "Ticket.findByTitulo", query = "SELECT t FROM Ticket t WHERE t.titulo = :titulo")
    , @NamedQuery(name = "Ticket.findByDescripcion", query = "SELECT t FROM Ticket t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Ticket.findByPrioridad", query = "SELECT t FROM Ticket t WHERE t.prioridad = :prioridad")
    , @NamedQuery(name = "Ticket.findByTiempoEstimado", query = "SELECT t FROM Ticket t WHERE t.tiempoEstimado = :tiempoEstimado")
    , @NamedQuery(name = "Ticket.findByArea", query = "SELECT t FROM Ticket t WHERE t.area = :area")
    , @NamedQuery(name = "Ticket.findByStatus", query = "SELECT t FROM Ticket t WHERE t.status = :status")
    , @NamedQuery(name = "Ticket.findByFechaEstimada", query = "SELECT t FROM Ticket t WHERE t.fechaEstimada = :fechaEstimada")
    , @NamedQuery(name = "Ticket.findByFechaCreacion", query = "SELECT t FROM Ticket t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Ticket.findByFechaModificacion", query = "SELECT t FROM Ticket t WHERE t.fechaModificacion = :fechaModificacion")})
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rid", nullable = false)
    private Long rid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "titulo", nullable = false)
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prioridad", nullable = false)
    private int prioridad;
    @Column(name = "tiempo_estimado")
    private Integer tiempoEstimado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "area", nullable = false)
    private String area;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "fecha_estimada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstimada;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ridTicket", fetch = FetchType.LAZY)
    private Collection<TicketComentarios> ticketComentariosCollection;
    
    @JsonIgnore
    @JoinColumn(name = "rid_empleado", referencedColumnName = "rid", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado ridEmpleado;
    
    @JsonIgnore
    @JoinColumn(name = "belongs", referencedColumnName = "rid", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa belongs;
    
    @JsonIgnore
    @JoinColumn(name = "rid_usuario", referencedColumnName = "rid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario ridUsuario;

    public Ticket() {
    }

    public Ticket(Long rid) {
        this.rid = rid;
    }

    public Ticket(Long rid, String titulo, String descripcion, int prioridad, String area, int status) {
        this.rid = rid;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.area = area;
        this.status = status;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Integer tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(Date fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Collection<TicketComentarios> getTicketComentariosCollection() {
        return ticketComentariosCollection;
    }

    public void setTicketComentariosCollection(Collection<TicketComentarios> ticketComentariosCollection) {
        this.ticketComentariosCollection = ticketComentariosCollection;
    }

    public Empleado getRidEmpleado() {
        return ridEmpleado;
    }

    public void setRidEmpleado(Empleado ridEmpleado) {
        this.ridEmpleado = ridEmpleado;
    }

    public Empresa getBelongs() {
        return belongs;
    }

    public void setBelongs(Empresa belongs) {
        this.belongs = belongs;
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
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ticket{" + "rid=" + rid + ", titulo=" + titulo + ", descripcion=" + descripcion + ", prioridad=" + prioridad + ", tiempoEstimado=" + tiempoEstimado + ", area=" + area + ", status=" + status + ", fechaEstimada=" + fechaEstimada + ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion + '}';
    }

}
