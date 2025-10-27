package com.snifee.data_cdc.config;

import io.debezium.connector.postgresql.PostgresConnector;
import io.debezium.storage.jdbc.offset.JdbcOffsetBackingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DebeziumConfig {

    @Value("${spring.debezium.hostname}")
    private String hostname;

    @Value("${spring.debezium.database}")
    private String database;

    @Value("${spring.debezium.username}")
    private String username;

    @Value("${spring.debezium.password}")
    private String password;

    @Value("${spring.debezium.port}")
    private String port;

    @Value("${spring.debezium.offset-storage-jdbc-table-name}")
    private String offsetStorageJdbcTableName;

    @Bean
    public io.debezium.config.Configuration debeziumListenerConfiguration(){

        String jdbcURL = String.format("jdbc:postgresql://%s:%s/%s", hostname, port, database);

        return io.debezium.config.Configuration.create()
                .with("name","transaction-postgres-connector")
                //SOURCE DATA PROPERTIES
                .with("connector.class", PostgresConnector.class)
                .with("database.hostname", hostname)
                .with("database.port", port)
                .with("plugin.name","pgoutput")
                .with("heartbeat.interval.ms","100000")
                .with("database.user", username)
                .with("database.password", password)
                .with("database.dbname", database)
                .with("database.include.list", database)
                .with("include.schema.changes", "false")
                .with("schema.include.list","public")
                .with("table.include.list", "public.transaction_laundering")
                .with("database.server.id", "10181")
                .with("database.server.name", "transaction-postgres-db-server")
                .with("topic.prefix", "cdc_")
                //OFFSET STORAGE PROPERTIES
                .with("offset.storage", JdbcOffsetBackingStore.class)
                .with("offset.storage.jdbc.connection.url",jdbcURL)
                .with("offset.storage.jdbc.table.name", offsetStorageJdbcTableName)
                .with("offset.storage.jdbc.connection.user",username)
                .with("offset.storage.jdbc.connection.password",password)
                .with("offset.flush.interval.ms", "600000")
                //DATABASE HISTORY PROPERTIES
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", "/tmp/dbhistory.dat")
                .build();
    }
}
