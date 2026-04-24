package org.example.springmicroserviceshandson.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springmicroserviceshandson.domain.dtos.TagRequest;
import org.example.springmicroserviceshandson.domain.dtos.TagResponse;
import org.example.springmicroserviceshandson.domain.entities.Tag;
import org.example.springmicroserviceshandson.mappers.TagMapper;
import org.example.springmicroserviceshandson.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getTags() {
        List<Tag> tags = tagService.getAllTags();
        log.info("Returning list of tags {}", tags);
        List<TagResponse> tagResponse = tags.stream().map(TagMapper::toDto).toList();
        return ResponseEntity.ok(tagResponse);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(
            @Valid @RequestBody TagRequest tagRequest
    ) {
        List<Tag> tags = tagService.createTags(tagRequest.getNames());
        List<TagResponse> tagResponse = tags.stream().map(TagMapper::toDto).toList();
        return ResponseEntity.ok(tagResponse);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteTag(@PathVariable("id") UUID id) {
        tagService.deleteTag(id);
        String message = String.format("Tag with id %s deleted successfully", id);
        return ResponseEntity.ok(message);
    }
}
