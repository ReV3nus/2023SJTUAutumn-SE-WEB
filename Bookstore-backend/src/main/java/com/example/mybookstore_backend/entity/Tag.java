package com.example.mybookstore_backend.entity;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Node("Tag")
@Data
public class Tag {
    @Id
    private Long id;

    private String name;
    @Relationship(type = "Related", direction = Relationship.Direction.OUTGOING)
    private List<Tag> relatedTags;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }


}