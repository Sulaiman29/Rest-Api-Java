package com.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class q1 {
    public static int getDrawnMatches(int year) {
        int result = 0;
        String baseUrl = "https://jsonmock.hackerrank.com/api/football_matches";
        HttpClient client = HttpClient.newHttpClient(); // ⭐ reuse client

        for (int goals = 0; goals <= 10; goals++) {

            int page = 1;
            int totalPages = 1;

            while (page <= totalPages) {

                String url = baseUrl
                        + "?year=" + year
                        + "&team1goals=" + goals
                        + "&team2goals=" + goals
                        + "&page=" + page;

                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .GET()
                            .build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    JSONObject obj = new JSONObject(response.body());

                    totalPages = obj.getInt("total_pages");

                    result += obj.getJSONArray("data").length();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                page++;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(getDrawnMatches(2011));
    }
}
