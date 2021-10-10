package com.vosouq.scoringcollector.conifg;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

//@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value(value = "${spring.data.mongodb.host}")
    private String host;

    @Value(value = "${spring.data.mongodb.port}")
    private int port;

    @Value(value = "${spring.data.mongodb.database}")
    private String name;

    @Value(value = "${spring.data.mongodb.username}")
    private String username;

    @Value(value = "${spring.data.mongodb.password}")
    private String password;


    @Override
    protected String getDatabaseName() {
        return name;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + name);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.vosouq.scoringcollector");
    }
}