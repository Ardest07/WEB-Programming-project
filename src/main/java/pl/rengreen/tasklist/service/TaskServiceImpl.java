package pl.rengreen.tasklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rengreen.tasklist.domain.Task;
import pl.rengreen.tasklist.domain.User;
import pl.rengreen.tasklist.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public void createTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.getOne(id);

        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setDate(updatedTask.getDate());

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void setTaskCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(true);
        taskRepository.save(task);
    }

    @Override
    public void setTaskNotCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(false);
        taskRepository.save(task);
    }

    @Override
    public void assignTaskToUser(Task task, User user) {
        task.setOwner(user);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findFreeTasks() {
        List<Task> allTasks = taskRepository.findAll();
        List<Task> freeTasks = new ArrayList<>();
        for (Task item : allTasks) {
            if (item.getOwner() == null) {
                freeTasks.add(item);
            }
        }
        return freeTasks;
    }

    @Override
    public String getOwnerNameOrNoOwner(Task task) {
        if (task.getOwner() != null) {
            return task.getOwner().getName();
        } else {
            return "no owner";
        }
    }

    @Override
    public List<Task> findAllByOrderByDateDesc() {
        return taskRepository.findAllByOrderByDateDesc();
    }

    @Override
    public List<Task> findByOwnerOrderByDateDesc(User owner) {
        return taskRepository.findByOwnerOrderByDateDesc(owner);
    }

}
