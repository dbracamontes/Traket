package com.traket.jaxrs.resources;

import com.traket.entity.Ticket;
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
@Path("ticket")
@RequestScoped
public class TicketResource {

    @Context
    private UriInfo context;

    @EJB
    private com.traket.beans.TicketFacade ticketFacade;

    /**
     * Creates a new instance of GenericResource
     */
    public TicketResource() {
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
        List<Ticket> ticket = ticketFacade.findAll();
        if (ticket.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ticket).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) {
        Ticket ticket = ticketFacade.find(id);
        if (ticket == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ticket).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Ticket ticket) {
        return Response.status(Response.Status.CREATED).entity(ticketFacade.create(ticket)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Ticket ticket) {
        return Response.status(Response.Status.OK).entity(ticketFacade.edit(ticket)).build();
    }

    @DELETE
    public Response delete(Ticket ticket) {
        ticketFacade.remove(ticket);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
