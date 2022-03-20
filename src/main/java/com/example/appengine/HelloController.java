package com.example.appengine;

import com.google.appengine.api.datastore.*;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import java.io.PrintWriter;
import java.io.StringWriter;

@Controller("/")
public class HelloController {
    private final DatastoreService ds;

    public HelloController() {
        ds = DatastoreServiceFactory.getDatastoreService();
    }

    @Get("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String index(String id) {
        try {
            Entity gaeando = ds.get(KeyFactory.createKey("gaeando", id));

            if (gaeando == null) {
                return "ERROR";
            }

            return (String) gaeando.getProperty("message");
        } catch (Throwable e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);

            return "BLA: " + stringWriter;
        }
    }

    @Get("/pqp2/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String save(String id) {
        try {
            Entity gaeando = new Entity("gaeando", id);
            gaeando.setProperty("message", id);
            Key gaeandoKey = ds.put(gaeando);

            if (gaeandoKey == null) {
                return "ERROR";
            }

            return String.format("Saved: [ " +
                            "Namespace: %s" +
                            "Kind: %s,  " +
                            "Name: %s" +
                            " ]",
                    gaeandoKey.getNamespace(),
                    gaeandoKey.getKind(),
                    gaeandoKey.getName());
        } catch (Throwable e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);

            return "BLA: " + stringWriter;
        }
    }
}
