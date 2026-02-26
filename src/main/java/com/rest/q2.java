package com.rest;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

public class q2 {

    public static int getWinnerTotalGoals(String competition, int year) {
        int result = 0;
        String compUrl = "https://jsonmock.hackerrank.com/api/football_competitions";
        String matchesUrl = "https://jsonmock.hackerrank.com/api/football_matches";
        String encodedCompetition = URLEncoder.encode(competition, StandardCharsets.UTF_8);

        // find comp winner
        HttpClient client = HttpClient.newHttpClient();
        String url = compUrl + "?name=" + encodedCompetition + "&year=" + year;
        String winner = "";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject obj = new JSONObject(response.body());
            JSONArray data = obj.getJSONArray("data");
            winner = data.getJSONObject(0).getString("winner");
            winner = URLEncoder.encode(winner, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // find winner Home goals
        for (int i = 1; i <= 10; i++) {
            int page = 1;
            int totalPages = 1;

            while (page <= totalPages) {
                url = matchesUrl + "?competition=" + encodedCompetition + "&year=" + year + "&team1=" + winner + "&team1goals=" + i + "&page=" + page;
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .GET()
                            .build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    JSONObject obj = new JSONObject(response.body());
                    totalPages = obj.getInt("total_pages");
                    JSONArray data = obj.getJSONArray("data");
                    result += data.length() * i;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                page++;
            }
        }

        // find winner Away goals
        for (int i = 1; i <= 10; i++) {
            int page = 1;
            int totalPages = 1;

            while (page <= totalPages) {
                url = matchesUrl + "?competition=" + encodedCompetition + "&year=" + year + "&team2=" + winner + "&team2goals=" + i + "&page=" + page;
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .GET()
                            .build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    JSONObject obj = new JSONObject(response.body());
                    totalPages = obj.getInt("total_pages");
                    JSONArray data = obj.getJSONArray("data");
                    result += data.length() * i;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                page++;
            }
        }
        
        return result;
    }

    public static void main(String[] args) {
        System.out.println(q2.getWinnerTotalGoals("UEFA Champions League", 2011));
    }
}
