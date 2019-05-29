package pl.rengreen.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rengreen.tasklist.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
