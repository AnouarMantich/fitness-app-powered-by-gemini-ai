package org.app.userservice.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String userId;

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
