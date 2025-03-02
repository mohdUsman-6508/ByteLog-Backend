package com.social.blogpost.repository;

import com.social.blogpost.model.Comment;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface CommentRepo extends Neo4jRepository<Comment, Long> {
    List<Comment> findByBlogPostId(Long blogPostId);
}
