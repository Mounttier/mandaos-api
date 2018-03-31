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
        String url = System.getenv("MANDAOS_POSTGRESQL_URL");
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(url);

        return SimpleJDBC.from(ds);
    }
}
