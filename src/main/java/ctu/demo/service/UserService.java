/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.service;

import ctu.demo.model.Account.Role;
import ctu.demo.model.Task;
import ctu.demo.model.User;
import ctu.demo.repository.TaskRepository;
import ctu.demo.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TaskRepository taskRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<User> getUserNotHasRoleAdmin() {
        return userRepository.findUserNotHasRoleAdmin(Role.ADMIN);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }
      public User getUserByAccountId(Long accountId) {
         Optional<User> userOptional = userRepository.findByAccountId(accountId);
    if (!userOptional.isPresent()) {
        // Ném exception hoặc xử lý tình huống không tìm thấy người dùng
        throw new RuntimeException ("User not found"); // Hoặc xử lý khác tùy vào yêu cầu
    }
    return userOptional.get();
    }
}
