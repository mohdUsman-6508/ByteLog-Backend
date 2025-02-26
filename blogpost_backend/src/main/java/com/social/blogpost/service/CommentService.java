package com.social.blogpost.service;


import com.social.blogpost.exception.ResourceNotFoundException;
import com.social.blogpost.model.BlogPost;
import com.social.blogpost.model.Comment;
import com.social.blogpost.model.User;
import com.social.blogpost.repository.BlogPostRepo;
import com.social.blogpost.repository.CommentRepo;
import com.social.blogpost.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepo commentRepository;
    private final BlogPostRepo blogPostRepository;
    private final UserRepo userRepository;

    public CommentService(CommentRepo commentRepository, BlogPostRepo blogPostRepository, UserRepo userRepository) {
        this.commentRepository = commentRepository;
        this.blogPostRepository = blogPostRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Comment createComment(Comment comment, Long commenterId, Long blogPostId) {
        Optional<BlogPost> post = blogPostRepository.findById(blogPostId);
        Optional<User> commenter = userRepository.findById(commenterId);
        if (post.isPresent() && commenter.isPresent()) {
            comment.setCommentedAt(Instant.now());
            comment.setCommenterId(commenterId);
            comment.setBlogPostId(blogPostId);
            Comment createdComment = commentRepository.save(comment);
            post.get().getComments().add(comment);
            commenter.get().getComments().add(comment);
            userRepository.save(commenter.get());
            blogPostRepository.save(post.get());
            return createdComment;
        } else throw new ResourceNotFoundException("User or post not found!");
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
