package com.efimov.orchestrator.config.postgre;

import com.efimov.orchestrator.config.ApplicationProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
public class DbConfig {

    @Bean
    @Primary
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }


    @Bean
    @DependsOn("createDatabase")
    public HikariConfig hikariConfig(ApplicationProperties applicationProperties) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(applicationProperties.getHikari().getJdbcUrl());
        hikariConfig.setDriverClassName(applicationProperties.getHikari().getDriverClassName());
        hikariConfig.setUsername(applicationProperties.getDatabase().getUsername());
        hikariConfig.setPassword(applicationProperties.getDatabase().getPassword());
        return hikariConfig;
    }

    @Bean
    public DataSource hikariDataSource(ApplicationProperties applicationProperties) {
        return new HikariDataSource(hikariConfig(applicationProperties));
    }

}
