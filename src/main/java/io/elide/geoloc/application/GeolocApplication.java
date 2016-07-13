package io.elide.geoloc.application;

import com.yahoo.elide.resources.JsonApiEndpoint;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.elide.geoloc.filters.AuthFilter;
import io.elide.geoloc.models.Location;
import io.elide.geoloc.models.User;

public class GeolocApplication extends Application<GeolocConfiguration> {
    private final GeolocElideBundle geolocBundle;

    public GeolocApplication() {
        geolocBundle = new GeolocElideBundle(User.class, Location.class);
    }

    @Override
    public void initialize(Bootstrap<GeolocConfiguration> bootstrap) {
        bootstrap.addBundle(geolocBundle);
    }

    public void run(GeolocConfiguration geolocConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(geolocBundle).to(GeolocElideBundle.class);
            }
        });

        environment.jersey().register(AuthFilter.class);
        environment.jersey().register(JsonApiEndpoint.class);
    }

    public static void main(String[] args) throws Exception {
        new GeolocApplication().run(args);
    }
}
