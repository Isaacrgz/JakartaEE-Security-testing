package com.iri.jakartasecurity.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.CallerOnlyCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;
import jakarta.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author IsaacRgz
 */
@ApplicationScoped
public class CustomIdentityStore implements IdentityStore {

  @Override
  public CredentialValidationResult validate(Credential credential) {
    if (credential instanceof UsernamePasswordCredential) {
      String username = ((UsernamePasswordCredential) credential).getCaller();
      String password = ((UsernamePasswordCredential) credential).getPasswordAsString();
      return new CredentialValidationResult(username, new HashSet<>(Arrays.asList("user")));
    }
    if (credential instanceof CallerOnlyCredential) {
      String username = ((CallerOnlyCredential) credential).getCaller();
      return new CredentialValidationResult(username, new HashSet<>(Arrays.asList("user")));
    }
    return NOT_VALIDATED_RESULT;
  }

  @Override
  public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
    return validationResult.getCallerGroups();
  }

}
