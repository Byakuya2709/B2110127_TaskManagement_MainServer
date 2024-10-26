/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.request;

import ctu.demo.model.Task;

/**
 *
 * @author ADMIN
 */
public class UpdatedTask {
    int taskId;
    int userId;
    String updateDescription;
    private Task.TaskStatus updateStatus;
    
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUpdateDescription() {
        return updateDescription;
    }

    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }

   
    public Task.TaskStatus getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Task.TaskStatus updateStatus) {
        this.updateStatus = updateStatus;
    }
    
    
}
