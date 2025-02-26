package com.social.blogpost.controller;


import com.social.blogpost.exception.ResourceNotFoundException;
import com.social.blogpost.model.Comment;
import com.social.blogpost.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) return ResponseEntity.ok(comment.get());
        else throw new ResourceNotFoundException("Comment not found with id " + id);
    }

    @GetMapping("/blogpost/{blogPostId}")
    public ResponseEntity<List<Comment>> getCommentByBlogPost(@PathVariable Long blogPostId) {
        List<Comment> comments = commentService.getCommentsByBlogPost(blogPostId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{commenterId}/{blogPostId}")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment,@PathVariable Long commenterId,@PathVariable Long blogPostId) {
        Comment createdComment = commentService.createComment(comment,commenterId,blogPostId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        Comment updatedComment = commentService.updateComment(id, comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
