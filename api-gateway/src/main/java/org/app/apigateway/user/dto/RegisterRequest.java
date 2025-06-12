package org.app.apigateway.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    @NotBlank(message = "The email is required")
    @Email(message = "Please enter a valid email address")
    private String email;
    private String keycloakId;
    @NotBlank(message = "The password is required")
    @Size(min = 6,message = "The password should at least 6 characters")
    private String password;
    private String firstName;
    private String lastName;

}
