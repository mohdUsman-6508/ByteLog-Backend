package com.social.blogpost.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("User")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;

    @Relationship(type = "AUTHORED", direction = Relationship.Direction.OUTGOING)
    private Set<BlogPost> posts;

    @Relationship(type = "LIKED", direction = Relationship.Direction.OUTGOING)
    private Set<BlogPost> likedPosts;

    @Relationship(type = "SAVED", direction = Relationship.Direction.OUTGOING)
    private Set<BlogPost> savedPosts;

    @Relationship(type = "WROTE", direction = Relationship.Direction.OUTGOING)
    private Set<Comment> comments;

}
