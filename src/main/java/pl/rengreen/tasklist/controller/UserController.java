package pl.rengreen.tasklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.rengreen.tasklist.service.TaskService;
import pl.rengreen.tasklist.service.UserService;

@Controller
public class UserController {

    private UserService userService;
    private TaskService taskService;

    @Autowired
    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "views/usersList";
    }

    @GetMapping("user/assignTasks/{email}")
    public String showAssignTaskForm(@PathVariable String email, Model model) {

        model.addAttribute("user", userService.getUserByEmail(email));
        model.addAttribute("freeTasks", taskService.findFreeTasks());

        return "views/assignTasksForm";
    }
}
