package ctu.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "task_update")
public class TaskUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @JsonIgnore
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private Task.TaskStatus requestedStatus; // Trạng thái mà người dùng muốn cập nhật

    @Column(nullable = false)
    private Date requestedDate; // Ngày người dùng yêu cầu cập nhật

    @Column
    private String adminComment; // Nhận xét từ admin về yêu cầu

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UpdateStatus status; // Trạng thái của yêu cầu cập nhật (chờ duyệt, được chấp nhận, bị từ chối)

    public enum UpdateStatus {
        PENDING, // Yêu cầu đang chờ xử lý
        APPROVED, // Yêu cầu đã được chấp thuận
        REJECTED // Yêu cầu bị từ chối
    }

    public TaskUpdate() {
        this.status = UpdateStatus.PENDING; // Mặc định là đang chờ xử lý khi tạo yêu cầu
        this.requestedDate = new Date(); // Lưu thời gian yêu cầu
    }

    // Getters và setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task.TaskStatus getRequestedStatus() {
        return requestedStatus;
    }

    public void setRequestedStatus(Task.TaskStatus requestedStatus) {
        this.requestedStatus = requestedStatus;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public UpdateStatus getStatus() {
        return status;
    }

    public void setStatus(UpdateStatus status) {
        this.status = status;
    }

    
    
//    // Phương thức để xử lý khi admin phê duyệt yêu cầu
//    public void approve() {
//        if (this.status == UpdateStatus.PENDING) {
//            this.status = UpdateStatus.APPROVED;
//            this.task.setStatus(this.requestedStatus); // Cập nhật trạng thái của task
//        }
//    }
//
//    // Phương thức để xử lý khi admin từ chối yêu cầu
//    public void reject(String comment) {
//        if (this.status == UpdateStatus.PENDING) {
//            this.status = UpdateStatus.REJECTED;
//            this.adminComment = comment; // Lưu nhận xét của admin khi từ chối
//        }
//    }
}
