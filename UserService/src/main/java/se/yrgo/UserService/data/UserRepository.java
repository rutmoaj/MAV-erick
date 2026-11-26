package se.yrgo.UserService.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.UserService.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    boolean existsByUsername(String username);
}
