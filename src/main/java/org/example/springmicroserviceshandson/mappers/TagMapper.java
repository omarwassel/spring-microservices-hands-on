package org.example.springmicroserviceshandson.mappers;

import org.example.springmicroserviceshandson.domain.PostStatus;
import org.example.springmicroserviceshandson.domain.dtos.tags.TagDto;
import org.example.springmicroserviceshandson.domain.entities.Post;
import org.example.springmicroserviceshandson.domain.entities.Tag;

import java.util.List;
import java.util.Set;

public class TagMapper {

    public static TagDto toDto(Tag tag) {
        return TagDto
                .builder()
                .id(tag.getId())
                .name(tag.getName())
                .postCount(calculatePostsCount(tag.getPosts()))
                .build();
    }

    private static Long calculatePostsCount(Set<Post> posts) {
        if (null == posts) {
            return 0L;
        }
        return posts.stream().filter(post -> post.getStatus().equals(PostStatus.PUBLISHED)).count();
    }

    public static List<Tag> toEntities(List<String> tagNames) {
        if (tagNames == null) return List.of();
        return tagNames.stream()
                .map(TagMapper::toEntity) // Use the class name and the correct method name
                .toList();
    }

    public static Tag toEntity(String tagName) {
        return Tag
                .builder()
                .name(tagName)
                .build();
    }

}
