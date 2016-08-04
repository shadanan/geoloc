package io.elide.geoloc.application;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.DatabaseConfiguration;

import java.util.Map;

public class GeolocConfiguration extends Configuration {
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDatabase() {
        Map<String, String> properites = database.getProperties();

        DatabaseConfiguration databaseConfiguration =
                HerokuDatabaseConfiguration.create(System.getenv("DATABASE_URL"), properites);
        database = (DataSourceFactory) databaseConfiguration.getDataSourceFactory(null);
        return database;
    }

    @JsonProperty("database")
    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }
}
