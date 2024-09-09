/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.controller;

import ctu.demo.dto.TaskDTO;
import ctu.demo.dto.TaskResponse;
import ctu.demo.model.Task;
import ctu.demo.model.User;
import ctu.demo.respone.ResponseHandler;
import ctu.demo.service.TaskService;
import ctu.demo.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        List<TaskResponse> DTO = new ArrayList<>();
        for (Task task : tasks)
            DTO.add(Task.toTaskResponse(task));
        return ResponseHandler.resBuilder("Lấy tất cả các task thành công", HttpStatus.OK,DTO);
    }
    
    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseHandler.resBuilder("Lấy danh sách tất cả user thành công", HttpStatus.OK, users);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy danh sách user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        try {
             Task task = taskService.getTaskById(id).orElseThrow(() -> new RuntimeException("Task không tồn tại"));
            return ResponseHandler.resBuilder("Lấy task theo id thành công", HttpStatus.OK, Task.toTaskResponse(task));
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy task theo id: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseHandler.resBuilder("Xóa task thành công", HttpStatus.NO_CONTENT, null);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi xóa task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
     @PostMapping("/task/new")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO task ){
        Task savedTask;
        Optional<Task> findedTask =taskService.getTaskByTitle(task.getTitle());
        if (findedTask.isPresent()) {
        return ResponseHandler.resBuilder("Task này đã tồn tại", HttpStatus.CONFLICT, null);
    }
        try{
            
             savedTask = taskService.saveTask(task);
        }catch(Exception e){
           e.printStackTrace();
           return ResponseHandler.resBuilder("Lỗi khi tạo một task mới", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
      
         return ResponseHandler.resBuilder("Tạo task thành công", HttpStatus.CREATED,Task.toTaskResponse(savedTask));
    }
    @PutMapping("/task/updata/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            Task existingTask = taskService.getTaskById(id).orElseThrow(() -> new RuntimeException("Task không tồn tại"));
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setDate(taskDTO.getDate());
            existingTask.setStatus(taskDTO.getStatus());
            
            User user = userService.getUserById(Long.valueOf(taskDTO.getUserId()));
            
           if (user != null) {
                    existingTask.setUser(user);
            } else {
            throw new UsernameNotFoundException("Người dùng không tồn tại");
        }
            Task updatedTask = taskService.saveTask(existingTask);
            return ResponseHandler.resBuilder("Cập nhật task thành công", HttpStatus.OK, Task.toTaskResponse(updatedTask));
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi cập nhật task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
