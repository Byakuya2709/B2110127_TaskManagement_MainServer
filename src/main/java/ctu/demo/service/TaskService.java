/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.service;

import ctu.demo.dto.TaskDTO;
import ctu.demo.model.Task;
import ctu.demo.model.User;
import ctu.demo.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
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
    // Lấy tất cả các task
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Lấy task theo ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Thêm hoặc cập nhật một task
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
     public Task saveTask(TaskDTO taskDTO) {
        Task task = this.toTask(taskDTO);
        return taskRepository.save(task);
    }
    // Xóa một task theo ID
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
     public  Task toTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDate(taskDTO.getDate());
        task.setStatus(taskDTO.getStatus());
        
        User user = userService.getUserById(taskDTO.getUserID());
        if (user != null) {
            task.setUser(user);
        } else {
        throw new UsernameNotFoundException("Người dùng không tồn tại");
        }
        task.setUser(user);
        
        return task;
    }

}
