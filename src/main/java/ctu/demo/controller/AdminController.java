/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.controller;

import ctu.demo.dto.CommentDTO;
import ctu.demo.dto.GroupDTO;
import ctu.demo.dto.TaskDTO;
import ctu.demo.dto.TaskResponse;
import ctu.demo.dto.UserDTO;
import ctu.demo.model.Account;
import ctu.demo.model.Comment;
import ctu.demo.model.Group;
import ctu.demo.model.Task;
import ctu.demo.model.TaskUpdate;
import ctu.demo.model.User;
import ctu.demo.request.ConfirmUpdateRequest;
import ctu.demo.request.GroupRequest;
import ctu.demo.request.UpdateGroupRequest;
import ctu.demo.respone.ResponseHandler;
import ctu.demo.service.AccountService;
import ctu.demo.service.CommentService;
import ctu.demo.service.GroupService;
import ctu.demo.service.TaskService;
import ctu.demo.service.TaskUpdateService;
import ctu.demo.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;
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

    @GetMapping("/task-updates/all")
    public ResponseEntity<?> getAllTaskUpdates() {
        List<TaskUpdate> taskUpdates = taskUpdateService.getAllTaskUpdates();

        return ResponseHandler.resBuilder("Lấy tất cả các yêu cầu cập nhật thành công", HttpStatus.OK, taskUpdates);
    }

    @GetMapping("/task/all")
    public ResponseEntity<?> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskResponse> DTO = new ArrayList<>();
        for (Task task : tasks) {
            DTO.add(Task.toTaskResponse(task));
        }
        return ResponseHandler.resBuilder("Lấy tất cả các task thành công", HttpStatus.OK, DTO);
    }
//    @GetMapping("/group/{id}")
//    public ResponseEntity<?> getUserOfGroup(@PathVariable("id") Long id) {
//        Group group = groupService.findById(id);
//        if (group == null) {
//            return ResponseHandler.resBuilder("Nhóm không tồn tại", HttpStatus.NOT_FOUND, null);
//        }
//        List<User> users = userService.getAllUsersGroup(group);
//        return ResponseHandler.resBuilder("Lấy tất cả các task thành công", HttpStatus.OK, users);
//    }

    @PostMapping("/group/create")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest groupRequest) {
        try {
            System.out.println(groupRequest);
            Group newGroup = groupService.createGroup(groupRequest);
            return ResponseHandler.resBuilder("Tạo nhóm làm việc thành công", HttpStatus.CREATED, newGroup);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Error creating group: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/group/add-user")
    public ResponseEntity<?> addToGroup(@RequestBody UpdateGroupRequest groupRequest) {
        System.out.println(groupRequest.getGroupId());
        try {
            // Gọi phương thức dịch vụ để cập nhật nhóm
            Group updatedGroup = groupService.updateGroup(groupRequest);

            // Trả về phản hồi thành công
            return ResponseHandler.resBuilder("Cập nhật nhóm thành công.", HttpStatus.OK, updatedGroup);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.resBuilder("Không tìm thấy nhóm với ID: " + groupRequest.getGroupId(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác và trả về lỗi máy chủ nội bộ
            return ResponseHandler.resBuilder("Đã xảy ra lỗi không mong muốn: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/task/update-request/all")
    public ResponseEntity<?> getAllTaskUpdatesRequest() {
        try {
            List<TaskUpdate> taskUpdates = taskUpdateService.getAllTaskUpdates();

            if (taskUpdates.isEmpty()) {
                return ResponseHandler.resBuilder("Không tìm thấy yêu cầu cập nhật tác vụ nào", HttpStatus.NOT_FOUND, null);
            }

            return ResponseHandler.resBuilder("Lấy thành công danh sách yêu cầu cập nhật tác vụ", HttpStatus.OK, taskUpdates);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy các yêu cầu cập nhật task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/task/update-request/approve")
    public ResponseEntity<?> approveTaskUpdatesRequest(@RequestBody ConfirmUpdateRequest req) {
      System.out.println(req.toString());
        try {
            TaskUpdate taskUpdate = taskUpdateService.approveTaskUpdate(req);
            if (taskUpdate == null) {
                return ResponseHandler.resBuilder("Lỗi khi xác thực yêu cầu cập nhật tác vụ", HttpStatus.BAD_REQUEST, null);
            }
            return ResponseHandler.resBuilder("Yêu cầu cập nhật tác vụ đã được phê duyệt thành công.", HttpStatus.OK, taskUpdate);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.resBuilder("Lỗi: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi xử lý yêu cầu phê duyệt tác vụ: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/task/update-request/reject")
    public ResponseEntity<?> rejectTaskUpdatesRequest(@RequestBody ConfirmUpdateRequest req) {
          
        try {
            TaskUpdate taskUpdate = taskUpdateService.rejectTaskUpdate(req);
            if (taskUpdate == null) {
                return ResponseHandler.resBuilder("Lỗi khi xác thực yêu cầu cập nhật tác vụ", HttpStatus.BAD_REQUEST, null);
            }
            return ResponseHandler.resBuilder("Yêu cầu cập nhật tác vụ đã bị từ chối thành công.", HttpStatus.OK, taskUpdate);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.resBuilder("Lỗi: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi xử lý yêu cầu từ chối tác vụ: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroup(@PathVariable("id") long id) {
        try {
            Group optionalGroup = groupService.findById(id);

            if (optionalGroup == null) {
                return ResponseHandler.resBuilder("Nhóm không tồn tại", HttpStatus.NOT_FOUND, null);
            }

            Map<String, Object> groupDTO = new HashMap<>();
            groupDTO.put("id", optionalGroup.getId());
            groupDTO.put("name", optionalGroup.getName());
            groupDTO.put("description", optionalGroup.getDescription());
            return ResponseHandler.resBuilder("Lấy thông tin nhóm thành công", HttpStatus.OK, groupDTO);
        } catch (MethodArgumentTypeMismatchException e) {
            return ResponseHandler.resBuilder("Giá trị tham số không hợp lệ", HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            // Ghi log lỗi (nếu có)
            // logger.error("Lỗi khi lấy thông tin nhóm: ", e);
            return ResponseHandler.resBuilder("Đã xảy ra lỗi: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/group/all")
    public ResponseEntity<?> getAllGroup() {
        try {
            List<Group> groups = groupService.getAllGroups();

            if (groups == null || groups.isEmpty()) {
                return ResponseHandler.resBuilder("Nhóm không tồn tại", HttpStatus.NOT_FOUND, null);
            }

            // Sử dụng stream để chuyển đổi danh sách Group thành GroupDTO
            List<GroupDTO> groupsDTO = groups.stream()
                    .map(GroupDTO::convertToDto)
                    .collect(Collectors.toList());

            return ResponseHandler.resBuilder("Lấy tất cả các nhóm thành công", HttpStatus.OK, groupsDTO);
        } catch (MethodArgumentTypeMismatchException e) {
            return ResponseHandler.resBuilder("Giá trị tham số không hợp lệ", HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.resBuilder(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/account/{email}")
    public ResponseEntity<?> getAcount(@PathVariable("email") String email) {
        Account account = accountService.findAccountByEmail(email);
        if (account == null) {
            return ResponseHandler.resBuilder("Tài khoản không tồn tại", HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.resBuilder("Lấy tất cả các task thành công", HttpStatus.OK, account);
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getUserNotHasRoleAdmin();
            List<UserDTO> listUsersResponse = new ArrayList<>();
            for (User user : users) {
                listUsersResponse.add(UserDTO.convertToDto(user));
            }
            return ResponseHandler.resBuilder("Lấy danh sách tất cả user thành công", HttpStatus.OK, listUsersResponse);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy danh sách user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/user/no-group")
    public ResponseEntity<?> getAllUsersHasNoGroup() {
        try {
            List<User> users = userService.getUserHasRoleAdminAndNoGroup();
            List<UserDTO> listUsersResponse = new ArrayList<>();
            for (User user : users) {
                listUsersResponse.add(UserDTO.convertToDto(user));
            }
            return ResponseHandler.resBuilder("Lấy danh sách tất cả user thành công", HttpStatus.OK, listUsersResponse);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy danh sách user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                userService.deleteUserWithGroup(id);
                return ResponseHandler.resBuilder("Xóa người dùng thành công", HttpStatus.OK, user);
            } else {
                return ResponseHandler.resBuilder("Người dùng không tồn tại", HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi xóa người dùng: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/task/user/{userId}")
    public ResponseEntity<?> getTasksOfUsers(@PathVariable long userId, @PathVariable long taskId) {
        try {
            List<Task> tasks = userService.getTasksByUserId(userId);
            return ResponseHandler.resBuilder("Lấy danh sách tất cả task của user thành công", HttpStatus.OK, tasks);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy danh sách task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
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
    public ResponseEntity<?> createTask(@RequestBody TaskDTO task) {
        Task savedTask;
        Optional<Task> findedTask = taskService.getTaskByTitle(task.getTitle());
        if (findedTask.isPresent()) {
            return ResponseHandler.resBuilder("Task này đã tồn tại", HttpStatus.CONFLICT, null);
        }
        try {

            savedTask = taskService.saveTask(task);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.resBuilder("Lỗi khi tạo một task mới", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        return ResponseHandler.resBuilder("Tạo task thành công", HttpStatus.CREATED, Task.toTaskResponse(savedTask));
    }

    @PutMapping("/task/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            Task existingTask = taskService.getTaskById(id).orElseThrow(() -> new RuntimeException("Task không tồn tại"));
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setDate(taskDTO.getDate());
            existingTask.setStatus(taskDTO.getStatus());
            existingTask.setUpdatedDate(new Date());

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

    @PostMapping("/task/comment/{taskId}")
    public ResponseEntity<?> createComment(@PathVariable Long taskId, @RequestBody CommentDTO cmtDTO) {
        Task task;
        Optional<Task> findedTask = taskService.getTaskById(taskId);
        if (findedTask.isEmpty()) {
            return ResponseHandler.resBuilder("Task không tồn tại", HttpStatus.CONFLICT, null);
        }
        try {
            Comment cmt = commentService.saveComment(taskId, cmtDTO);
            return ResponseHandler.resBuilder("Lấy task theo id thành công", HttpStatus.CREATED, cmt.toCommentDTO(cmt));
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy task theo id: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/task/comment/{taskId}")
    public ResponseEntity<?> getAllComment(@PathVariable Long taskId) {
        Task task;
        Optional<Task> findedTask = taskService.getTaskById(taskId);
        if (findedTask.isEmpty()) {
            return ResponseHandler.resBuilder("Task không tồn tại", HttpStatus.CONFLICT, null);
        }
        try {
            // Lấy tất cả các comment liên quan đến taskId
            List<Comment> comments = commentService.getCommentByTaskId(taskId);

            // Chuyển đổi từ Comment sang CommentDTO
            List<CommentDTO> commentDTOs = new ArrayList<>();
            for (Comment comment : comments) {
                commentDTOs.add(comment.toCommentDTO(comment));
            }
            return ResponseHandler.resBuilder("Lấy tất cả comment theo task thành công", HttpStatus.OK, commentDTOs);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy comment theo task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
