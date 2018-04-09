package com.smontiel.mandaos_api;

import com.smontiel.simple_jdbc.SimpleJDBC;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Salvador Montiel on 22/mar/2018.
 */
@Configuration
public class AppConfiguration {

	@Bean
	public HikariDataSource dataSource() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("MANDAOS_POSTGRESQL_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);

        return new HikariDataSource(config);
	}

    @Bean
    public SimpleJDBC simpleJDBC(HikariDataSource hikariDataSource) {
    	/*URI dbUri = new URI(System.getenv("MANDAOS_POSTGRESQL_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);

        HikariDataSource hikariDataSource = new HikariDataSource(config);*/

        return SimpleJDBC.from(dataSource());
    }
}
