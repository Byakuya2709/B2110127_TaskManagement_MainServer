/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ctu.demo.dto.TaskDTO;
import ctu.demo.dto.TaskResponse;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author admin
 */
@Entity
@Table(name="tasks")
public class Task implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String title;
    
    @Column
    private String description;
    
    @Column
    private Date date;
    
    @Column
    private TaskStatus status;
    
    @Column
    private Date createdDate; // Ngày tạo nhiệm vụ

    @Column
    private Date updatedDate; // Ngày cập nhật nhiệm vụ
    
    @ManyToOne
    @JoinColumn(name="user_id")
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public enum TaskStatus{
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELED
    }

    public Task() {
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    
    public static TaskDTO toTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDate(task.getDate());
        taskDTO.setStatus(task.getStatus());
        
        taskDTO.setUserId(task.getUser().getId().intValue());
        
        return taskDTO;
    }
     public static TaskResponse toTaskResponse(Task task) {
        TaskResponse taskDTO = new TaskResponse();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDate(task.getDate());
        taskDTO.setStatus(task.getStatus());
        
        taskDTO.setUserId(task.getUser().getId().intValue());
        taskDTO.setUserName(task.getUser().getFullname());
        taskDTO.setCreatedDate(task.getCreatedDate());
        taskDTO.setUpdatedDate(task.getCreatedDate());
                
        return taskDTO;
    }
}
