package com.example.authservice.service.client;

import com.example.authservice.config.Config;
import com.example.authservice.service.JwtService;
import com.example.authservice.service.client.user.CreateUserRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.example.authservice.service.client.user.CreateUserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.HttpResponseException;
import io.jsonwebtoken.Jwts;

public class UserServiceClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public int addUser(CreateUserRequest user) throws Exception {
        String url = Config.USER_SERVICE_URL + "/api/v1/users";
        objectMapper.registerModule(new JavaTimeModule());
        String requestBody = objectMapper.writeValueAsString(user);

        String jwtToken = JwtService.generateTemporalToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            CreateUserResponse userResponse = objectMapper.readValue(response.body(), CreateUserResponse.class);
            return userResponse.id;
        } else {
            throw new HttpResponseException(response.statusCode(), response.body());
        }
    }
}
