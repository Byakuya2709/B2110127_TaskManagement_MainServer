/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.service;

import ctu.demo.dto.CommentDTO;
import ctu.demo.model.Comment;
import ctu.demo.model.Task;
import ctu.demo.model.User;
import ctu.demo.repository.CommentRepository;
import ctu.demo.repository.TaskRepository;
import ctu.demo.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
     @Autowired
    private TaskRepository taskRepository;
       @Autowired
    private UserRepository userRepository;
    // Create a new comment
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
    

    // Get a comment by its ID
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }
    
    public List<Comment> getCommentByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
    // Get all comments
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // Delete a comment by ID
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    // Update an existing comment
    public Comment updateComment(Long id, Comment newCommentData) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setContent(newCommentData.getContent());
                    comment.setCreatedDate(newCommentData.getCreatedDate());
                    // You can update other fields as needed
                    return commentRepository.save(comment);
                }).orElseThrow(() -> new RuntimeException("Comment not found"));
    }
    public Comment toComment(Long taskId,CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreatedDate(new Date()); // giả sử bạn muốn gán ngày hiện tại

        // Fetch Task và User từ các repository/service
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task ID: " + commentDTO.getTaskId()));
        comment.setTask(task);

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User ID: " + commentDTO.getUserId()));
        comment.setUser(user);

        return comment;
    }

    public Comment saveComment(long id,CommentDTO commentDTO) {
        Comment comment = toComment(id,commentDTO);
        return commentRepository.save(comment);
    }
}
