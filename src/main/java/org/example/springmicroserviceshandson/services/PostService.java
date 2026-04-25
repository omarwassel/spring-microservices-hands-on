package org.example.springmicroserviceshandson.services;

import org.example.springmicroserviceshandson.domain.dtos.Post.CreatePostRequest;
import org.example.springmicroserviceshandson.domain.dtos.Post.PostDto;
import org.example.springmicroserviceshandson.domain.dtos.Post.UpdatePostRequest;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostDto> getPosts(UUID categoryId, UUID tagId);
    PostDto getPostById(UUID id);
    PostDto createPost(CreatePostRequest request);
    PostDto updatePost(UUID id, UpdatePostRequest request);
    void deletePost(UUID id);
}