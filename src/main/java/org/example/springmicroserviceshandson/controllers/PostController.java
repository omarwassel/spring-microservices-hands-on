package org.example.springmicroserviceshandson.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.dtos.Post.CreatePostRequest;
import org.example.springmicroserviceshandson.domain.dtos.Post.PostDto;
import org.example.springmicroserviceshandson.domain.dtos.Post.UpdatePostRequest;
import org.example.springmicroserviceshandson.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
        List<PostDto> posts = postService.getPosts(categoryId, tagId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {
         PostDto post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequest request) {
         PostDto created = postService.createPost(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequest request) {
         PostDto updated = postService.updatePost(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
         postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}