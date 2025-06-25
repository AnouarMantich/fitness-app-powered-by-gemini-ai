package org.app.userservice.repository;

import org.app.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsById(String id);
    boolean existsByKeycloakId(String userId);

    User findByEmail(String email);
}
