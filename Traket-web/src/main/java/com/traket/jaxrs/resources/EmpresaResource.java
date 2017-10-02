/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.jaxrs.resources;

import com.traket.entity.Empresa;
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
@Path("empresa")
@RequestScoped
public class EmpresaResource {

    @Context
    private UriInfo context;

    @EJB
    private com.traket.beans.EmpresaFacade empresaFacade;

    /**
     * Creates a new instance of GenericResource
     */
    public EmpresaResource() {
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
    public Response getEmpresas() {
        List<Empresa> empresas = empresaFacade.findAll();

        if (empresas.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(empresas).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpresaById(@PathParam("id") long id) {
        Empresa empresa = empresaFacade.find(id);
        if (empresa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(empresa).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(Empresa empresa) {

        return Response.status(Response.Status.CREATED).entity(empresaFacade.create(empresa)).build();

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmpresa(Empresa empresa) {
        return Response.status(Response.Status.OK).entity(empresaFacade.edit(empresa)).build();
    }

    @DELETE
    public Response deleteUser(Empresa empresa) {
        empresaFacade.remove(empresa);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
