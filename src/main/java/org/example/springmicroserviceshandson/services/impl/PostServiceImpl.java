package org.example.springmicroserviceshandson.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.dtos.CategoryDto;
import org.example.springmicroserviceshandson.domain.dtos.Post.CreatePostRequest;
import org.example.springmicroserviceshandson.domain.dtos.Post.PostDto;
import org.example.springmicroserviceshandson.domain.dtos.Post.UpdatePostRequest;
import org.example.springmicroserviceshandson.domain.dtos.TagDto;
import org.example.springmicroserviceshandson.domain.entities.Category;
import org.example.springmicroserviceshandson.domain.entities.Post;
import org.example.springmicroserviceshandson.domain.entities.Tag;
import org.example.springmicroserviceshandson.domain.entities.User;
import org.example.springmicroserviceshandson.repositories.CategoryRepository;
import org.example.springmicroserviceshandson.repositories.PostRepository;
import org.example.springmicroserviceshandson.repositories.TagRepository;
import org.example.springmicroserviceshandson.repositories.UserRepository;
import org.example.springmicroserviceshandson.services.PostService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<PostDto> getPosts(UUID categoryId, UUID tagId) {
        List<Post> posts;
        if (categoryId != null && tagId != null) {
            posts = postRepository.findByCategoryIdAndTagsId(categoryId, tagId);
        } else if (categoryId != null) {
            posts = postRepository.findByCategoryId(categoryId);
        } else if (tagId != null) {
            posts = postRepository.findByTagsId(tagId);
        } else {
            posts = postRepository.findAll();
        }

        return posts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostDto getPostById(UUID id) {
        return postRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    @Override
    public PostDto createPost(CreatePostRequest request) {


        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        List<Tag> tags = tagRepository.findAllById(request.getTagIds());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Post post = Post.builder()
                .author(author)
                .title(request.getTitle())
                .content(request.getContent())
                .status(request.getStatus())
                .category(category)
                .tags(new HashSet<>(tags))
                .build();

        return mapToResponse(postRepository.save(post));
    }

    @Override
    public PostDto updatePost(UUID id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        List<Tag> tags = tagRepository.findAllById(request.getTagIds());

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus());
        post.setCategory(category);
        post.setTags(new HashSet<>(tags));

        return mapToResponse(postRepository.save(post));
    }

    @Override
    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete: Post not found");
        }
        postRepository.deleteById(id);
    }

    private PostDto mapToResponse(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .author(PostDto.AuthorDto
                        .builder()
                        .id(post.getAuthor().getId())
                        .name(post.getAuthor().getName())
                        .build()
                )
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .category(CategoryDto
                        .builder()
                        .id(post.getCategory().getId())
                        .name(post.getCategory().getName())
                        .build())
                .tags(post
                        .getTags()
                        .stream()
                        .map(t -> TagDto
                                .builder()
                                .id(t.getId())
                                .name(t.getName())
                                .build()
                        )
                        .collect(Collectors.toList()))
                .build();
    }
}
