package org.example.springmicroserviceshandson.repositories;

import org.example.springmicroserviceshandson.domain.PostStatus;
import org.example.springmicroserviceshandson.domain.entities.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @EntityGraph(attributePaths = {"category", "tags"})
    List<Post> findByCategoryIdAndTagsIdAndStatus(UUID categoryId, UUID tagId, PostStatus status);

    @EntityGraph(attributePaths = {"category", "tags"})
    List<Post> findByCategoryIdAndStatus(UUID categoryId, PostStatus status);

    @EntityGraph(attributePaths = {"category", "tags"})
    List<Post> findByTagsIdAndStatus(UUID tagId, PostStatus status);

    @EntityGraph(attributePaths = {"category", "tags"})
    List<Post> findByStatus(PostStatus postStatus);
}
