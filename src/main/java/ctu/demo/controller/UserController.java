/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.controller;

import ctu.demo.dto.CommentDTO;
import ctu.demo.dto.TaskDTO;
import ctu.demo.dto.TaskResponse;
import ctu.demo.model.Comment;
import ctu.demo.model.Task;
import ctu.demo.model.User;
import ctu.demo.repository.CommentRepository;
import ctu.demo.respone.ResponseHandler;
import ctu.demo.service.CommentService;
import ctu.demo.service.TaskService;
import ctu.demo.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * @author admin
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user!=null) {
                return ResponseHandler.resBuilder("Lấy thông tin user thành công", HttpStatus.OK, user);
            } else {
                return ResponseHandler.resBuilder("User không tồn tại", HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy thông tin user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    
    
    @PostMapping("/task/newtask")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO task) {
    Optional<Task> foundTask = taskService.getTaskByTitle(task.getTitle());
    if (foundTask.isPresent()) {
        return ResponseHandler.resBuilder("Task này đã tồn tại", HttpStatus.CONFLICT, null);
    }
    try {
        Task savedTask = taskService.saveTask(task);
        return ResponseHandler.resBuilder("Tạo task thành công", HttpStatus.CREATED, Task.toTaskResponse(savedTask));
    } catch (Exception e) {
        return ResponseHandler.resBuilder("Lỗi khi tạo một task mới: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

    // Lấy Task theo ID
   @GetMapping("/{userId}/task")
    public ResponseEntity<?> getTasksByUserId(@PathVariable Long userId) {
    List<Task> tasks = userService.getTasksByUserId(userId);
    List<TaskResponse> taskDTOs = new ArrayList<>();
    for (Task task : tasks) {
        taskDTOs.add(Task.toTaskResponse(task));
    }
    return ResponseHandler.resBuilder("Lấy tất cả các task theo userID thành công", HttpStatus.OK, taskDTOs);
}

    @PostMapping("/{userId}/task/{taskId}/comment")
    public ResponseEntity<?> createComment(@PathVariable Long userId, @PathVariable Long taskId, @RequestBody CommentDTO commentDTO) {
    try {
        User user = userService.getUserById(userId);
        Task task = taskService.getTaskById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTask(task);
        comment.setContent(commentDTO.getContent());
        comment.setCreatedDate(new Date()); // Set created date if needed
        
        Comment savedComment = commentService.saveComment(comment);
        return ResponseHandler.resBuilder("Tạo bình luận thành công", HttpStatus.CREATED, savedComment);
    } catch (Exception e) {
        return ResponseHandler.resBuilder("Lỗi khi tạo bình luận: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

    // Cập nhật một Task
    @PutMapping("/{userId}/task/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            Task existingTask = taskService.getTaskById(id).orElseThrow(() -> new RuntimeException("Task không tồn tại"));
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setDate(taskDTO.getDate());
            existingTask.setStatus(taskDTO.getStatus());
//            existingTask.setUser(userService.getUserById(taskDTO.getUserID()));
            Task updatedTask = taskService.saveTask(existingTask);
            return ResponseHandler.resBuilder("Cập nhật task thành công", HttpStatus.OK, Task.toTaskResponse(updatedTask));
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi cập nhật task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    // Xóa một Task
   @DeleteMapping("/task/delete/{id}")
public ResponseEntity<?> deleteTask(@PathVariable Long id) {
    try {
        taskService.deleteTask(id);
        return ResponseHandler.resBuilder("Xóa task thành công", HttpStatus.OK, "Task deleted successfully");
    } catch (Exception e) {
        return ResponseHandler.resBuilder("Lỗi khi xóa task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
 
}
