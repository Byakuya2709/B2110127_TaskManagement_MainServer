/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.dto;

import java.util.Date;

/**
 *
 * @author admin
 */
public class CommentDTO {
    private Long id;
    private String content;
    private Date createdDate;
    private Long userId;
    private String userName;
    private Long taskId;
    private String taskTitle;

    public CommentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getUserName() {
        return userName;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    @Override
    public String toString() {
        return "CommentDTO{" + "id=" + id + ", content=" + content + ", createdDate=" + createdDate + ", userId=" + userId + ", userName=" + userName + ", taskId=" + taskId + ", taskTitle=" + taskTitle + '}';
    }
    
   
    
}
