package com.example.appengine;

import com.google.appengine.api.datastore.*;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/")
public class HelloController {
    private final DatastoreService ds;

    public HelloController() {
        ds = DatastoreServiceFactory.getDatastoreService();
    }

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        Entity greeting = new Entity("Greeting");
        greeting.setProperty("content", "Hello World! 321");

        Key key = ds.put(greeting);

        try {
            if (ds.get(key) != null) {
                return (String) ds.get(key).getProperty("content");
            }

            return "PQP2";
        } catch (EntityNotFoundException e) {
            return "PQP";
        }
    }
}
