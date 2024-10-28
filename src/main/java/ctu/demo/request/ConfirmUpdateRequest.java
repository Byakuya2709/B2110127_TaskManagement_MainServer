/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.request;

/**
 *
 * @author admin
 */
public class ConfirmUpdateRequest {
    int requestId;
    int taskId;
    String requestStatus;
    String status;
    String adminComment;

    public ConfirmUpdateRequest() {
    }

    @Override
    public String toString() {
        return "ConfirmUpdateRequest{" + "requestId=" + requestId + ", taskId=" + taskId + ", requestStatus=" + requestStatus + ", status=" + status + ", adminComment=" + adminComment + '}';
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    
 
}
