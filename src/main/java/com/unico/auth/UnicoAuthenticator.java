package com.unico.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.*;

import static java.util.Collections.singleton;

public class UnicoAuthenticator implements Authenticator<BasicCredentials, User> {
    //Emulates de user database
    private static final Map<String, Set<String>> USERS_MAP ;
    private static final String DEFAULT_PASSWORD = "123456";
    static {
        Set<String> executorRoles = singleton("EXECUTOR");
        Set<String> readerRoles = singleton("READER");
        Set<String> adminRoles = new HashSet<>(executorRoles);
        adminRoles.addAll(readerRoles);
        USERS_MAP = new HashMap<>();
        USERS_MAP.put("executor",
              executorRoles);
        USERS_MAP.put("reader",
              readerRoles);
        USERS_MAP.put("admin",
              adminRoles);
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (isValidUser(credentials)) {
            return Optional.of(
                    new User(credentials.getUsername(),
                            getRolesFor(credentials.getUsername())));
        }
        return Optional.empty();
    }

    private boolean isValidUser(BasicCredentials credentials) {
        return USERS_MAP.containsKey(credentials.getUsername()) &&
        DEFAULT_PASSWORD.equals(credentials.getPassword());
    }

    private Set<String> getRolesFor(String userName) {
        return USERS_MAP.get(userName);
    }
}
