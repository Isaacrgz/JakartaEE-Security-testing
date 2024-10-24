package com.iri.jakartasecurity.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Isaac
 */
@Path("service")
public class JakartaEE10Resource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    @POST
    public Response pingPost(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}
