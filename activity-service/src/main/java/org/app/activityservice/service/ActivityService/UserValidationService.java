package org.app.activityservice.service.ActivityService;


import lombok.RequiredArgsConstructor;
import org.app.activityservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {
        try {
            return userServiceWebClient.get()
                    .uri("/api/v1/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("User with id "+userId+" doesn't exist");
            }
            else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid request");
            }
        }

        return false;
    }

}
