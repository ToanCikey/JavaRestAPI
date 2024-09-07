package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

import java.util.Optional;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // @GetMapping("/user/create")
    @PostMapping("/user")
    public User createNewUser(@RequestBody User userPostMan) {
        // User user = new User();
        // user.setEmail("nguyenvantoan@gmail.com");
        // user.setPassword("123");
        // user.setName("Toan");
        User userNew = this.userService.handleCreateUser(userPostMan);
        return userNew;
    }

    @DeleteMapping("user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        this.userService.handleDeleteUser(id);
        return "userNew";
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") long id) {
        Optional<User> user = this.userService.fetchUserById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @GetMapping("/user")
    public List<User> fetchAllUserPage() {
        List<User> userArrs = this.userService.fetchAllUsers();
        return userArrs;
    }

    @PutMapping("/user")
    public User putUserById(@RequestBody User userPostMan) {
        User user = this.userService.handleUpdateUser(userPostMan);
        return user;
    }

}
