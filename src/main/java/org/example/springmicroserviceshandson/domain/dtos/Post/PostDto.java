package org.example.springmicroserviceshandson.domain.dtos.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springmicroserviceshandson.domain.PostStatus;
import org.example.springmicroserviceshandson.domain.dtos.CategoryDto;
import org.example.springmicroserviceshandson.domain.dtos.TagDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private UUID id;
    private String title;
    private String content;
    private AuthorDto author;
    private CategoryDto category;
    private List<TagDto> tags;
    private Integer readingTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PostStatus status;

    @Data
    @Builder
    public static class AuthorDto {
        private UUID id;
        private String name;
    }
}