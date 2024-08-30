/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.controller;

import ctu.demo.dto.TaskDTO;
import ctu.demo.model.Task;
import ctu.demo.model.User;
import ctu.demo.respone.ResponseHandler;
import ctu.demo.service.TaskService;
import ctu.demo.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/task/all")
    public ResponseEntity<?> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDTO> DTO = new ArrayList<>();
        for (Task task : tasks)
            DTO.add(Task.toTaskDTO(task));
        return ResponseHandler.resBuilder("Lấy tất cả các task thành công", HttpStatus.OK,DTO);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseHandler.resBuilder("Lấy danh sách tất cả user thành công", HttpStatus.OK, users);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy danh sách user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
