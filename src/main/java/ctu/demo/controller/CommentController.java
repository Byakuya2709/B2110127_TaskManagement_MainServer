/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.controller;

import ctu.demo.dto.CommentDTO;
import ctu.demo.model.Comment;
import ctu.demo.model.Task;
import ctu.demo.respone.ResponseHandler;
import ctu.demo.service.CommentService;
import ctu.demo.service.TaskService;
import ctu.demo.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    
    @PostMapping("/task/{taskId}")
     public ResponseEntity<?> createComment(@PathVariable Long taskId,@RequestBody CommentDTO cmtDTO) {
         System.out.println(cmtDTO.toString());
        Task task;
        Optional<Task> findedTask =taskService.getTaskById(taskId);
            if (findedTask.isEmpty()) {
        return ResponseHandler.resBuilder("Task không tồn tại", HttpStatus.CONFLICT, null);
    }
        try {
             Comment cmt = commentService.saveComment(taskId,cmtDTO);
            return ResponseHandler.resBuilder("Thêm nhận xét thàn công", HttpStatus.OK,cmt.toCommentDTO(cmt));
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi thực hiên comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
     @GetMapping("/task/{taskId}")
     public ResponseEntity<?> getAllComment(@PathVariable Long taskId) {
        Task task;
        Optional<Task> findedTask =taskService.getTaskById(taskId);
            if (findedTask.isEmpty()) {
        return ResponseHandler.resBuilder("Task không tồn tại", HttpStatus.CONFLICT, null);
    }
        try {
             // Lấy tất cả các comment liên quan đến taskId
        List<Comment> comments = commentService.getCommentByTaskId(taskId);
        
        // Chuyển đổi từ Comment sang CommentDTO
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOs.add(comment.toCommentDTO(comment));
        }
            return ResponseHandler.resBuilder("Lấy tất cả nhận xét theo tác vụ thành công", HttpStatus.OK,commentDTOs);
        } catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy comment theo task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    @DeleteMapping("/task/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        try{
            commentService.deleteComment(commentId);
            return ResponseHandler.resBuilder("Xóa nhận xét thành công", HttpStatus.OK,null);
        }catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi lấy xóa comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    @PutMapping("/task/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId,@RequestBody CommentDTO newComment){
  
        try{
            Comment cmt = commentService.updateComment(commentId,newComment);
            return ResponseHandler.resBuilder("Cập nhật nhận xét thành công", HttpStatus.OK,null);
        }catch (Exception e) {
            return ResponseHandler.resBuilder("Lỗi khi Cập nhật comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
