package com.snifee.data_cdc.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
//@EnableReactiveMongoRepositories(
//        basePackages = "com.snifee.data_cdc.mongodb.repository"
//)
@EnableMongoRepositories(
        basePackages = "com.snifee.data_cdc.mongodb.repository"
)
public class MongoDBConfig {

    @Value(value = "${spring.mongodb.uri}")
    private String mongoUri;

    @Bean
    @Primary
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "playground"); // Replace with your database name
    }


}
