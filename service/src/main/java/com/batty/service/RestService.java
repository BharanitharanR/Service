package com.batty.service;

import com.batty.service.api.HikeListApi;
import com.batty.service.datastore.DatastoreImpl;
import com.batty.service.model.RTPHikers;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Component("ServiceController")
@RestController
public class RestService implements HikeListApi
{
    @Autowired
    protected DatastoreImpl dbConnection;
  // https://mydeveloperplanet.com/2022/02/08/generate-server-code-using-openapi-generator/

  // https://github.com/mydeveloperplanet/myopenapiplanet/tree/master
    @Override
    public ResponseEntity<RTPHikers> addUser(String userId) {
        try {
            RTPHikers response = new RTPHikers();
            Document servicecollection =  new Document();
            servicecollection.put("userId",userId);
            servicecollection.put("name",userId);
            if (dbConnection.insertData(servicecollection)) {
                response.setUserID(userId);
                response.setName(userId);
                return ResponseEntity.ok(response);
            } else {
                throw new RuntimeException();
            }
        }
        catch(Exception e)
        {
            return (ResponseEntity<RTPHikers>) ResponseEntity.internalServerError();
        }

    }


    public Object findUser(@PathVariable  String userId) {
        return dbConnection.findUser(userId);

    }

    @Override
    public ResponseEntity<RTPHikers> getUser(String userId) {
        RTPHikers response = new RTPHikers();
        String data = dbConnection.findUser(userId).getUserId();
        response.setUserID(data);
        response.setName(data);

        return ResponseEntity.ok(response);
    }
}
