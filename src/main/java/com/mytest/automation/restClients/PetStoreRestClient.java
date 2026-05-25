package com.mytest.automation.restClients;


import com.mytest.automation.restTargets.TargetRest;
import com.mytest.automation.utils.Headers;
import com.mytest.automation.utils.Logs;
import com.mytest.automation.utils.RestClient;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.post;

/**
 * @author Rajani
 */
public class PetStoreRestClient extends RestClient {

    public PetStoreRestClient(String URI) {
        super(URI);
        headers.putAll(new Headers().getAsMap());
    }

    public void updateHeader(String key, String val) {
        headers.put(key, val);
        Logs.info("Updated Key: " + key + " value: " + val + " in header");
    }

    public Response updatePet(Map<String, String> map) {
        Response response = post(TargetRest.TargetPetAPI.getPet(), "PetStore/addPet.json", map);
        Logs.info("Response body received: " + response.getBody().asString());
        Logs.info("Response status code received: " + response.getStatusCode());
        assertStatusCode(response, "200");
        return response;
    }

    public Response getPet(String id) {
        Response response = get(TargetRest.TargetPetAPI.getPet() + "/" + id);
        Logs.info("Response body received: " + response.getBody().asString());
        Logs.info("Response status code received: " + response.getStatusCode());
        assertStatusCode(response, "200");
        return response;

    }
}
