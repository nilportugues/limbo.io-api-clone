package io.bandit.limbo.limbo.infrastructure.liquibase;


import io.bandit.limbo.limbo.infrastructure.Constants;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("io.bandit.limbo.limbo.modules.main.*.infrastructure.jpa")
@EnableJpaAuditing
@EnableTransactionManagement
@EnableElasticsearchRepositories("io.bandit.limbo.limbo.modules.main.*.infrastructure.elasticsearch")
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Inject
    private Environment env;

    

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {

        // Use liquibase.integration.spring.SpringLiquibase if you don't want Liquibase to start asynchronously
        SpringLiquibase liquibase = new AsyncSpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());

        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        if (env.acceptsProfiles(Constants.PRODUCTION)) {
            liquibase.setShouldRun(false);
        }
        return liquibase;
    }

    @Bean
    public Hibernate4Module hibernate4Module() {
        return new Hibernate4Module();
    }
}
