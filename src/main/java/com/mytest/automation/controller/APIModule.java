package com.mytest.automation.controller;


import com.mytest.automation.restClients.PetStoreRestClient;
import com.mytest.automation.utils.Environments;
import com.mytest.automation.utils.Logs;
import io.restassured.response.Response;

import java.util.*;


/**
 * @author Rajani
 */
public class APIModule {

    public PetStoreRestClient rest;
    Logs logger = Logs.getInstance();
    private String localDate;

    private APIModule() {
    }

    public APIModule(String env) {
        Environments envProps = new Environments();
        rest = new PetStoreRestClient(envProps.getProperty(env));
    }

    public void updateHeader(String key, String value) {
        rest.updateHeader(key, value);
    }

    public void addPet(String category, String name, String tag) {
        String id = "";
        Map<String, String> params = new HashMap<>();
        params.put("category", category);
        params.put("name", name);
        params.put("tag", tag);

        Response response = rest.updatePet(params);
        if (response.getBody().asString().isEmpty()) {
            throw new RuntimeException("Response body is empty");
        } else {
            id = rest.getResponseValue(response, "category.id").toString();
            Logs.info("Pet category id: " + id);
            String pet = rest.getResponseValue(response, "name").toString();
            Logs.info("Pet name: " + pet);
        }
    }

    public void getPet(String id) {
        Response response = rest.getPet(id);
        Logs.info("Pet name: " + rest.getResponseValue(response, "name"));
        Logs.info("Status: " + rest.getResponseValue(response, "status"));
    }
}

