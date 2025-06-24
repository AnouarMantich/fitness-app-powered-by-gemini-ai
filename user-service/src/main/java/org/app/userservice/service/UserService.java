package org.app.userservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.app.userservice.Exception.UserNotFoundException;
import org.app.userservice.dtos.RegisterRequest;
import org.app.userservice.dtos.UserResponse;
import org.app.userservice.model.User;
import org.app.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId + " not found")
        );
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .keycloakId(user.getKeycloakId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            User existingUser = userRepository.findByEmail(request.getEmail());
            return UserResponse.builder()
                    .id(existingUser.getId())
                    .firstName(existingUser.getFirstName())
                    .lastName(existingUser.getLastName())
                    .email(existingUser.getEmail())
                    .keycloakId(existingUser.getKeycloakId())
                    .createdAt(existingUser.getCreatedAt())
                    .updatedAt(existingUser.getUpdatedAt())
                    .build();
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .keycloakId(request.getKeycloakId())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        User saved = userRepository.save(user);

        return UserResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .keycloakId(saved.getKeycloakId())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    public boolean existById(String userId) {
        return userRepository.existsById(userId);
    }

    public Boolean existByKeycloakId(String userId) {
        return userRepository.existsByKeycloakId(userId);
    }
}
