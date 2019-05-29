package pl.rengreen.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rengreen.tasklist.domain.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
