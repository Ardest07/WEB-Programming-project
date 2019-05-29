package pl.rengreen.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rengreen.tasklist.domain.Task;
import pl.rengreen.tasklist.domain.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByOwner(User owner);

    List<Task> findCompletedNotByOwner(User owner);

    List<Task> findAllByOrderByDateDesc();

    List<Task> findByOwnerOrderByDateDesc(User owner);
}