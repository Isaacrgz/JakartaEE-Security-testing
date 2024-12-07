package com.iri.jakartasecurity.web;

import com.iri.jakartasecurity.auth.LoginBean;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;

/**
 *
 * @author IsaacRgz
 */
@Path("service")
public class JakartaEE10Resource {

  @Context
  HttpServletRequest request;
  @Context
  HttpServletResponse response;

  @GET
  @Path("ping")
  public Response ping() {
    return Response
            .ok("ping Jakarta EE")
            .build();
  }

  @POST
  @Path("ping")
  public Response pingPost() {
    return Response
            .ok("ping Jakarta EE")
            .build();
  }

  @POST
  @Path("login")
  @Produces(MediaType.APPLICATION_JSON)
  public Response login() throws AuthenticationException, ServletException, IOException {
    System.out.println("Starting log-in");
    LoginBean loginBean = new LoginBean();
    loginBean.setUsername("user");
    loginBean.setPassword("user");
    AuthenticationStatus status = loginBean.loginer(request, response);
    System.out.println("Finished log-in");
    
    return Response
            .ok(status)
            .build();
  }
}
