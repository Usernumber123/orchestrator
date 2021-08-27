package com.efimov.orchestrator.config.postgre;

import com.efimov.orchestrator.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Component
public class CreateDatabase {

    private static final String DATABASE_IS_ALREADY_EXIST = "42P04";

    private String dbName;
    private String username;
    private String password;
    private String databaseUrl;

    public CreateDatabase(ApplicationProperties applicationProperties) {
        this.dbName = applicationProperties.getDatabase().getDatabaseName();
        this.username = applicationProperties.getDatabase().getUsername();
        this.password = applicationProperties.getDatabase().getPassword();
        this.databaseUrl = applicationProperties.getDatabase().getUrl();
    }

    @PostConstruct
    public void init() {
        createDatabase(dbName);
    }

    private void createDatabase(String databaseName) {
        try {
            Connection connection = DriverManager.getConnection(databaseUrl, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE " + databaseName);

            log.warn("Database {} created successfully", databaseName);
        } catch (SQLException e) {
            if (DATABASE_IS_ALREADY_EXIST.equals(e.getSQLState())) {
                log.info("Database {} is already exist", databaseName);
            } else {
                log.error("Unable to create {} database", databaseName);
            }
        }
    }
}