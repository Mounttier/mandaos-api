package com.smontiel.mandaos_api;

import com.smontiel.simple_jdbc.SimpleJDBC;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Salvador Montiel on 22/mar/2018.
 */
@Configuration
public class AppConfiguration {

    @Bean
    public SimpleJDBC simpleJDBC() {
        String jdbcUrl = System.getenv("MANDAOS_POSTGRESQL_URL");
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(jdbcUrl);

        return SimpleJDBC.from(hikariDataSource);
    }
}
