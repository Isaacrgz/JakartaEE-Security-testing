package com.iri.jakartasecurity.auth;

import jakarta.enterprise.inject.Model;
import jakarta.faces.application.FacesMessage;
import static jakarta.faces.application.FacesMessage.SEVERITY_ERROR;
import jakarta.faces.context.FacesContext;
import jakarta.inject.*;
import jakarta.security.enterprise.*;
import static jakarta.security.enterprise.AuthenticationStatus.*;
import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import jakarta.security.enterprise.credential.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;

/**
 *
 * @author IsaacRgz
 */
@Model
public class LoginBean {

  @Inject
  private SecurityContext securityContext;

  @NotNull
  private String username;

  @NotNull
  private String password;

  private boolean rememberMe = false;

  private boolean continued = true;

  /**
   * Log-in through login.xhtml [Not support in this moment].
   *
   * @throws AuthenticationException
   * @throws ServletException
   * @throws IOException
   */
  public void loginer() throws AuthenticationException, ServletException, IOException {
    if (getUsername() != null && getPassword() != null) {
      FacesContext context = FacesContext.getCurrentInstance();
      Credential credential = new UsernamePasswordCredential(this.username, new Password(this.password));
      AuthenticationStatus status = securityContext.authenticate(
              getRequest(context),
              getResponse(context),
              withParams()
                      .credential(credential)
                      .newAuthentication(!continued)
                      .rememberMe(rememberMe)
      );
      System.out.println(status);
      switch (status) {
        case SEND_CONTINUE -> {
          context.responseComplete();
        }
        case SUCCESS -> {
          context.responseComplete();
          getResponse(context).setCharacterEncoding("UTF-8");
          getResponse(context).sendRedirect(getRequest(context).getContextPath() + "/" + "modulos?home");
        }
        case SEND_FAILURE ->
          addError(context, "Authentication failed");
      }
    }
  }

  /**
   * Log-in through any logical business.
   *
   * @param request
   * @param response
   * @return AuthenticationStatus
   * @throws AuthenticationException
   * @throws ServletException
   * @throws IOException
   */
  public AuthenticationStatus loginer(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, ServletException, IOException {
    if (getUsername() != null && getPassword() != null) {
      Credential credential = new UsernamePasswordCredential(this.username, new Password(this.password));
      AuthenticationStatus status = securityContext.authenticate(
              request,
              response,
              withParams()
                      .credential(credential)
                      .newAuthentication(!continued)
                      .rememberMe(rememberMe)
      );
      System.out.println("authentication result: " + status);
      return status;
    }
      return AuthenticationStatus.NOT_DONE;
  }

  private HttpServletRequest getRequest(FacesContext context) {
    return (HttpServletRequest) context
            .getExternalContext()
            .getRequest();
  }

  private HttpServletResponse getResponse(FacesContext context) {
    return (HttpServletResponse) context
            .getExternalContext()
            .getResponse();
  }

  private static void addError(FacesContext context, String message) {
    context.addMessage(null, new FacesMessage(SEVERITY_ERROR, message, null));
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isRememberMe() {
    return rememberMe;
  }

  public void setRememberMe(boolean rememberMe) {
    this.rememberMe = rememberMe;
  }

  public boolean isContinued() {
    return continued;
  }

  public void setContinued(boolean continued) {
    this.continued = continued;
  }

}
