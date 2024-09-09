package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.RestResponse;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import java.util.Optional;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // @GetMapping("/users/create")
    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User userPostMan) {
        String hashPassword = this.passwordEncoder.encode(userPostMan.getPassword());
        userPostMan.setPassword(hashPassword);
        User userNew = this.userService.handleCreateUser(userPostMan);
        return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<RestResponse<String>> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        if (id > 1500) {
            throw new IdInvalidException("id khong lon hon 1500");
        }

        this.userService.handleDeleteUser(id);
        RestResponse<String> res = new RestResponse<String>();
        res.setData("success");
        // return ResponseEntity.ok("success");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> user = this.userService.fetchUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> fetchAllUserPage() {
        List<User> userArrs = this.userService.fetchAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userArrs);
    }

    @PutMapping("/users")
    public ResponseEntity<User> putUserById(@RequestBody User userPostMan) {
        User user = this.userService.handleUpdateUser(userPostMan);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
