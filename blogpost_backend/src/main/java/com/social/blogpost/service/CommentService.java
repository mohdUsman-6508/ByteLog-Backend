package com.social.blogpost.service;


import com.social.blogpost.exception.ResourceNotFoundException;
import com.social.blogpost.model.Comment;
import com.social.blogpost.repository.BlogPostRepo;
import com.social.blogpost.repository.CommentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepo commentRepository;
    private final BlogPostRepo blogPostRepository;

    public CommentService(CommentRepo commentRepository, BlogPostRepo blogPostRepository) {
        this.commentRepository = commentRepository;
        this.blogPostRepository = blogPostRepository;
    }

    @Transactional
    public Comment createComment(Comment comment) {
        comment.setCommentedAt(Instant.now());
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBlogPost(Long blogPostId) {
        return commentRepository.findByBlogPostId(blogPostId);
    }

    @Transactional
    public Comment updateComment(Long id, Comment updatedComment) {
        return commentRepository.findById(id)
                .map(existingComment -> {
                    existingComment.setText(updatedComment.getText());
                    return commentRepository.save(existingComment);
                }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
