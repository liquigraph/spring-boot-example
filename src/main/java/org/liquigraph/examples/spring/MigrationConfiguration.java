package org.liquigraph.examples.spring;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.liquigraph.core.api.Liquigraph;
import org.liquigraph.core.configuration.ConfigurationBuilder;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class MigrationConfiguration {

    @Bean
    public MethodInvokingFactoryBean liquigraph(org.liquigraph.core.configuration.Configuration liquigraphConfig) {
        MethodInvokingFactoryBean method = new MethodInvokingFactoryBean();
        method.setTargetObject(new Liquigraph());
        method.setTargetMethod("runMigrations");
        method.setArguments(new Object[] {liquigraphConfig});
        return method;
    }

    @Bean
    public org.liquigraph.core.configuration.Configuration configuration(DataSource dataSource) {
        return new ConfigurationBuilder()
                .withDataSource(dataSource)
                .withMasterChangelogLocation("changelog.xml")
                .withRunMode()
                .build();
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig configuration = new HikariConfig("/datasource.properties");
        return new HikariDataSource(configuration);
    }


}
