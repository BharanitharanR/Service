package com.batty.framework.service.datastore;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.InsertOneResult;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.stereotype.Component;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Filter;

import static com.mongodb.client.model.Filters.eq;

@Component
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class CheckDBConnection {
    protected Logger log = LoggerFactory.getLogger(CheckDBConnection.class);
    private boolean isDBConnected;

    protected MongoClient client;

    protected  MongoDatabase database;

    protected MongoCollection collection;

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
            // Bson schema = eq("userId","String");

           // Specify validation options (optional)
             // ValidationOptions options = new ValidationOptions().validator(schema);

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
            this.collection = this.database.getCollection(collectionName);
            setupCollectionIndexes();

        }
        catch(Exception ignored) {
            log.info("Connection error" + ignored.getMessage());
        }
    }

    /* @javadoc
    *   creates a indexes for this collection
    */
    private void setupCollectionIndexes() {
        try
        {
            // Bson index = eq("userId", 1);
            Document index = new Document();
            index.put("userId",1);
            // index.append("lastModifiedDate",1);
            IndexOptions indexOptions = new IndexOptions().expireAfter(60L, TimeUnit.SECONDS).unique(true);
            String indexCreated = this.collection.createIndex(index, indexOptions);
            index.clear();
            index.put("lastModifiedTimeStamp",1);
            indexOptions = new IndexOptions();
            indexCreated = this.collection.createIndex(index, indexOptions);
            log.info("Index created :"+indexCreated);
        }
        catch(Exception e)
        {
            log.info("failed to create index:"+ e);
        }


    }

    public void insertData(String userId) {
        try
        {
            Document doc = new Document();
            doc.put("userId",userId);
            InsertOneResult result = this.insertOne(doc);
            log.info( "data inserted :"+result.wasAcknowledged());
        }
        catch(Exception e) {
            e.printStackTrace();
            // log.info("Insert exception :"+ e.);
        }

    }

    public InsertOneResult insertOne(Document doc)
    {
        InsertOneResult result = null;
        try {
            doc.append("lastModifiedTimeStamp", new Date());
            result = this.collection.insertOne(doc);
        }
        catch(Exception ignored)
        {

        }
        finally
        {
            return result;
        }
    }

}
