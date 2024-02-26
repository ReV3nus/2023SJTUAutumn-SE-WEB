package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.entity.Tag;
import com.example.mybookstore_backend.repository.TagRepository;
import com.example.mybookstore_backend.service.TagService;
import org.neo4j.driver.internal.value.ListValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    Neo4jClient neo4jClient;
    @Override
    public Tag getTagByName(String name){
        return tagRepository.findByName(name);
    }
    @Override
    public List<String> getRelatedTags(String tagName){
        List<Object> relatedTypeNames = tagRepository.getRelatedTypeNames(tagName);

        List<String> relatedTagNames = relatedTypeNames.stream()
                .map(item -> {
                    if (item instanceof ListValue) {
                        List<Object> listValueItems = ((ListValue) item).asList(Object::toString);
                        return listValueItems.stream().map(Object::toString).collect(Collectors.toList());
                    } else {
                        return Collections.singletonList(item.toString());
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return relatedTagNames.stream()
                .map(subString -> subString.replaceAll("\"", ""))
                .collect(Collectors.toList());
    }
}
