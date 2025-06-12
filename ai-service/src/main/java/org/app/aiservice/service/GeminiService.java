package org.app.aiservice.service;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeminiService {

    private WebClient webClient;

    @Value("${gemini.api.url}")
    private String baseUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAnswer(String question) {
        Map<String,Object[]> requestBody = Map.of(
                "contents",new Object[]{
                        new Object[]{
                                Map.of("parts", new Object[]{
                                        Map.of("text", question),
                                })
                        },
                }
        );

       return webClient.post()
                .uri(baseUrl+apiKey)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }



}
