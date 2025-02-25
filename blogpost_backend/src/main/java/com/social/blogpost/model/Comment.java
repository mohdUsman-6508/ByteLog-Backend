package com.social.blogpost.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("Comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private Long commenterId;
    private String text;
    private Instant commentedAt;

    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private BlogPost blogPost;

}
