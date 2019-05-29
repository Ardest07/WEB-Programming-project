package pl.rengreen.tasklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.rengreen.tasklist.domain.Role;
import pl.rengreen.tasklist.domain.User;
import pl.rengreen.tasklist.repository.UserRepository;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Role roleUser = new Role("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void createAdmin(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Role roleAdmin = new Role("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(roleAdmin);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }

    @Override
    public boolean isUserPresent(String email) {
        if (userRepository.findById(email).isPresent()) {
            return true;
        }
        return false;
    }
}
