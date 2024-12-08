package com.iri.jakartasecurity.web;

import jakarta.annotation.security.RolesAllowed;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author IsaacRgz
 */
@Path("service")
public class JakartaEE10Resource {

  @Context
  HttpServletRequest request;

  @Context
  private SecurityContext securityContext;

  @GET
  public Response ping() throws InterruptedException {
    String name = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Sin usuario";
    System.out.println("User Principal name: " + name + ", role user: " + securityContext.isUserInRole("user"));
    System.out.println("sleeping...");
    TimeUnit.SECONDS.sleep(1L);
    System.out.println("waking up...");
    return Response
            .ok("ping Jakarta EE")
            .build();
  }

  @GET
  @Path("protected")
  @RolesAllowed("user")
  public Response pingPost() throws InterruptedException {
    String name = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Sin usuario";
    System.out.println("User Principal name: " + name + ", role user: " + securityContext.isUserInRole("user"));
    System.out.println("sleeping...");
    TimeUnit.SECONDS.sleep(1L);
    System.out.println("waking up...");
    return Response
            .ok("ping Jakarta EE protected")
            .build();
  }

  @POST
  @Path("logout")
  @Produces(MediaType.APPLICATION_JSON)
  public Response login() throws AuthenticationException, ServletException, IOException {
    System.out.println("Starting log-out");
    if (request.getUserPrincipal() != null) {
      request.logout();
    }
    request.getSession().invalidate();
    System.out.println("Finished log-out");

    return Response
            .ok()
            .build();
  }
}
