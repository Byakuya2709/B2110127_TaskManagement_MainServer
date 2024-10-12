/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ctu.demo.repository;

import ctu.demo.model.Task;
import ctu.demo.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface TaskRepository extends JpaRepository<Task,Long>{
     List<Task> findByUserId(Long userId);
     Optional<Task> findByTitle(String title);
      // Đếm số lượng nhiệm vụ theo trạng thái
    // Tìm nhiệm vụ theo ID người dùng

    // Đếm số lượng nhiệm vụ theo trạng thái
    long countByStatus(Task.TaskStatus status);

    // Đếm số lượng nhiệm vụ theo người dùng
    long countByUser(User user);

    // Đếm số lượng nhiệm vụ hoàn thành theo người dùng
    long countByUserAndStatus(User user, Task.TaskStatus status);

    // Lấy danh sách nhiệm vụ theo trạng thái
    List<Task> findByStatus(Task.TaskStatus status);
}
