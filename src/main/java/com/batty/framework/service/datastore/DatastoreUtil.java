package com.batty.framework.service.datastore;

import com.batty.framework.service.interfaces.DatastoreInterface;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatastoreUtil {
    protected Logger log = LoggerFactory.getLogger(DatastoreUtil.class);

    protected IndexOptions idx;

    public IndexOptions getOptions()
    {
        this.idx = new IndexOptions();
        return this.idx;

    }
}
