/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.service;

import ctu.demo.dto.TaskDTO;
import ctu.demo.model.Group;
import ctu.demo.model.Task;
import ctu.demo.model.User;
import ctu.demo.repository.GroupRepository;
import ctu.demo.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupRepository groupRepository;
    // Lấy tất cả các task
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Lấy task theo ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
     // Lấy task theo ID
    public Optional<Task> getTaskByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    // Thêm hoặc cập nhật một task
   
     public Task saveTask(TaskDTO taskDTO) {
        Task task = this.toTask(taskDTO);
        return taskRepository.save(task);
    }
    // Xóa một task theo ID
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
     public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
     
    public List<Task> getAllTasksByGroupId(Long groupId) {
        // Lấy danh sách các thành viên trong nhóm
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Group not found"));
        
        // Lấy tất cả các nhiệm vụ của các thành viên trong nhóm
        return group.getUsers().stream()
            .flatMap(user -> taskRepository.findByUserId(user.getId()).stream())
            .collect(Collectors.toList());
    }
     public  Task toTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDate(taskDTO.getDate());
        task.setStatus(taskDTO.getStatus());
        
        User user = userService.getUserById(Long.valueOf(taskDTO.getUserId()));
        if (user != null) {
            task.setUser(user);
        } else {
        throw new UsernameNotFoundException("Người dùng không tồn tại");
        }
        task.setUser(user);
        
        return task;
    }
     // Thống kê số lượng nhiệm vụ theo trạng thái
    public long countTasksByStatus(Task.TaskStatus status) {
        return taskRepository.countByStatus(status);
    }

    // Thống kê số lượng nhiệm vụ theo người dùng
    public long countTasksByUser(User user) {
        return taskRepository.countByUser(user);
    }

    // Thống kê số lượng nhiệm vụ hoàn thành theo người dùng
    public long countCompletedTasksByUser(User user) {
        return taskRepository.countByUserAndStatus(user, Task.TaskStatus.COMPLETED);
    }

    // Lấy danh sách nhiệm vụ theo trạng thái
    public List<Task> getTasksByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
}
