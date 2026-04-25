package org.example.springmicroserviceshandson.domain.dtos.Post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springmicroserviceshandson.domain.PostStatus;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    @NotEmpty(message = "At least one tag is required")
    private List<UUID> tagIds;

    @NotNull(message = "Status is required (DRAFT or PUBLISHED)")
    private PostStatus status;
}