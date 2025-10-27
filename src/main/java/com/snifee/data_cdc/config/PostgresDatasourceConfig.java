package com.snifee.data_cdc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;


@Configuration
public class PostgresDatasourceConfig {


    @Value(value = "${spring.datasource.jdbc-url}")
    private String jdbcUrl;

    @Bean(name = "postgresDatasource")
    public DataSource postgresDatasource()  {
        return DataSourceBuilder
                .create()
                .url(jdbcUrl)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(postgresDatasource());
    }
}
