package ctu.demo.controller;

import ctu.demo.model.Task;
import ctu.demo.model.User;
import ctu.demo.repository.UserRepository; // Import UserRepository
import ctu.demo.respone.ResponseHandler;
import ctu.demo.service.TaskService;
import ctu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analyst")
public class ManagementController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository

    // Thống kê số lượng nhiệm vụ theo trạng thái
    @GetMapping("/tasks/stats/status")
    public ResponseEntity<Object> getTaskStatsByStatus() {
        Map<String, Long> stats = new HashMap<>();

        // Đếm số lượng nhiệm vụ theo trạng thái
        stats.put("PENDING", taskService.countTasksByStatus(Task.TaskStatus.PENDING));
        stats.put("IN_PROGRESS", taskService.countTasksByStatus(Task.TaskStatus.IN_PROGRESS));
        stats.put("COMPLETED", taskService.countTasksByStatus(Task.TaskStatus.COMPLETED));
        stats.put("CANCELED", taskService.countTasksByStatus(Task.TaskStatus.CANCELED));

        return ResponseHandler.resBuilder("Thống kê số lượng nhiệm vụ theo trạng thái thành công", HttpStatus.OK, stats);
    }

    // Thống kê số lượng nhiệm vụ theo người dùng
    @GetMapping("/tasks/stats/user")
    public ResponseEntity<Object> getTaskStatsByUser() {
        Map<Long, Map<String, Long>> stats = new HashMap<>();

        // Lấy danh sách tất cả người dùng
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            Map<String, Long> userStats = new HashMap<>();
            userStats.put("COMPLETED", taskService.countCompletedTasksByUser(user));
            userStats.put("IN_PROGRESS", taskService.countTasksByUser(user));
            userStats.put("PENDING", taskService.countTasksByUser(user));
            stats.put(user.getId(), userStats);
        }

        return ResponseHandler.resBuilder("Thống kê số lượng nhiệm vụ theo người dùng thành công", HttpStatus.OK, stats);
    }

    // Thống kê số lượng người dùng
    @GetMapping("/users/stats")
    public ResponseEntity<Object> getUserStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("activeUsers", userService.countActiveUsers()); // Add count of active users

        return ResponseHandler.resBuilder("Thống kê người dùng đã được lấy thành công", HttpStatus.OK, stats);
    }

    // Optional: Thống kê số lượng người dùng đang hoạt động
    @GetMapping("/users/stats/active")
    public ResponseEntity<Object> getActiveUserCount() {
        long activeUserCount = userService.countActiveUsers();
        return ResponseHandler.resBuilder("Số lượng người dùng đang hoạt động đã được lấy thành công", HttpStatus.OK, activeUserCount);
    }
    @GetMapping("/users/stats/inactive")
    public ResponseEntity<Object> getInactiveUserCount() {
        long inactiveUserCount = userService.countInactiveUsers();
        return ResponseHandler.resBuilder("Số lượng người dùng đang không hoạt động đã được lấy thành công", HttpStatus.OK, inactiveUserCount);
    }
}
