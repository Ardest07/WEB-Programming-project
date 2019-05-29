package pl.rengreen.tasklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.rengreen.tasklist.domain.Task;
import pl.rengreen.tasklist.domain.User;
import pl.rengreen.tasklist.service.TaskService;
import pl.rengreen.tasklist.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class TaskController {

    private UserService userService;
    private TaskService taskService;

    @Autowired
    public TaskController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String listTasks(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        String email = principal.getName();
        User signedUser = userService.getUserByEmail(email);
        boolean isAdmin;
        if (request.isUserInRole("ROLE_ADMIN")){
            isAdmin=true;
        } else {
            isAdmin=false;
        }
        model.addAttribute("tasks", taskService.findAllByOrderByDateDesc());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("signedUser", signedUser);
        model.addAttribute("isAdmin", isAdmin);
        return "views/tasksList";
    }

    @GetMapping("task/new/free")
    public String showEmptyTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "views/emptyTaskForm";
    }

    @PostMapping("task/new/free")
    public String createTask(@Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "views/emptyTaskForm";
        }
        taskService.createTask(task);

        return "redirect:/tasks";
    }

    @GetMapping("task/create")
    public String showEmptyTaskFormForUser(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        Task task= new Task();
        task.setCreator(user);
        if (!request.isUserInRole("ROLE_ADMIN")){
            task.setOwner(user);
        }
        model.addAttribute("task", task);
        return "views/emptyTaskForm";
    }

    @PostMapping("task/create")
    public String createTaskForUser(@Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "views/emptyTaskForm";
        }
        taskService.createTask(task);

        return "redirect:/tasks";
    }

    @GetMapping("task/edit/{id}")
    public String showFilledTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "views/filledTaskForm";
    }

    @PostMapping("task/edit/{id}")
    public String updateTask(@Valid Task task, BindingResult bindingResult, @PathVariable Long id, Model model) {
        if (bindingResult.hasErrors()) {
            return "views/filledTaskForm";
        }
        taskService.updateTask(id, task);
        return "redirect:/tasks";
    }

    @GetMapping("task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("task/markDone/{id}")
    public String setTaskCompleted(@PathVariable Long id) {
        taskService.setTaskCompleted(id);
        return "redirect:/tasks";
    }

    @GetMapping("task/markUndone/{id}")
    public String setTaskNotCompleted(@PathVariable Long id) {
        taskService.setTaskNotCompleted(id);
        return "redirect:/tasks";
    }

}


