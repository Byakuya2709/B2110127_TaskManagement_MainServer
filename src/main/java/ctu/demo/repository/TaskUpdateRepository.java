package ctu.demo.repository;

import ctu.demo.model.TaskUpdate;
import ctu.demo.model.TaskUpdate.UpdateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskUpdateRepository extends JpaRepository<TaskUpdate, Long> {
    List<TaskUpdate> findByStatus(UpdateStatus status); // Tìm các yêu cầu theo trạng thái (chờ duyệt, được chấp nhận, bị từ chối)

    List<TaskUpdate> findByTaskId(Long taskId); // Tìm các yêu cầu cập nhật liên quan đến một Task cụ thể
}

