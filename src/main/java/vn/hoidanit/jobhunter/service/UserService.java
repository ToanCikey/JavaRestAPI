package vn.hoidanit.jobhunter.service;

import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public Optional<User> fetchUserById(long id) {
        return this.userRepository.findById(id);
    }

    public List<User> fetchAllUsers() {
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User user) {
        Optional<User> userOptional = this.userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            currentUser.setEmail(user.getEmail());
            currentUser.setPassword(user.getPassword());
            currentUser.setName(user.getName());
            return this.userRepository.save(user);
        }
        return null;
    }
}
