package com.social.blogpost.repository;

import com.social.blogpost.model.BlogPost;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

public interface BlogPostRepo extends Neo4jRepository<BlogPost, Long> {

    Optional<BlogPost> findByTitle(String title);

    List<BlogPost> findByAuthor(String author);
}
