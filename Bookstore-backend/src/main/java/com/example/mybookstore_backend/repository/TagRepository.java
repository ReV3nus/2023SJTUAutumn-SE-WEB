package com.example.mybookstore_backend.repository;

import com.example.mybookstore_backend.entity.Tag;
import org.neo4j.driver.internal.value.ListValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends Neo4jRepository<Tag, Long> {
    Tag findByName(String name);
    @Query("MATCH (tag:Tag {name: $tagName})-[:Related]->(related:Tag) RETURN COLLECT(DISTINCT tag.name) + COLLECT(related.name)")
    List<Object> getRelatedTypeNames(String tagName);

}
