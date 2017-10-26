/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.jaxrs.resources;

import com.traket.entity.Usuario;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author dani
 */
@Path("usuario")
@RequestScoped
public class UsuarioResource {

    @Context
    private UriInfo context;

    @EJB
    private com.traket.beans.UsuarioFacade usuarioFacade;

    /**
     * Creates a new instance of GenericResource
     */
    public UsuarioResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.traket.jaxrs.resources.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllUsuarios() {
        List<Usuario> usuarios = usuarioFacade.findAll();

        if (usuarios.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(usuarios).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findEmpresaById(@PathParam("id") long id) {
        Usuario usuario = usuarioFacade.find(id);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(usuario).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Usuario usuario) {

        return Response.status(Response.Status.CREATED).entity(usuarioFacade.create(usuario)).build();

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editEmpresa(Usuario usuario) {
        return Response.status(Response.Status.OK).entity(usuarioFacade.edit(usuario)).build();
    }

    @DELETE
    public Response deleteUser(Usuario usuario) {
        usuarioFacade.remove(usuario);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
