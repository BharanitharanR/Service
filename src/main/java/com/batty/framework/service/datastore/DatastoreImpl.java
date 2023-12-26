package com.batty.framework.service.datastore;

import com.batty.framework.service.interfaces.DatastoreInterface;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.result.InsertOneResult;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DatastoreImpl implements DatastoreInterface {

    protected Logger log = LoggerFactory.getLogger(DatastoreImpl.class);

    @Autowired
    protected CheckDBConnection datastore;

    @Autowired
    protected DatastoreUtil utils;

    @PostConstruct
    public void initialize()
    {
        createIndex();
    }


    @Override
    public void createIndex() {
        try
        {
            Document index = new Document();
            index.put("userId",1);
            this.datastore.createIndex(index, this.utils.getOptions().unique(true));
            index.clear();
            index.put("lastModifiedTimeStamp",1);
            this.datastore.createIndex(index, this.utils.getOptions().expireAfter(15L, TimeUnit.SECONDS));
        }
        catch(Exception e)
        {

        }
    }

    public void insertData(String userId) {
        try
        {
            Document doc = new Document();
            doc.put("userId",userId);
            this.datastore.insertOne(doc);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}
