package com.unico.auth;

import io.dropwizard.auth.Authorizer;

public class UnicoAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User principal, String role) {
        return principal.getRoles() != null &&
                principal.getRoles().contains(role);
    }
}
