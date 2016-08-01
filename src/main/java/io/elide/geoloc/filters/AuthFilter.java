package io.elide.geoloc.filters;

import org.hibernate.Session;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

import io.elide.geoloc.application.GeolocElideBundle;
import io.elide.geoloc.application.GeolocSecurityContext;
import io.elide.geoloc.models.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthFilter implements ContainerRequestFilter {

    private final GeolocElideBundle bundle;

    @Inject
    public AuthFilter(GeolocElideBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MultivaluedMap<String, String> headers = requestContext.getHeaders();

        String userId = headers.getFirst("X-User-Id");
        String userPassword = headers.getFirst("X-User-Password");

        log.info("User: {}, Password: {}", userId, userPassword);

        if(null != userPassword && null != userId) {
            try (Session session = bundle.getSessionFactory().openSession()) {
                User user = session.get(User.class, Long.parseLong(userId));
                if (userPassword.equals(user.getPassword())) {
                    requestContext.setSecurityContext(new GeolocSecurityContext(user));
                    log.info("User: {}", String.valueOf(user));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            requestContext.setSecurityContext(new GeolocSecurityContext(null));
        }
    }
}
