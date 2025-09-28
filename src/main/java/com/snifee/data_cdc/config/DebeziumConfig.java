package com.snifee.data_cdc.config;

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

    @Bean
    public io.debezium.config.Configuration transactionConnector(){


//        File offsetStorageTempFile = new File("offsets_.dat");

        return io.debezium.config.Configuration.create()
                .with("name","transaction-postgres-connector")
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("offset.storage", "io.debezium.storage.jdbc.offset.JdbcOffsetBackingStore")
                .with("offset.storage.jdbc.connection.url","jdbc:postgresql://localhost:5432/demo_batch")
                .with("offset.storage.jdbc.table.name", "offset_store")
                .with("offset.storage.jdbc.connection.user","postgres")
                .with("offset.storage.jdbc.connection.password","postgres")
//                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
                .with("offset.flush.interval.ms", "600000")
                .with("database.hostname", hostname)
                .with("database.port", port)
                .with("plugin.name","pgoutput")
//                .with("heartbeat.interval.ms","1000")
                .with("database.user", username)
                .with("database.password", password)
                .with("database.dbname", database)
                .with("database.include.list", database)
                .with("include.schema.changes", "false")
                .with("schema.include.list","public")
                .with("table.include.list", "public.transaction_laundering")
                .with("database.server.id", "10181")
                .with("database.server.name", "transaction-postgres-db-server")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", "/tmp/dbhistory.dat")
                .with("topic.prefix", "cdc_")
                .build();
    }


}
