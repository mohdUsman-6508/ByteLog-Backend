package com.social.blogpost.service;

import com.social.blogpost.exception.ResourceNotFoundException;
import com.social.blogpost.model.BlogPost;
import com.social.blogpost.repository.BlogPostRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    private final BlogPostRepo blogPostRepo;

    public BlogPostService(BlogPostRepo blogPostRepo) {
        this.blogPostRepo = blogPostRepo;
    }

    @Transactional
    public BlogPost createBlogPost(BlogPost blogPost) {
        blogPost.setCreatedAt(Instant.now());
        return blogPostRepo.save(blogPost);
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
