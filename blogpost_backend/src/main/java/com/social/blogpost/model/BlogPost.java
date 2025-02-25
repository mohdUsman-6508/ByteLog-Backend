package com.social.blogpost.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("BlogPost")
public class BlogPost {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private String author;
    private Instant createdAt;

    @Relationship(type = "HAS_COMMENT",direction=Relationship.Direction.OUTGOING)
    private Set<Comment> comments;

    @Relationship(type="HAS_IMAGE",direction=Relationship.Direction.OUTGOING)
    private Set<Image> image;

    @Relationship(type="HAS_TAG",direction=Relationship.Direction.OUTGOING)
    private Set<Tag> tags;

    public BlogPost(String title, String content, String author, Instant createdAt) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }
}
