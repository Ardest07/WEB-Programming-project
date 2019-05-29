package pl.rengreen.tasklist.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;
    @NotEmpty
    private String name;
    @Size(min = 5)
    private String password;
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'images/user.png'")
    private String photo;
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Task> tasksCreated;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Task> tasksOwned;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_EMAIL", referencedColumnName = "email")},
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_NAME", referencedColumnName = "name")})
    private List<Role> roles;

    public List<Task> getTasksCompleted() {
        List<Task> tasksCompleted = new ArrayList<>();
        for (Task task : tasksOwned) {
            if (task.isCompleted()){
                tasksCompleted.add(task);
            }
        }
        return tasksCompleted;
    }

    public List<Task> getTasksInProgress() {
        List<Task> tasksInProgress = new ArrayList<>();
        for (Task task : tasksOwned) {
            if (!task.isCompleted()){
                tasksInProgress.add(task);
            }
        }
        return tasksInProgress;
    }

    public User() {
    }

    public User(@Email @NotEmpty String email, @NotEmpty String firstName, @Size(min = 5) String password, String photo) {
        this.email = email;
        this.name = firstName;
        this.password = password;
        this.photo = photo;
    }

    public User(@Email @NotEmpty String email, @NotEmpty String firstName, @Size(min = 5) String password, String photo,
                List<Task> tasksCreated, List<Task> tasksOwned, List<Role> roles) {
        this.email = email;
        this.name = firstName;
        this.password = password;
        this.photo = photo;
        this.tasksCreated = tasksCreated;
        this.tasksOwned = tasksOwned;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Task> getTasksCreated() {
        return tasksCreated;
    }

    public void setTasksCreated(List<Task> tasksCreated) {
        this.tasksCreated = tasksCreated;
    }

    public List<Task> getTasksOwned() {
        return tasksOwned;
    }

    public void setTasksOwned(List<Task> tasksOwned) {
        this.tasksOwned = tasksOwned;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
