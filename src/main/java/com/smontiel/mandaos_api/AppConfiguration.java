package com.smontiel.mandaos_api;

import com.smontiel.simple_jdbc.SimpleJDBC;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Salvador Montiel on 22/mar/2018.
 */
@Configuration
public class AppConfiguration {

    @Bean
    public SimpleJDBC simpleJDBC() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setDatabaseName("mandaos");
        ds.setPassword("6283");
        ds.setUser("postgres");
        ds.setPortNumber(5432);

        return SimpleJDBC.from(ds);
    }
}
