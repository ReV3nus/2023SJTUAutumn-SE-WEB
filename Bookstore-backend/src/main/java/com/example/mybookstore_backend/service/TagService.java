package com.example.mybookstore_backend.service;

import com.example.mybookstore_backend.entity.Tag;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface TagService {
    Tag getTagByName(String name);
    List<String> getRelatedTags(String tagName);
}
