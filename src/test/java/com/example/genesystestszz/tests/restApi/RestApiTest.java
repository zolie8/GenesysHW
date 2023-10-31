package com.example.genesystestszz.tests.restApi;
import com.google.gson.JsonArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RestApiTest {
    private static final org.apache.log4j.Logger log = Logger.getLogger(RestApiTest.class);

    @BeforeAll
    public static void initLog() {
        BasicConfigurator.configure();
    }
    @Test
    public void testGetApiData() {
        String apiUrl = "https://jsonplaceholder.typicode.com/users"; // Replace with your API endpoint URL
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(apiUrl);

            log.info("Execute the HTTP GET request");
            HttpResponse response = httpClient.execute(httpGet);

            log.info("Check if the response status code is 200");
            assertEquals(200, response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);

            log.info("Parse the JSON response");
            JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();

            log.info("Iterate through each JSON object in the array");
            List<String> emails = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String name = jsonObject.get("name").getAsString();
                String email = jsonObject.get("email").getAsString();
                if(Objects.equals(name, "Leanne Graham") || Objects.equals(name, "Ervin Howell")) {
                    log.info("name:" + name);
                    log.info("email:" + email);
                    emails.add(email);
                }
            }
            Assertions.assertTrue(emails.get(0).contains("@"), "The first email doesn't contains \"@\"");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}