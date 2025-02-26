package com.social.blogpost.controller;

import com.social.blogpost.exception.ResourceNotFoundException;
import com.social.blogpost.model.BlogPost;
import com.social.blogpost.model.User;
import com.social.blogpost.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) return ResponseEntity.ok(user.get());
        else throw new ResourceNotFoundException("User not found with username " + username);
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/saveBlogPost/{blogPostId}/{userId}")
    public ResponseEntity<BlogPost> savePost(@PathVariable Long blogPostId, @PathVariable Long userId) {
        BlogPost savedBlogPost = userService.saveBlogPost(blogPostId, userId);
        return ResponseEntity.ok(savedBlogPost);
    }

    @GetMapping("/likeBlogPost/{blogPostId}/{userId}")
    public ResponseEntity<BlogPost> likePost(@PathVariable Long blogPostId, @PathVariable Long userId) {
        BlogPost likedBlogPost = userService.likeBlogPost(blogPostId, userId);
        return ResponseEntity.ok(likedBlogPost);
    }

}
