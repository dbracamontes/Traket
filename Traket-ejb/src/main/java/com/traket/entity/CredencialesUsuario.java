/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dani
 *
 * This class is the representation of UserCredential. It contains the
 * properties needed to represent the UserCredential.
 *
 * The class is anotated with the @Entity
 */
@Entity
@Table(name = "credenciales_usuario")
@NamedQueries({
    @NamedQuery(name = "Credenciales.findAll", query = "SELECT c FROM CredencialesUsuario c")
    , @NamedQuery(name = "Credenciales.findByRid", query = "SELECT c FROM Usuario c WHERE c.rid = :rid")
    , @NamedQuery(name = "Credenciales.findByUsuarioRid", query = "SELECT c FROM CredencialesUsuario c WHERE c.usuario.email = :email")
   })
public class CredencialesUsuario implements Serializable {

    public CredencialesUsuario() {
    }

    /**
     * The id of the entity, it is a unique an consecutive number
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Long id;

    /**
     * Encripted Password
     */
    @NotNull(message = "Password can't be null")
    @JsonIgnore
    @Column(name = "password")
    private byte[] password;

    /**
     * Password without encryp
     */
    @Transient
    private String passwordNoEnc;

    /**
     * Salt for the encripted password
     */
    @JsonIgnore
    @Column(name = "salt")
    private byte[] salt;

    /**
     * Password's expired Date
     */
    @JsonIgnore
    @NotNull(message = "Expired date can´t be null")
    private LocalDateTime fecha_expiracion;

    /**
     * Password's last modified date
     */
    @JsonIgnore
    @NotNull(message = "Last modified date can´t be null")
    private LocalDateTime fecha_modificacion;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "rid_usuario")
    private Usuario usuario;

    /**
     *
     * @return {@link #id}
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id {@link #id}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return {@link #password}
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     *
     * @param password {@link #password}
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     *
     * @return {@link #salt}
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     *
     * @param salt {@link #salt}
     */
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getPasswordNoEnc() {
        return passwordNoEnc;
    }

    public void setPasswordNoEnc(String passwordNoEnc) {
        this.passwordNoEnc = passwordNoEnc;
    }

    public LocalDateTime getFecha_expiracion() {
        return fecha_expiracion;
    }

    public void setFecha_expiracion(LocalDateTime fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public LocalDateTime getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(LocalDateTime fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "CredencialesUsuario{" + "id=" + id + ", password=" + password + ", passwordNoEnc=" + passwordNoEnc + ", salt=" + salt + ", fecha_expiracion=" + fecha_expiracion + ", fecha_modificacion=" + fecha_modificacion + '}';
    }

}
