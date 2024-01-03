package com.batty.service;

import com.batty.framework.interfaces.RegisterServiceInterface;
import com.batty.registry.api.DefaultApi;
import com.batty.registry.api.client.ApiClient;
import com.batty.registry.api.client.ApiException;
import com.batty.registry.api.model.ServiceSchema;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ServiceRegistry")
public class RegisterService implements RegisterServiceInterface {

    protected DefaultApi service;

    @PostConstruct
    public void register()
    {

        com.batty.registry.api.client.Configuration.setDefaultApiClient(new ApiClient().setBasePath("http://localhost:8080"));
        this.service = new DefaultApi();
        registerService(true);
    }

    @PreDestroy
    public void unregister()
    {
        registerService(false);
    }

    @Override
    public void registerService(boolean value) {
        ServiceSchema schema = new ServiceSchema();
        schema.setServiceId("service");
        schema.setServiceName("service");
        schema.setServiceHostIP("localhost:8081");
        if( value ) { schema.setStatus("Ok"); } else { schema.setStatus("Off"); };
        try {
            this.service.addService("service",schema);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
}
