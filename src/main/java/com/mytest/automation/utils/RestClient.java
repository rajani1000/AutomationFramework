package com.mytest.automation.utils;


import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rajani
 */
public class RestClient {
    private String originalURI;
    public Map<String, String> headers;
    Logs logger = Logs.getInstance();
    String resourcePath = "src/main/resources/Json/";

    public RestClient(String URI) {
        this.originalURI = URI;
        this.headers = new HashMap<>();
        RestAssured.config = RestAssuredConfig.config()
                .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
        Logs.info("SSL certificate verification disabled for testing");
    }


    public Response get(String target) {
        RestAssured.baseURI = this.originalURI;
        Logs.info(RestAssured.baseURI + "/" + target);
        Response response = (Response) RestAssured.given().auth().basic(TestProps.get("loginUser"), TestProps.get("password")).headers(this.headers).get(target, new Object[0]);
        Logs.info("Received response for get call");
        return response;
    }

    public Response get(String target, Map<String, Object> params, String noAuth, String nothing) {
        RestAssured.baseURI = this.originalURI;
        Logs.info(RestAssured.baseURI + "/" + target);
        Response response = (Response) RestAssured.given().params(params).headers(this.headers).get(target, new Object[0]);

        for (Map.Entry<String, Object> pair : params.entrySet()) {
            Logs.info(String.format("Key (Param name) is: %s, Value (Param value) is : %s", pair.getKey(), pair.getValue()));
        }

        Logs.info("Received response for get call");
        return response;
    }

    public Response get(String target, String payload) {
        RestAssured.baseURI = this.originalURI;
        Logs.info(RestAssured.baseURI + "/" + target);
        Response response = (Response) RestAssured.given().headers(this.headers).body(payload).get(target, new Object[0]);
        Logs.info("Received response for get call");
        return response;
    }

    public Response post(String target, String payload) {
        RestAssured.baseURI = this.originalURI;
        Response response = (Response) RestAssured.given().headers(this.headers).body(payload).post(target, new Object[0]);
        Logs.info("Received response for post call");
        Logs.info(RestAssured.baseURI + "/" + target);
        return response;
    }

    public Response post(String target, String payload, String key) {
        RestAssured.baseURI = this.originalURI;
        Response response = (Response) RestAssured.given().contentType("multipart/form-data").multiPart(key, payload).headers(this.headers).post(target, new Object[0]);
        Logs.info("Received response for post call");
        Logs.info(RestAssured.baseURI + "/" + target);
        return response;
    }



    public Response post(String target, String path, Map<String, String> params, Map<String, String> replacements) {
        RestAssured.baseURI = this.originalURI;

        for (Map.Entry<String, String> pair : params.entrySet()) {
            Logs.info(String.format("Key (Param name) is: %s, Value (Param value) is : %s", pair.getKey(), pair.getValue()));
        }

        Logs.info(RestAssured.baseURI + "/" + target);
        return (Response) RestAssured.given().params(params).headers(this.headers).body((new Templater(this.getResourceStream(this.resourcePath + path))).replace(replacements).toString()).post(target, new Object[0]);
    }

    public Response post(String target, String path, Map<String, String> replacements) {
        return this.post(target, (new Templater(this.getResourceStream(this.resourcePath + path))).replace(replacements).toString());
    }

    public Response post(String target, String path, Map<String, String> replacements, String keyVal) {
        return this.post(target, (new Templater(this.getResourceStream(this.resourcePath + path))).replace(replacements).toString(), keyVal);
    }

    public Response put(String target, String payload) {
        RestAssured.baseURI = this.originalURI;
        Logs.info(RestAssured.baseURI + "/" + target);
        Response response = (Response) RestAssured.given().headers(this.headers).body(payload).put(target, new Object[0]);
        Logs.info("Received response for put call");
        return response;
    }

    public Response put(String target, String path, Map<String, String> replacements) {
        return this.put(target, (new Templater(this.getResourceStream(this.resourcePath + path))).replace(replacements).toString());
    }

    public Response put(String target, String path, Map<String, String> params, Map<String, String> replacements) {
        RestAssured.baseURI = this.originalURI;
        Logs.info(RestAssured.baseURI + "/" + target);

        for (Map.Entry<String, String> pair : params.entrySet()) {
            Logs.info(String.format("Key (Param name) is: %s, Value (Param value) is : %s", pair.getKey(), pair.getValue()));
        }

        return (Response) RestAssured.given().params(params).headers(this.headers).body((new Templater(this.getResourceStream(this.resourcePath + path))).replace(replacements).toString()).put(target, new Object[0]);
    }

    public Response delete(String target) {
        RestAssured.baseURI = this.originalURI;
        Logs.info(RestAssured.baseURI + "/" + target);
        Response response = (Response) RestAssured.given().headers(this.headers).delete(target, new Object[0]);
        Logs.info("Received response for get call");
        return response;
    }

    public InputStream getResourceStream(String resourcePath) {
        try {
            return new FileInputStream(resourcePath);
        } catch (FileNotFoundException e) {
            Logs var10000 = this.logger;
            Logs.error("Error message: " + e);
            return null;
        }
    }

    public String streamToString(InputStream stream) {
        try {
            return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
        } catch (IOException e) {
            Logs var10000 = this.logger;
            Logs.error("Error message: " + e);
            return null;
        }
    }

    public void assertResponseValue(Response response, String path, String value) {
        if (!response.jsonPath().get(path).toString().contains(value)) {
            throw new RuntimeException("\nError in response value, response value not matched\n" + response.toString());
        } else {
            Logs.info("Response value :" + value + " validated - Response value verification passed");
        }
    }

    public void assertStatusCode(Response response, String scode) {
        if (!Integer.toString(response.getStatusCode()).equals(scode)) {
            throw new RuntimeException("\nError in response status code\nExpected: " + scode + ", received: " + response.getStatusCode());
        } else {
            Logs.info("Status code :" + scode + " validated - status code verification passed");
        }
    }

    public Object getResponseValue(Response response, String path) {
        try {
            Object val = response.jsonPath().get(path);
            if (val != null) {
                return val;
            } else {
                throw new RuntimeException("Expected Response value is null and path is : " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Response path is not valid");
        }
    }
}

