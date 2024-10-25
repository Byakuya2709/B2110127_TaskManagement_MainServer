package ctu.demo.service;

import ctu.demo.model.Task;
import ctu.demo.model.TaskUpdate;
import ctu.demo.model.TaskUpdate.UpdateStatus;
import ctu.demo.repository.TaskRepository;
import ctu.demo.repository.TaskUpdateRepository;
import ctu.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskUpdateService {

    @Autowired
    private TaskUpdateRepository taskUpdateRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TaskUpdate> getAllTaskUpdates() {
        return taskUpdateRepository.findAll();
    }

    // Tạo một yêu cầu cập nhật trạng thái của Task
    public TaskUpdate createTaskUpdate(Long taskId, Long userId, Task.TaskStatus requestedStatus) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            throw new IllegalArgumentException("Task không tồn tại với ID: " + taskId);
        }

        Task task = optionalTask.get();
        TaskUpdate taskUpdate = new TaskUpdate();
        taskUpdate.setTask(task);
        taskUpdate.setUser(userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Người dùng không tồn tại với ID: " + userId)));
        taskUpdate.setRequestedStatus(requestedStatus);
        taskUpdate.setRequestedDate(new Date());

        return taskUpdateRepository.save(taskUpdate);
    }

    // Lấy tất cả các yêu cầu cập nhật theo trạng thái
    public List<TaskUpdate> getTaskUpdatesByStatus(UpdateStatus status) {
        return taskUpdateRepository.findByStatus(status);
    }

    // Admin duyệt yêu cầu cập nhật
    public TaskUpdate approveTaskUpdate(Long taskUpdateId, String adminComment) {
        TaskUpdate taskUpdate = getTaskUpdateById(taskUpdateId);
        taskUpdate.setStatus(UpdateStatus.APPROVED);
        taskUpdate.setAdminComment(adminComment);
        taskUpdate.getTask().setStatus(taskUpdate.getRequestedStatus());
        taskUpdate.getTask().setUpdatedDate(new Date());
        taskRepository.save(taskUpdate.getTask());
        return taskUpdateRepository.save(taskUpdate);
    }

    // Admin từ chối yêu cầu cập nhật
    public TaskUpdate rejectTaskUpdate(Long taskUpdateId, String adminComment) {
        TaskUpdate taskUpdate = getTaskUpdateById(taskUpdateId);
        taskUpdate.setStatus(UpdateStatus.REJECTED);
        taskUpdate.setAdminComment(adminComment);
        return taskUpdateRepository.save(taskUpdate);
    }

    // Lấy tất cả các yêu cầu cập nhật liên quan đến một task
    public List<TaskUpdate> getTaskUpdatesByTaskId(Long taskId) {
        return taskUpdateRepository.findByTaskId(taskId);
    }

    // Lấy thông tin chi tiết của một yêu cầu cập nhật theo ID
    public TaskUpdate getTaskUpdateById(Long taskUpdateId) {
        return taskUpdateRepository.findById(taskUpdateId).orElseThrow(
                () -> new IllegalArgumentException("Yêu cầu cập nhật không tồn tại với ID: " + taskUpdateId));
    }
}
