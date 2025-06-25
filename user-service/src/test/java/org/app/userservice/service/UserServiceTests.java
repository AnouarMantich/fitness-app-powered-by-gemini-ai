package org.app.userservice.service;


import org.app.userservice.dtos.RegisterRequest;
import org.app.userservice.dtos.UserResponse;
import org.app.userservice.model.User;
import org.app.userservice.model.UserRole;
import org.app.userservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void init(){

        // Arrange

        user= User.builder()
                .id("user-001")
                .email("jane.doe@example.com")
                .keycloakId("keycloak-1234-5678")
                .firstName("Jane")
                .lastName("Doe")
                .role(UserRole.USER)
                .build();
    }

    @Test
    public void userService_getUserProfile_returnUserResponse(){

//        UserResponse userResponse = UserResponse.builder()
//                .email("john.doe@example.com")
//                .keycloakId("abc123-xyz789")
//                .firstName("John")
//                .lastName("Doe")
//                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));

        UserResponse userResponse = userService.getUserProfile("sssss");

        Assertions.assertThat(userResponse).isNotNull();
        Assertions.assertThat(userResponse.getFirstName()).isEqualTo("Jane");
        Assertions.assertThat(userResponse.getLastName()).isEqualTo("Doe");
        Assertions.assertThat(userResponse.getId()).isEqualTo("user-001");
    }

    @Test
    public void userService_register_returnUserResponse(){

        RegisterRequest registerRequest = RegisterRequest.builder()
                .userId("user-001")
                .email("jane.doe@example.com")
                .keycloakId("keycloak-1234-5678")
                .password("securePass123") // meets 6+ character requirement
                .firstName("Jane")
                .lastName("Doe")
                .build();


        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        UserResponse register = userService.register(registerRequest);

        Assertions.assertThat(register).isNotNull();
        Assertions.assertThat(register.getId()).isEqualTo("user-001");


    }


}
