/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.beans;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author dani
 * @version 1.0
 */
@Stateless
@LocalBean
public class AuthFacade {

//    @EJB
//    private UsuarioFacade usuarioFacade;
//
//    @EJB
//    private CredecialesFacade credecialesFacade;
//
//    /**
//     * Validate if the user exist
//     *
//     * @param email
//     * @return
//     */
//    public boolean isUser(String email) {
//        Usuario usuario = usuarioFacade.getUsuarioByEmail(email);
//
//        return usuario.getRid()> 0;
//    }
//
//    /**
//     * Validate user name against password
//     *
//     * @param usuario
//     * @return
//     */
//    public boolean isAuth(Usuario usuario) {
//        boolean isAuth = false;
//
//        if (!isUser(usuario.getEmail())) {
//            System.out.println("usuario no existe");
//            return false;
//        } else {
//            PasswordEncryptionService passEnc = new PasswordEncryptionService();
//            Usuario credUsuario = usuarioFacade.getUsuarioByEmail(usuario.getEmail());
//            isAuth = passEnc.authenticate(usuario.getCredencialesUsuario().getPasswordNoEnc(), 
//                    credUsuario.getCredencialesUsuario().getPassword(),
//                    credUsuario.getCredencialesUsuario().getSalt());
//        }
//
//        return isAuth;
//    }
//
//    /**
//     * Generate user and userCredentials
//     *
//     * @param user
//     * @return
//     */
//    public Usuario createUserWithCred(Usuario usuario) {
//        PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionService();
//        CredencialesUsuario userCredentials = user.getUserCredentials();
//        LocalDateTime nowDate = LocalDateTime.now();
//
//        byte[] salt = passwordEncryptionService.generateSalt();
//        byte[] passwordEnc = passwordEncryptionService.getEncryptedPassword(user.getUserCredentials().getPasswordNotEnc(), salt);
//
//        userCredentials.setPassword(passwordEnc);
//        userCredentials.setSalt(salt);
//        userCredentials.setLastModifiedDate(nowDate);
//        userCredentials.setExpiredDate(nowDate.plusMonths(2));
//
//        user.setUserCredentials(userCredentials);
//        user.setCreationDate(nowDate);
//        userDao.create(user);
//
//        return userDao.create(user);
//    }
//
//    /**
//     * Generate token for web session
//     *
//     * @param userCredentials
//     * @return
//     */
//    public String generateToken(UserCredentials userCredentials) {
//        String token = "";
//        Instant instant = Instant.now();
//
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(".d6a1b8l1#");
//            Map<String, Object> headerClaims = new HashMap<String, Object>();
//            headerClaims.put("typ", "jwt");
//
//            token = JWT.create()
//                    .withHeader(headerClaims)
//                    .withIssuer("http://localhost/books")
//                    .withIssuedAt(Date.from(instant))
//                    .withExpiresAt(Date.from(instant.plus(30, ChronoUnit.MINUTES)))
//                    .withSubject(userCredentials.getUserName())
//                    .sign(algorithm);
//
//        } catch (JWTCreationException exception) {
//            //Invalid Signing configuration / Couldn't convert Claims.
//        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
//            Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return token;
//    }
//
//    /**
//     * Verify the JWT token provided
//     * @param token
//     * @return 
//     */
//    public boolean verifyToken(String token) {
//        boolean isVerify = false;
//        
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(".d6a1b8l1#");
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withIssuer("http://localhost/books")
//                    .build(); //Reusable verifier instance
//            DecodedJWT jwt = verifier.verify(token);
//            isVerify = true;            
//        } catch (UnsupportedEncodingException exception) {
//            //UTF-8 encoding not supported
//        } catch (JWTVerificationException exception) {
//            System.out.println(exception);
//        }
//
//        return isVerify;
//    }

}
