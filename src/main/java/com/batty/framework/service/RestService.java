package com.batty.framework.service;

import com.batty.framework.service.datastore.CheckDBConnection;
import com.batty.framework.service.datastore.DatastoreImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class RestService
{
    @Autowired
    protected DatastoreImpl dbConnection;
    @GetMapping("/home/addUser/{userId}")
    public void addUser(@PathVariable  String userId) {
        dbConnection.insertData(userId);

    }
}
