package io.elide.geoloc.application;

import com.yahoo.elide.contrib.dropwizard.elide.ElideBundle;
import com.yahoo.elide.core.DataStore;
import com.yahoo.elide.datastores.hibernate5.HibernateStore;
import com.yahoo.elide.resources.JsonApiEndpoint;

import org.hibernate.SessionFactory;

import javax.ws.rs.core.SecurityContext;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.setup.Environment;

public class GeolocElideBundle extends ElideBundle<GeolocConfiguration> {

    private DataStore dataStore;
    private SessionFactory sessionFactory;

    public GeolocElideBundle(Class<?> entity, Class<?>... entities) {
        super(entity, entities);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(GeolocConfiguration geolocConfiguration) {
        return geolocConfiguration.getDatabase();
    }

    @Override
    public JsonApiEndpoint.DefaultOpaqueUserFunction getUserFn(GeolocConfiguration configuration,
                                                               Environment environment) {
        return SecurityContext::getUserPrincipal;
    }

    @Override
    public DataStore getDataStore(GeolocConfiguration configuration, Environment environment) {
        if (dataStore == null) {
            PooledDataSourceFactory dbConfig = getDataSourceFactory(configuration);
            sessionFactory = getSessionFactoryFactory().build(this, environment, dbConfig, getEntities(), name());
            dataStore = new HibernateStore(sessionFactory);
        }

        return dataStore;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
