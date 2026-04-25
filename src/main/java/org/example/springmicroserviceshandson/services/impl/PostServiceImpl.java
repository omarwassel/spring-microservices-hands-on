package org.example.springmicroserviceshandson.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.PostStatus;
import org.example.springmicroserviceshandson.domain.dtos.posts.CreatePostRequest;
import org.example.springmicroserviceshandson.domain.dtos.posts.UpdatePostRequest;
import org.example.springmicroserviceshandson.domain.entities.Category;
import org.example.springmicroserviceshandson.domain.entities.Post;
import org.example.springmicroserviceshandson.domain.entities.Tag;
import org.example.springmicroserviceshandson.domain.entities.User;
import org.example.springmicroserviceshandson.repositories.PostRepository;
import org.example.springmicroserviceshandson.services.CategoryService;
import org.example.springmicroserviceshandson.services.PostService;
import org.example.springmicroserviceshandson.services.TagService;
import org.example.springmicroserviceshandson.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final CategoryService categoryService;
    private final TagService tagService;
    private final UserService userService;

    @Override
    @Transactional
    public List<Post> getPosts(UUID categoryId, UUID tagId) {
        List<Post> posts;
        if (categoryId != null && tagId != null) {
            posts = postRepository.findByCategoryIdAndTagsIdAndStatus(categoryId, tagId, PostStatus.PUBLISHED);
        } else if (categoryId != null) {
            posts = postRepository.findByCategoryIdAndStatus(categoryId, PostStatus.PUBLISHED);
        } else if (tagId != null) {
            posts = postRepository.findByTagsIdAndStatus(tagId, PostStatus.PUBLISHED);
        } else {
            posts = postRepository.findByStatus(PostStatus.PUBLISHED);
        }
        return posts;
    }

    @Override
    public List<Post> getDraftPosts() {
        return postRepository.findByStatus(PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post getPostById(UUID id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    @Override
    public Post createPost(CreatePostRequest request) {
        Category category = categoryService.findById(request.getCategoryId());
        List<Tag> tags = tagService.findAllById(request.getTagIds());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userService.findByEmail(email);
        Post post = Post
                .builder()
                .author(author)
                .title(request.getTitle())
                .content(request.getContent())
                .status(request.getStatus())
                .category(category)
                .tags(new HashSet<>(tags))
                .build();
        postRepository.save(post);
        return post;
    }

    @Override
    public Post updatePost(UUID id, UpdatePostRequest request) {
        Post post = getPostById(id);
        Category category = categoryService.findById(request.getCategoryId());
        List<Tag> tags = tagService.findAllById(request.getTagIds());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus());
        post.setCategory(category);
        post.setTags(new HashSet<>(tags));
        return post;
    }

    @Override
    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete: Post not found");
        }
        postRepository.deleteById(id);
    }

}
