package org.example.springmicroserviceshandson.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.dtos.posts.CreatePostRequest;
import org.example.springmicroserviceshandson.domain.dtos.posts.PostDto;
import org.example.springmicroserviceshandson.domain.dtos.posts.UpdatePostRequest;
import org.example.springmicroserviceshandson.domain.entities.Post;
import org.example.springmicroserviceshandson.mappers.PostMapper;
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
        List<Post> posts = postService.getPosts(categoryId, tagId);
        List<PostDto> dtoList = posts.stream().map(PostMapper::toDto).toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable UUID id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(PostMapper.toDto(post));
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> getDraftPosts() {
        List<Post> posts = postService.getDraftPosts();
        List<PostDto> dtoList = posts.stream().map(PostMapper::toDto).toList();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequest request) {
        Post post = postService.createPost(request);
        return new ResponseEntity<>(
                PostMapper.toDto(post),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequest request) {
        Post updatedPost = postService.updatePost(id, request);
        return ResponseEntity.ok(PostMapper.toDto(updatedPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}