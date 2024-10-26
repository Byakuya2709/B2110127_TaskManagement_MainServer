/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.controller;

import ctu.demo.dto.CommentDTO;
import ctu.demo.dto.TaskDTO;
import ctu.demo.dto.TaskResponse;
import ctu.demo.dto.UserDTO;
import ctu.demo.model.Comment;
import ctu.demo.model.Group;
import ctu.demo.model.Task;
import ctu.demo.model.TaskUpdate;
import ctu.demo.model.User;
import ctu.demo.repository.CommentRepository;
import ctu.demo.request.UpdatedTask;
import ctu.demo.respone.ResponseHandler;
import ctu.demo.service.CommentService;
import ctu.demo.service.GroupService;
import ctu.demo.service.TaskService;
import ctu.demo.service.TaskUpdateService;
import ctu.demo.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Autowired
    private GroupService groupService;
    @Autowired
    private TaskUpdateService taskUpdateService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            UserDTO res = UserDTO.convertToDto(user);
            if (user != null) {
                return ResponseHandler.resBuilder("Lấy thông tin user thành công", HttpStatus.OK, res);
            } else {
                return ResponseHandler.resBuilder("User không tồn tại", HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy thông tin user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
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

    @GetMapping("/group/{userId}/tasks")
    public ResponseEntity<?> getAllTasksByGroup(@PathVariable Long userId) {
        try {
            Group group = groupService.findByUserId(userId);
            if (group == null) {
                return ResponseHandler.resBuilder("Người dùng không thuộc về nhóm nào", HttpStatus.BAD_REQUEST, null);
            }
            List<Task> tasks = taskService.getAllTasksByGroupId(group.getId());
            List<TaskResponse> DTO = new ArrayList<>();
            for (Task task : tasks) {
                DTO.add(Task.toTaskResponse(task));
            }
            return ResponseHandler.resBuilder("Lấy task theo id thành công", HttpStatus.OK, DTO);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy task theo id: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
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
    @GetMapping("/{userId}/tasks")
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

    @GetMapping("/task/update-request/{userId}/all")
    public ResponseEntity<?> getAllTaskUpdatesByUser(@PathVariable Long userId) {
        try {
            List<TaskUpdate> taskUpdates = taskUpdateService.getUpdatesByUserId(userId);

            if (taskUpdates.isEmpty()) {
                return ResponseHandler.resBuilder("Không tìm thấy yêu cầu cập nhật nào cho người dùng với ID: " + userId, HttpStatus.NOT_FOUND, null);
            }

            return ResponseHandler.resBuilder("Lấy thành công danh sách yêu cầu cập nhật cho người dùng với ID: " + userId, HttpStatus.OK, taskUpdates);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy các yêu cầu cập nhật task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    // Cập nhật một Task
    @PostMapping("/task/update")
    public ResponseEntity<?> updateTask(@RequestBody UpdatedTask updatedTask) {
        try {
            TaskUpdate taskUpdate = taskUpdateService.createTaskUpdate(updatedTask);
            return ResponseHandler.resBuilder("Gửi yêu cầu cập nhật Task thành công.", HttpStatus.OK, taskUpdate);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi cập nhật task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/task/delete/{id}")
public ResponseEntity<?> deleteUpdatedTask(@PathVariable Long id) {
    try {
        if (taskUpdateService.deleteTaskUpdate(id)) {
            return ResponseHandler.resBuilder("Xóa yêu cầu cập nhật tác vụ: " + id + " thành công", HttpStatus.OK, null);
        } else {
            return ResponseHandler.resBuilder("Không tìm thấy yêu cầu cập nhật tác vụ với ID: " + id, HttpStatus.NOT_FOUND, null);
        }
    } catch (DataIntegrityViolationException e) {
        return ResponseHandler.resBuilder("Không thể xóa yêu cầu cập nhật vì có ràng buộc dữ liệu: " + e.getMessage(), HttpStatus.CONFLICT, null);
    } catch (Exception e) {
        return ResponseHandler.resBuilder("Lỗi khi xóa yêu cầu cập nhật tác vụ: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}

    // Xóa một Task
//    @DeleteMapping("/task/delete/{id}")
//    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
//        try {
//            taskService.deleteTask(id);
//            return ResponseHandler.resBuilder("Xóa task thành công", HttpStatus.OK, "Task deleted successfully");
//        } catch (Exception e) {
//            return ResponseHandler.resBuilder("Lỗi khi xóa task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
//        }
//    }
}
