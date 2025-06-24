package org.app.aiservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
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
        log.info("this is the url : {}", baseUrl);
        log.info("this is the api key : {}", apiKey);
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
               .onStatus(
                       status -> status.is4xxClientError() || status.is5xxServerError(),
                       clientResponse -> clientResponse.bodyToMono(String.class)
                               .flatMap(errorBody -> {
                                   // Log or handle error message
                                   return Mono.error(new RuntimeException("API call failed with status: "
                                           + clientResponse.statusCode() + ", body: " + errorBody));
                               })
               )
                .bodyToMono(String.class)

                .block();

    }



}
