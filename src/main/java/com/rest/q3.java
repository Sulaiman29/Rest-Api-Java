package com.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class q3 {
    public static int healthCheckup(int Lowerlimit, int Uppperlimit) {
        int result = 0;
        int page = 1;
        int totalPages = 1;

        String baseUrl = "https://jsonmock.hackerrank.com/api/medical_records";
        HttpClient client = HttpClient.newHttpClient();
        
        while (page <= totalPages) {
            String url = baseUrl + "?page=" + page;
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject obj = new JSONObject(response.body());
                //System.out.println(response.body());
                totalPages = obj.getInt("total_pages");
                JSONArray data = obj.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject record = data.getJSONObject(i);
                    JSONObject vitals = record.getJSONObject("vitals");
                    int bp = vitals.getInt("bloodPressureDiastole");
                    if (bp >= Lowerlimit && bp <= Uppperlimit) {
                        result++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            page++;
        }
        return result;
    }



    public static void main(String[] args) {
        System.out.println(q3.healthCheckup(120, 160));
    }
}
