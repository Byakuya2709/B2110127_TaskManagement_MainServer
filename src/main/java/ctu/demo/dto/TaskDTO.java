/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.dto;

import ctu.demo.model.Task;
import ctu.demo.model.Task.TaskStatus;
import java.util.Date;

/**
 *
 * @author admin
 */
public class TaskDTO {

    private String title;

    private String description;
    
    private Date date;
    
    private TaskStatus status;
    
    private int userId;
    
    private Date createdDate; // Ngày tạo nhiệm vụ

    private Date updatedDate; // Ngày cập nhật nhiệm vụ

    public String getTitle() {
        return title;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    @Override
    public String toString() {
        return "TaskDTO{" + "title=" + title + ", description=" + description + ", date=" + date + ", status=" + status + ", userId=" + userId + '}';
    }
    
  
}
