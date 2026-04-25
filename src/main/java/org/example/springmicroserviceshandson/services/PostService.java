package org.example.springmicroserviceshandson.services;

import org.example.springmicroserviceshandson.domain.dtos.posts.CreatePostRequest;
import org.example.springmicroserviceshandson.domain.dtos.posts.UpdatePostRequest;
import org.example.springmicroserviceshandson.domain.entities.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getPosts(UUID categoryId, UUID tagId);
    Post getPostById(UUID id);
    Post createPost(CreatePostRequest request);
    Post updatePost(UUID id, UpdatePostRequest request);
    void deletePost(UUID id);
    List<Post> getDraftPosts();
}