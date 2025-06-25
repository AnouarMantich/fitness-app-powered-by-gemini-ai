package org.app.userservice.repository;

import org.app.userservice.model.User;
import org.app.userservice.model.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void init(){

        // Arrange

         user= User.builder()
                .email("user@gmail.com")
                .keycloakId("123456")
                .firstName("firstName")
                .lastName("lastName")
                .role(UserRole.USER)
                .build();
    }


    @Test
    public void userRepository_SaveUser_returnSavedUser() {
        //Act

        User savedUser = userRepository.save(user);

        //Assert

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo("firstName");
        Assertions.assertThat(savedUser.getLastName()).isEqualTo("lastName");
        Assertions.assertThat(savedUser.getRole()).isEqualTo(UserRole.USER);
        Assertions.assertThat(savedUser.getKeycloakId()).isEqualTo("123456");

    }


    @Test
    public void userRepository_GetAllUsers_returnAllUsers() {

        User user2= User.builder()
                .email("user2@gmail.com")
                .keycloakId("123456")
                .firstName("firstName2")
                .lastName("lastName2")
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
        userRepository.save(user2);

        List<User> allUsers = userRepository.findAll();

        Assertions.assertThat(allUsers).isNotNull();
        Assertions.assertThat(allUsers.size()).isEqualTo(2);
    }

    @Test
    public void userRepository_getUserById_returnUser() {

        userRepository.save(user);

        Assertions.assertThat(userRepository.findById(user.getId())).isNotNull();
    }

    @Test
    public void userRepository_existsByEmail_returnUser() {
        userRepository.save(user);

        Assertions.assertThat(userRepository.existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    public void userRepository_existsById_returnBoolen() {

        userRepository.save(user);
        Assertions.assertThat(userRepository.existsById(user.getId())).isTrue();
    }

    @Test
    public void userRepository_existsByKeycloakId_returnUser() {
        userRepository.save(user);
        Assertions.assertThat(userRepository.existsByKeycloakId(user.getKeycloakId())).isTrue();
    }

    @Test
    public void userRepository_findByEmail_returnUser() {
        userRepository.save(user);

        Assertions.assertThat(userRepository.findByEmail(user.getEmail())).isNotNull();
    }


    @Test
    public void userRepository_updateUser_returnUser() {
        User savedUser = userRepository.save(user);

        savedUser.setEmail("updatedEmail@gmail.com");
        User saved = userRepository.save(savedUser);

        Assertions.assertThat(userRepository.findById(savedUser.getId())).isNotNull();
        Assertions.assertThat(saved.getEmail()).isEqualTo("updatedEmail@gmail.com");
    }

    @Test
    public void userRepository_deleteUser_ReturnUserIsEmpty() {
        User saved = userRepository.save(user);
        userRepository.delete(saved);


        Assertions.assertThat(userRepository.findById(user.getId())).isEmpty();
    }

}
