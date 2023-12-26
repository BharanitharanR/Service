package com.batty.framework.service.datastore;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.stereotype.Component;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Component
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class CheckDBConnection {
    protected Logger log = LoggerFactory.getLogger(CheckDBConnection.class);
    private boolean isDBConnected;

    protected MongoClient client;

    protected  MongoDatabase database;

    @Value("${mongodb.atlas.connection}")
    public String dbConnectionString;

    @Value("${mongodb.database.name}")
    public String dbName;

    @Value("${mongodb.collection.name}")
    public String collectionName;


    @PostConstruct
    public void initialize()
    {
        log.info("Intialized + db string"+ dbConnectionString);
        this.connectToDB();

    }
    public  boolean isDataStoreConnected()
    {
        return this.isDBConnected;
    }

    private boolean createCollection() {
        boolean returnStatus = false;
        try
        {
            this.database.createCollection(collectionName);
            returnStatus = true;

        }
        catch(Exception e)
        {
            log.info("Exception : "+e);
            returnStatus = false;
        }
        finally {
            return returnStatus;
        }
    }

    private void connectToDB()
    {
        try {
            this.client = MongoClients.create(dbConnectionString);
            this.database  = this.client.getDatabase(dbName);
            //  attempt a create collection
            log.info("Collection status: " +  ( (createCollection()) ? "Collection created":"Collection exists"));
            // MongoCollection<Document> collection = this.client.getDatabase(dbName).getCollection(collectionName);

        }
        catch(Exception ignored) {
            log.info("Connection error" + ignored.getMessage());
        }
/*            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");
            Document doc = collection.find(eq("title", "Back to the Future")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }*/


    }


}
