package com.traket.jaxrs.resources;

import com.traket.entity.Empleado;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 * 
 * @author Abraham Rayas<abraham.rayas@hotmail.com>
 */
@Path("empleado")
@RequestScoped
public class EmpleadoResource {
    
    @Context
    private UriInfo context;

    @EJB
    private com.traket.beans.EmpleadoFacade empleadoFacade;
    
    /**
     * Creates a new instance of GenericResource
     */
    public EmpleadoResource() {
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
    public Response getAll() {
        List<Empleado> empresas = empleadoFacade.findAll();
        if (empresas.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(empresas).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) {
        Empleado empresa = empleadoFacade.find(id);
        if (empresa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(empresa).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Empleado empleado) {
        return Response.status(Response.Status.CREATED).entity(empleadoFacade.create(empleado)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Empleado empleado) {
        return Response.status(Response.Status.OK).entity(empleadoFacade.edit(empleado)).build();
    }

    @DELETE
    public Response delete(Empleado empleado) {
        empleadoFacade.remove(empleado);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
