/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracket.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dani
 */
@Entity
@Table(name = "empleado", catalog = "traket", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e")
    , @NamedQuery(name = "Empleado.findByRid", query = "SELECT e FROM Empleado e WHERE e.rid = :rid")
    , @NamedQuery(name = "Empleado.findByNombre", query = "SELECT e FROM Empleado e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Empleado.findByApellidoPaterno", query = "SELECT e FROM Empleado e WHERE e.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Empleado.findByApellidoMaterno", query = "SELECT e FROM Empleado e WHERE e.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Empleado.findByPuesto", query = "SELECT e FROM Empleado e WHERE e.puesto = :puesto")
    , @NamedQuery(name = "Empleado.findByArea", query = "SELECT e FROM Empleado e WHERE e.area = :area")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_gen")
    @SequenceGenerator(name = "seq_gen", sequenceName = "empleado_rid_seq")
    @Basic(optional = false)
    @Column(name = "rid", nullable = false)
    private Long rid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "apellido_materno", nullable = false)
    private String apellidoMaterno;
    @Size(max = 2147483647)
    @Column(name = "puesto")
    private String puesto;
    @Size(max = 2147483647)
    @Column(name = "area")
    private String area;
    @OneToMany(mappedBy = "ridEmpleado", fetch = FetchType.LAZY)
    private Collection<TicketComentarios> ticketComentariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ridEmpleado", fetch = FetchType.LAZY)
    private Collection<Ticket> ticketCollection;
    @JoinColumn(name = "belongs", referencedColumnName = "rid", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa belongs;

    public Empleado() {
    }

    public Empleado(Long rid) {
        this.rid = rid;
    }

    public Empleado(Long rid, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.rid = rid;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Collection<TicketComentarios> getTicketComentariosCollection() {
        return ticketComentariosCollection;
    }

    public void setTicketComentariosCollection(Collection<TicketComentarios> ticketComentariosCollection) {
        this.ticketComentariosCollection = ticketComentariosCollection;
    }

    public Collection<Ticket> getTicketCollection() {
        return ticketCollection;
    }

    public void setTicketCollection(Collection<Ticket> ticketCollection) {
        this.ticketCollection = ticketCollection;
    }

    public Empresa getBelongs() {
        return belongs;
    }

    public void setBelongs(Empresa belongs) {
        this.belongs = belongs;
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
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tracket.entity.Empleado[ rid=" + rid + " ]";
    }
    
}
