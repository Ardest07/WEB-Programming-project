package pl.rengreen.tasklist.service;

import pl.rengreen.tasklist.domain.Task;
import pl.rengreen.tasklist.domain.User;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    void createTask(Task task);

    void updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);

    Task getTaskById(Long id);

    void setTaskCompleted(Long id);

    void setTaskNotCompleted(Long id);

    void assignTaskToUser(Task task, User user);

    List<Task> findFreeTasks();

    String getOwnerNameOrNoOwner(Task task);

    List<Task>  findAllByOrderByDateDesc();

    List<Task> findByOwnerOrderByDateDesc(User owner);
}
