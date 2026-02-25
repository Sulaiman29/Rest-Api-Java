package com.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        try {

            String apiUrl = "https://jsonmock.hackerrank.com/api/movies";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseJson = new JSONObject(response.body());
            System.out.println(responseJson.getInt("total")); // Pretty print the JSON response
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
