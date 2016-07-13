package io.elide.geoloc.application;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import io.elide.geoloc.models.User;

public class GeolocSecurityContext implements SecurityContext {

    private User user;

    public GeolocSecurityContext(User user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
