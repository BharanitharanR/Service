package com.batty.service.datastore;


import com.batty.framework.interfaces.DatastoreInterface;
import com.batty.service.model.ServiceCollection;
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

    public boolean insertData(Document doc) {

        try
        {
            // Document doc = new Document();
            // doc.put("name",userId);
            return this.datastore.insertOne(doc) ;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public ServiceCollection findUser(String userId)
    {
        Object status = null;
        ServiceCollection coll = new ServiceCollection();
        try
        {
            Document doc = new Document();
            doc.put("userId",userId);
            //doc.put("name",userId);
            coll = this.datastore.findOne(doc,ServiceCollection.class);
            log.info("Data from DB: "+coll.toString());
            return coll;

        }
        catch(Exception e)
        {
            log.info("Error while fetch"+ e);
            status = "empty";
            return null;
        }

    }
}
