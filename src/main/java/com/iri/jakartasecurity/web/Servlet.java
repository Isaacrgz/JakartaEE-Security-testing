package com.iri.jakartasecurity.web;

import com.iri.jakartasecurity.auth.LoginBean;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isaac
 */
@WebServlet(name = "Servlet", urlPatterns = {"/servlet"})
public class Servlet extends HttpServlet {

  @Inject
  private LoginBean loginBean;

  @Inject
  private SecurityContext securityContext;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Sin usuario";
    System.out.println("User Principal name: " + name + ", role user: " + securityContext.isCallerInRole("user"));
    response.setContentType("text/plain;charset=UTF-8");
    response.getWriter().write("ping Servlet");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      System.out.println("Starting log-in");
      loginBean.setUsername("user");
      loginBean.setPassword("user");
      loginBean.setRememberMe(true);
      AuthenticationStatus status = loginBean.loginer(request, response);
      System.out.println("Finished log-in");
      System.out.println("User Principal name: " + request.getUserPrincipal().getName());

      response.setContentType("text/plain;charset=UTF-8");
      response.getWriter().write(status.name());
    } catch (AuthenticationException ex) {
      Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
      response.setContentType("text/plain;charset=UTF-8");
      response.getWriter().write("Failed log-in");
    }
  }

}
