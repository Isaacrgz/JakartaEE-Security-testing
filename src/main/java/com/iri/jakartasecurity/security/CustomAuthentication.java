package com.iri.jakartasecurity.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author IsaacRgz
 */
@AutoApplySession
@ApplicationScoped
public class CustomAuthentication implements HttpAuthenticationMechanism {

  @Inject
  private IdentityStore identityStore;

  @Override
  public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
    Credential credential = context.getAuthParameters().getCredential();
    if (credential != null) {
      CredentialValidationResult s = identityStore.validate(credential);
      return context.notifyContainerAboutLogin(s);
    } else {
      return context.doNothing();
    }
  }

}
