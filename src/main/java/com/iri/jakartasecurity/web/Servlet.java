package com.iri.jakartasecurity.web;

import com.iri.jakartasecurity.auth.LoginBean;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
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

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/plain;charset=UTF-8");
    response.getWriter().write("ping Servlet");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      System.out.println("Starting log-in");
      LoginBean loginBean = new LoginBean();
      loginBean.setUsername("user");
      loginBean.setPassword("user");
      AuthenticationStatus status = loginBean.loginer(request, response);
      System.out.println("Finished log-in");
      
      response.setContentType("text/plain;charset=UTF-8");
      response.getWriter().write(status.name());
    } catch (AuthenticationException ex) {
      Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);response.setContentType("text/plain;charset=UTF-8");
      response.getWriter().write("Failed log-in");
    }
  }
  
}
