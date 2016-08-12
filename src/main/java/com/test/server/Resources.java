package com.test.server;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author David Ferreira
 */

@Path("/")
public class Resources {

    private static ConcurrentValue concurrentValue = new ConcurrentValue();

    @GET
    @Path("/value")
    @Produces({MediaType.APPLICATION_JSON})
    public Response value() {
        // Get current
        Value currentValue = concurrentValue.get();
        return Response.status(200).entity(currentValue).build();
    }

    @POST
    @Path("/value")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response value(final Value value) {
        // Get current
        Value currentValue = concurrentValue.get();

        // Get incremented
        Value newValue = new Value(currentValue.getValue() + value.getValue());

        // increment
        concurrentValue.set(newValue);
        return Response.status(200).entity(newValue).build();
    }
}