package org.example.springmicroserviceshandson.services;

import org.example.springmicroserviceshandson.domain.entities.Tag;

import java.util.List;
import java.util.UUID;

public interface TagService {
    List<Tag> getAllTags();
    List<Tag> createTags(List<String> tagsNames);
    void deleteTag(UUID id);

    List<Tag> findAllById(List<UUID> id);
}
