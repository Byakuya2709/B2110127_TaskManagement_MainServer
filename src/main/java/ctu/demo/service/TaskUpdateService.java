package ctu.demo.service;

import ctu.demo.model.Task;
import ctu.demo.model.TaskUpdate;
import ctu.demo.model.TaskUpdate.UpdateStatus;
import ctu.demo.repository.TaskRepository;
import ctu.demo.repository.TaskUpdateRepository;
import ctu.demo.repository.UserRepository;
import ctu.demo.request.ConfirmUpdateRequest;
import ctu.demo.request.UpdatedTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

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
    public TaskUpdate createTaskUpdate(UpdatedTask updatedTask) {

        Optional<Task> optionalTask = taskRepository.findById((long) updatedTask.getTaskId());
        if (optionalTask.isEmpty()) {
            throw new IllegalArgumentException("Task không tồn tại với ID: " + updatedTask.getTaskId());
        }

        Task task = optionalTask.get();
        TaskUpdate taskUpdate = new TaskUpdate();
        taskUpdate.setTask(task);
        taskUpdate.setUser(userRepository.findById((long) updatedTask.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("Người dùng không tồn tại với ID: " + updatedTask.getUserId())));
        taskUpdate.setRequestedStatus(updatedTask.getUpdateStatus());
        taskUpdate.setDescription(updatedTask.getUpdateDescription());
        taskUpdate.setRequestedDate(new Date());

        return taskUpdateRepository.save(taskUpdate);
    }

    public boolean deleteTaskUpdate(Long id) {
        try {
            if (taskUpdateRepository.existsById(id)) {
                taskUpdateRepository.deleteById(id);
                return true;
            }
        } catch (DataIntegrityViolationException e) {
            // Log the error for debugging
            System.err.println("Error while deleting TaskUpdate: " + e.getMessage());
            // Handle specific errors based on your application's logic
        }
        return false;
    }

    // Lấy tất cả các yêu cầu cập nhật theo trạng thái
    public List<TaskUpdate> getTaskUpdatesByStatus(UpdateStatus status) {
        return taskUpdateRepository.findByStatus(status);
    }

    public TaskUpdate approveTaskUpdate(ConfirmUpdateRequest req) {
        TaskUpdate taskUpdate = getTaskUpdateById((long) req.getRequestId());

        if (taskUpdate == null || taskUpdate.getTask() == null) {
            throw new IllegalArgumentException("Invalid request: Task update not found or task is null.");
        }

        if (taskUpdate.getStatus() == TaskUpdate.UpdateStatus.APPROVED) 
            throw new RuntimeException("This request has been approved");
        
        Task task = taskUpdate.getTask();

        // Check for completed and approved status
        if (Task.TaskStatus.valueOf(req.getRequestStatus()) == Task.TaskStatus.COMPLETED
                && TaskUpdate.UpdateStatus.valueOf(req.getStatus()) == TaskUpdate.UpdateStatus.APPROVED) {

            task.setStatus(Task.TaskStatus.COMPLETED);
            taskRepository.save(task);

            updateTaskUpdate(taskUpdate, req);
            rejectOtherUpdates(taskUpdate, task.getId());

        } else if (isApprovedStatus(req.getStatus())) {
            if (req.getRequestStatus() != null) {
                task.setStatus(Task.TaskStatus.valueOf(req.getRequestStatus()));
            }
            taskRepository.save(task);

            updateTaskUpdate(taskUpdate, req);

        } else {
            throw new IllegalArgumentException("Invalid request status or update status for approval.");
        }

        return taskUpdate;
    }

    private boolean isApprovedStatus(String status) {
        try {
            return TaskUpdate.UpdateStatus.valueOf(status) == TaskUpdate.UpdateStatus.APPROVED;
        } catch (IllegalArgumentException e) {
            return false; // Status is invalid
        }
    }

    private void updateTaskUpdate(TaskUpdate taskUpdate, ConfirmUpdateRequest req) {
        taskUpdate.setStatus(TaskUpdate.UpdateStatus.APPROVED);
        if (req.getAdminComment() != null) {
            taskUpdate.setAdminComment(req.getAdminComment());
        }
        taskUpdate.setUpdatedDate(new Date());
        taskUpdateRepository.save(taskUpdate);
    }

    private void rejectOtherUpdates(TaskUpdate taskUpdate, Long taskId) {
        List<TaskUpdate> otherUpdates = taskUpdateRepository.findByTaskId(taskId);
        for (TaskUpdate otherUpdate : otherUpdates) {
            if (!otherUpdate.getId().equals(taskUpdate.getId())) {
                otherUpdate.setStatus(TaskUpdate.UpdateStatus.REJECTED);
                otherUpdate.setAdminComment("Tác vụ này đã hoàn thành");
                taskUpdateRepository.save(otherUpdate);
            }
        }
    }

    public TaskUpdate rejectTaskUpdate(ConfirmUpdateRequest req) {
        TaskUpdate taskUpdate = getTaskUpdateById((long) req.getRequestId());

        taskUpdate.setStatus(TaskUpdate.UpdateStatus.REJECTED);
        if (req.getAdminComment() != null) {
            taskUpdate.setAdminComment(req.getAdminComment());
        }
        taskUpdate.setUpdatedDate(new Date());

        taskUpdateRepository.save(taskUpdate);
        return taskUpdate;
    }

    // Lấy tất cả các yêu cầu cập nhật liên quan đến một task
    public List<TaskUpdate> getTaskUpdatesByTaskId(Long taskId) {
        return taskUpdateRepository.findByTaskId(taskId);
    }

    public List<TaskUpdate> getUpdatesByUserId(Long userId) {
        return taskUpdateRepository.findByUser_Id(userId);
    }

    // Lấy thông tin chi tiết của một yêu cầu cập nhật theo ID
    public TaskUpdate getTaskUpdateById(Long taskUpdateId) {
        return taskUpdateRepository.findById(taskUpdateId).orElseThrow(
                () -> new IllegalArgumentException("Yêu cầu cập nhật không tồn tại với ID: " + taskUpdateId));
    }
}
