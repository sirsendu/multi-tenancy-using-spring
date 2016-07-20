package com.sirsendu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Sirsendu Konar
 *
 */
@Configuration
public class MultitenantConfiguration {

    @Autowired
    private DataSourceProperties properties;

    /**
     * Defines the data source for the application
     * 
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        final File[] files = Paths.get("allTenants").toFile().listFiles();
        final Map<Object, Object> resolvedDataSources = new HashMap<>();

        for (final File propertyFile : files) {
            final Properties tenantProperties = new Properties();
            final DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());

            try {
                tenantProperties.load(new FileInputStream(propertyFile));

                final String tenantId = tenantProperties.getProperty("name");

                dataSourceBuilder.driverClassName(this.properties.getDriverClassName())
                        .url(tenantProperties.getProperty("datasource.url"))
                        .username(tenantProperties.getProperty("datasource.username"))
                        .password(tenantProperties.getProperty("datasource.password"));

                if (this.properties.getType() != null) {
                    dataSourceBuilder.type(this.properties.getType());
                }

                resolvedDataSources.put(tenantId, dataSourceBuilder.build());
            } catch (final IOException e) {
                e.printStackTrace();

                return null;
            }
        }

        // Now Create the multi-tenant source.
        // It needs a default database to connect to by default. Its good to have it
        // actually an empty tenant database.
        // Please donot use that for a regular tenant if you want things to be safe!
        final MultitenantDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(defaultDataSource());
        dataSource.setTargetDataSources(resolvedDataSources);

        // Call this to finalize the initialization of the data source.
        dataSource.afterPropertiesSet();

        return dataSource;
    }

    /**
     * Creates the default data source for the application
     * 
     * @return
     */
    private DataSource defaultDataSource() {
        final DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
                .driverClassName(this.properties.getDriverClassName()).url(this.properties.getUrl())
                .username(this.properties.getUsername()).password(this.properties.getPassword());

        if (this.properties.getType() != null) {
            dataSourceBuilder.type(this.properties.getType());
        }

        return dataSourceBuilder.build();
    }
}
