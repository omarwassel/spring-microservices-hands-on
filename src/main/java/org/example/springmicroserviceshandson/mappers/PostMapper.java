package org.example.springmicroserviceshandson.mappers;

import org.example.springmicroserviceshandson.domain.dtos.posts.PostDto;
import org.example.springmicroserviceshandson.domain.entities.Post;

import java.util.stream.Collectors;

public class PostMapper {

    public static PostDto toDto(Post post) {
        return PostDto
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(AuthorMapper.toDto(post.getAuthor()))
                .category(CategoryMapper.toDto(post.getCategory()))
                .tags(post.getTags().stream().map(TagMapper::toDto).collect(Collectors.toSet()))
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .status(post.getStatus())
                .build();
    }
}