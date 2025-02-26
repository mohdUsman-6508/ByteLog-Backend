package com.social.blogpost.service;

import com.social.blogpost.exception.ResourceNotFoundException;
import com.social.blogpost.model.BlogPost;
import com.social.blogpost.model.User;
import com.social.blogpost.repository.BlogPostRepo;
import com.social.blogpost.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final BlogPostRepo blogPostRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, BlogPostRepo blogPostRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.blogPostRepo = blogPostRepo;
    }


    @Transactional
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        return userRepo.findById(id).map(existingUser -> {
            if (updatedUser.getUsername() != null) existingUser.setUsername(updatedUser.getUsername());
            if (updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null)
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            return userRepo.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public BlogPost saveBlogPost(Long blogPostId, Long userId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<BlogPost> post = blogPostRepo.findById(blogPostId);
        if (user.isPresent() && post.isPresent()) {
            user.get().getSavedPosts().add(post.get());
            userRepo.save(user.get());
            return post.get();
        } else {
            throw new ResourceNotFoundException("Post or user not found!");
        }
    }

    @Transactional
    public BlogPost likeBlogPost(Long blogPostId, Long userId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<BlogPost> post = blogPostRepo.findById(blogPostId);
        if (user.isPresent() && post.isPresent()) {
            user.get().getLikedPosts().add(post.get());
            userRepo.save(user.get());
            return post.get();
        } else {
            throw new ResourceNotFoundException("Post or user not found!");
        }
    }

}
