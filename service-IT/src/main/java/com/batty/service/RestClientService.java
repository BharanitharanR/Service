package com.batty.service;


import com.batty.service.api.DefaultApi;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Component("ServiceITController")
@RestController
public class RestClientService
{



  // https://mydeveloperplanet.com/2022/02/08/generate-server-code-using-openapi-generator/

  // https://github.com/mydeveloperplanet/myopenapiplanet/tree/master

    @GetMapping("/it/userId/{userId}")
    public String addUser(@PathVariable String userId) {
        try {
            DefaultApi api  = new DefaultApi();
            api.addUser("bharani");
            return "success";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "error";
        }

    }
}
