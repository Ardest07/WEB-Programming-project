package pl.rengreen.tasklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.rengreen.tasklist.domain.Task;
import pl.rengreen.tasklist.domain.User;
import pl.rengreen.tasklist.service.TaskService;
import pl.rengreen.tasklist.service.UserService;

import java.security.Principal;

@Controller
public class AssigmentController {
    private UserService userService;
    private TaskService taskService;

    @Autowired
    public AssigmentController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/assign")
    public String showAssigmentForm(Model model, Principal principal, SecurityContextHolderAwareRequestWrapper request) {

        if (!request.isUserInRole("ROLE_ADMIN")){
            String email = principal.getName();
            model.addAttribute("selectedUser", userService.getUserByEmail(email));
        }

        model.addAttribute("users", userService.findAll());
        model.addAttribute("freeTasks", taskService.findFreeTasks());
        return "views/assigmentForm";
    }

    @GetMapping("/assign/{email}")
    public String showUserAssigmentForm(@PathVariable String email, Model model) {
        model.addAttribute("selectedUser", userService.getUserByEmail(email));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("freeTasks", taskService.findFreeTasks());
        return "views/assigmentForm";
    }

    @GetMapping("/assign/{email}/{id}")
    public String assignTaskToUser(@PathVariable String email, @PathVariable Long id, Model model) {
        Task selectedTask=taskService.getTaskById(id);
        User selectedUser=userService.getUserByEmail(email);
        taskService.assignTaskToUser(selectedTask, selectedUser);
        return "redirect:/assign/"+email;
    }
}


