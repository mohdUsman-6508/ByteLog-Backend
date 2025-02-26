package com.social.blogpost.service;

import com.social.blogpost.exception.ResourceNotFoundException;
import com.social.blogpost.model.BlogPost;
import com.social.blogpost.model.User;
import com.social.blogpost.repository.BlogPostRepo;
import com.social.blogpost.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    private final BlogPostRepo blogPostRepo;
    private final UserRepo userRepo;

    public BlogPostService(BlogPostRepo blogPostRepo, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.blogPostRepo = blogPostRepo;
    }

    @Transactional
    public BlogPost createBlogPost(BlogPost blogPost, Long userId) {
        Optional<User> existedUser = userRepo.findById(userId);
        if (existedUser.isPresent()) {
            blogPost.setCreatedAt(Instant.now());
            BlogPost post = blogPostRepo.save(blogPost);
            existedUser.get().getPosts().add(post);
            userRepo.save(existedUser.get());
            return post;
        } else {
            throw new ResourceNotFoundException("User not found with the id " + userId);
        }
    }

    @Transactional(readOnly = true)
    public Optional<BlogPost> getBlogPostById(Long id) {
        return blogPostRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepo.findAll();
    }

    @Transactional
    public BlogPost updateBlogPost(Long id, BlogPost updatedPost) {
        return blogPostRepo.findById(id).map(existingPost -> {
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setContent(updatedPost.getContent());
            return blogPostRepo.save(existingPost);
        }).orElseThrow(() -> new ResourceNotFoundException("BlogPost not found with id " + id));
    }

    @Transactional
    public void deleteBlogPost(Long id) {
        blogPostRepo.deleteById(id);
    }
}
