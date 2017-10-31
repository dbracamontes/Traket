/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.beans;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.traket.entity.CredencialesUsuario;
import com.traket.entity.Usuario;
import com.traket.security.PasswordEncryptionService;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dani
 */
@Stateless
@LocalBean
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "TraketPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    /**
     * Get user's credential by user name
     *
     * @param email
     * @return
     */
    public Usuario getUsuarioByEmail(String email) {
        Usuario usuario = new Usuario();
        try {
            Query qry = em.createQuery("Usuario.findByEmail");
            qry.setParameter("email", email);
            usuario = (Usuario) qry.setMaxResults(1).getSingleResult();

        } catch (NoResultException ex) {

        }
        return usuario;
    }

    /**
     * Validate if the user exist
     *
     * @param email
     * @return
     */
    public boolean isUser(String email) {
        Usuario usuario = this.getUsuarioByEmail(email);

        return usuario.getRid() > 0;
    }

    /**
     * Validate user name against password
     *
     * @param usuarioRemote
     * @return
     */
    public boolean isAuth(Usuario usuarioRemote) {
        boolean isAuth = false;

        if (!isUser(usuarioRemote.getEmail())) {
            System.out.println("usuario no existe");
            return false;
        } else {
            PasswordEncryptionService passEnc = new PasswordEncryptionService();
            Usuario usuarioDb = this.getUsuarioByEmail(usuarioRemote.getEmail());
            isAuth = passEnc.authenticate(usuarioRemote.getCredencialesUsuario().getPasswordNoEnc(),
                    usuarioDb.getCredencialesUsuario().getPassword(),
                    usuarioDb.getCredencialesUsuario().getSalt());
        }

        return isAuth;
    }

    /**
     * Generate user and userCredentials
     *
     * @param usuario
     * @return
     */
    public Usuario createUserWithCred(Usuario usuario) {
        PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionService();
        CredencialesUsuario credencialesUsuario = usuario.getCredencialesUsuario();

        if (credencialesUsuario != null) {
            LocalDateTime nowDate = LocalDateTime.now();

            byte[] salt = passwordEncryptionService.generateSalt();
            byte[] passwordEnc = passwordEncryptionService.getEncryptedPassword(credencialesUsuario.getPasswordNoEnc(), salt);

            credencialesUsuario.setPassword(passwordEnc);
            credencialesUsuario.setSalt(salt);
            credencialesUsuario.setFecha_modificacion(nowDate);
            credencialesUsuario.setFecha_expiracion(nowDate.plusMonths(2));

            usuario.setCredencialesUsuario(credencialesUsuario);
        }
        //userDao.create(user);

        return this.create(usuario);
    }

    /**
     * Generate token for web session
     *
     * @param credencialesUsuario
     * @return
     */
    public String generateToken(CredencialesUsuario credencialesUsuario) {
        String token = "";
        Instant instant = Instant.now();

        try {
            Algorithm algorithm = Algorithm.HMAC256(".d6a1b8l1#");
            Map<String, Object> headerClaims = new HashMap<String, Object>();
            headerClaims.put("typ", "jwt");

            token = JWT.create()
                    .withHeader(headerClaims)
                    .withIssuer("http://localhost/traket")
                    .withIssuedAt(Date.from(instant))
                    .withExpiresAt(Date.from(instant.plus(30, ChronoUnit.MINUTES)))
                    .withSubject(credencialesUsuario.getUsuario().getEmail())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            //Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return token;
    }

}
