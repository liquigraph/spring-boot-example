package org.liquigraph.examples.spring;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class MigrationConfiguration {

    @Bean
    public DataSource dataSource() {
        HikariConfig configuration = new HikariConfig("/datasource.properties");
        return new HikariDataSource(configuration);
    }
}
