package com.traket.jaxrs.resources;

import com.traket.entity.TicketComentarios;
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
 * @author Abraham Rayas<abraham.rayas@hotmail.com>
 */
@Path("ticketComentarios")
@RequestScoped
public class TicketComentarioResource {

    @Context
    private UriInfo context;

    @EJB
    private com.traket.beans.TicketComentariosFacade ticketComentariosFacade;

    /**
     * Creates a new instance of GenericResource
     */
    public TicketComentarioResource() {
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
        List<TicketComentarios> empresas = ticketComentariosFacade.findAll();
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
        TicketComentarios ticketComentarios = ticketComentariosFacade.find(id);
        if (ticketComentarios == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ticketComentarios).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(TicketComentarios ticketComentarios) {
        return Response.status(Response.Status.CREATED).entity(ticketComentariosFacade.create(ticketComentarios)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(TicketComentarios ticketComentarios) {
        return Response.status(Response.Status.OK).entity(ticketComentariosFacade.edit(ticketComentarios)).build();
    }

    @DELETE
    public Response delete(TicketComentarios ticketComentarios) {
        ticketComentariosFacade.remove(ticketComentarios);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
