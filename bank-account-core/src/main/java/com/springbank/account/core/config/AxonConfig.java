package com.springbank.account.core.config;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.thoughtworks.xstream.XStream;
import java.util.Collections;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoSettingsFactory;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class AxonConfig {

    @Value("${spring.data.mongodb.host:127.0.0.1}")
    private String mongoHost;
    @Value("${spring.data.mongodb.database:bank}")
    private String mongoDatabase;
    @Value("${spring.data.mongodb.port:27017}")
    private int mongoPort;

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[] { "**" });
        return xStream;
    }

    @Bean
    public MongoClient mongo() {
        MongoFactory mongoFactory = new MongoFactory();
        MongoSettingsFactory mongoSettingsFactory = new MongoSettingsFactory();
        mongoSettingsFactory.setMongoAddresses(Collections.singletonList(new ServerAddress(mongoHost, mongoPort)));
        mongoFactory.setMongoClientSettings(mongoSettingsFactory.createMongoClientSettings());
        return mongoFactory.createMongo();
    }

    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder()
                .mongoDatabase(mongo(), mongoDatabase)
                .build();
    }

    @Bean
    public TokenStore tokenStore(Serializer serializer) {
        return MongoTokenStore.builder()
                .mongoTemplate(axonMongoTemplate())
                .serializer(serializer)
                .build();
    }

    @Bean
    public EventStorageEngine storageEngine(MongoClient client) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(DefaultMongoTemplate.builder()
                        .mongoDatabase(client)
                        .build())
                .build();
    }

    @Bean
    //    AxonConfiguration is deprecated
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine
            //, AxonConfiguration axonConfiguration
    ) {

        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                //.messageMonitor(axonConfiguration.messageMonitor(EventStore.class, "eventStore"))
                .build();

    }

}
