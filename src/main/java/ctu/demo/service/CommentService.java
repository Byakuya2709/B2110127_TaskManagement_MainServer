/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.service;

import ctu.demo.model.Comment;
import ctu.demo.repository.CommentRepository;
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
    
}
