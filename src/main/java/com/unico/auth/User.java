package com.unico.auth;

import java.security.Principal;
import java.util.Set;

public class User implements Principal {
    private String name;
    private Set<String> roles;

    public User(String name, Set<String> roles) {
        this.name = name;
        this.roles = roles;
    }

    public User(String name) {
        this.name = name;
    }

    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
