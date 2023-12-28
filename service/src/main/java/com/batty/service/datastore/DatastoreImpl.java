package com.batty.service.datastore;


import com.batty.framework.interfaces.DatastoreInterface;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.batty.framework.datastore.DatabaseHandler;
import com.batty.framework.datastore.DatastoreUtil;
import java.util.concurrent.TimeUnit;

@Component("ServiceDataStoreImpl")
public class DatastoreImpl implements DatastoreInterface {

    protected Logger log = LoggerFactory.getLogger(DatastoreImpl.class);

    @Autowired
    protected DatabaseHandler datastore;

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
            index.put("name",1);
            this.datastore.createIndex(index, this.utils.getOptions().unique(true));
            index.clear();
            index.put("lastModifiedTimeStamp",1);
            this.datastore.createIndex(index, this.utils.getOptions().expireAfter(15L, TimeUnit.SECONDS));
        }
        catch(Exception e)
        {

        }
    }

    public boolean insertData(String userId) {

        try
        {
            Document doc = new Document();
            doc.put("name",userId);
            return this.datastore.insertOne(doc) ;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public Object findUser(String userId) {
        Object status = null;
        try
        {
            Document doc = new Document();
            doc.put("name",userId);
            status =  this.datastore.findOne(doc);
        }
        catch(Exception e) {
            status = "empty";
        }
        finally {
            return status;
        }

    }
}
