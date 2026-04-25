package org.example.springmicroserviceshandson.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.entities.Tag;
import org.example.springmicroserviceshandson.mappers.TagMapper;
import org.example.springmicroserviceshandson.repositories.TagRepository;
import org.example.springmicroserviceshandson.services.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getAllTags() {
        return tagRepository.findAllWithPosts();
    }

    @Override
    @Transactional
    public List<Tag> createTags(List<String> tagsNames) {
        Set<String> uniqueNames = tagsNames.stream()
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toSet());
        List<Tag> tagsToSave = TagMapper.toEntities(uniqueNames.stream().toList());
        return tagRepository.saveAll(tagsToSave);
    }

    @Override
    @Transactional
    public void deleteTag(UUID id) {
        if (!tagRepository.existsById(id)) {
            throw new EntityNotFoundException("Tag not found with id: " + id);
        }
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> findAllById(List<UUID> ids) {
        return tagRepository.findAllById(ids);
    }
}