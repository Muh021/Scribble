package dev.scribble.repository;

import dev.scribble.models.User;
import dev.scribble.models.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    UserLogin findByUser(User user);
    UserLogin findByUsername(String username);
}
