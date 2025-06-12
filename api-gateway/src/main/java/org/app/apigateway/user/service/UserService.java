package org.app.apigateway.user.service;


import lombok.RequiredArgsConstructor;
import org.app.apigateway.user.dto.RegisterRequest;
import org.app.apigateway.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UserService {

    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId) {
        try {
            return userServiceWebClient.get()
                    .uri("/api/v1/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class);

        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("User not found");
            }
            else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid request");
            }
            else  {
                throw new RuntimeException("Internal server error");
            }
        }


    }

    public Mono<UserResponse> registerUser(RegisterRequest request) {
        try {
            return userServiceWebClient.post()
                    .uri("/api/v1/users/register")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(UserResponse.class);

        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("User not found");
            }
            else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid request");
            }
            else  {
                throw new RuntimeException("Internal server error");
            }
        }

    }
}
