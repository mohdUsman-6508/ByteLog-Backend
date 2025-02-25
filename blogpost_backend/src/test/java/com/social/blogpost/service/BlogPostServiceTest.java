package com.social.blogpost.service;

import com.social.blogpost.model.BlogPost;
import com.social.blogpost.repository.BlogPostRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogPostServiceTest {

    @Mock
    private BlogPostRepo blogPostRepository;

    @InjectMocks
    private BlogPostService blogPostService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateBlogPost() {
        BlogPost post = new BlogPost("Test Title", "Test Content", "author1", Instant.now());
        when(blogPostRepository.save(any(BlogPost.class))).thenReturn(post);
        BlogPost created = blogPostService.createBlogPost(post);
        assertNotNull(created);
        assertEquals("Test Title", created.getTitle());
        verify(blogPostRepository, times(1)).save(post);
    }

    @Test
    void testGetAllBlogPosts(){
        BlogPost post1 = new BlogPost("Title1", "Content1", "author1", Instant.now());
        BlogPost post2 = new BlogPost("Title2", "Content2", "author2", Instant.now());

        List<BlogPost> posts= Arrays.asList(post1,post2);
        when(blogPostRepository.findAll()).thenReturn(posts);
        List<BlogPost> result=blogPostService.getAllBlogPosts();
        assertEquals(2,result.size());
        assertNotNull(result);
        verify(blogPostRepository,times(1)).findAll();
    }

}
