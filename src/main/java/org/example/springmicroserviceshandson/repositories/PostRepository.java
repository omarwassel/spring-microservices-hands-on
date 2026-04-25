package org.example.springmicroserviceshandson.repositories;

import org.example.springmicroserviceshandson.domain.entities.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @EntityGraph(attributePaths = {"category", "tags"})
    List<Post> findByCategoryIdAndTagsId(UUID categoryId, UUID tagId);

    @EntityGraph(attributePaths = {"category", "tags"})
    List<Post> findByCategoryId(UUID categoryId);

    @EntityGraph(attributePaths = {"category", "tags"})
    List<Post> findByTagsId(UUID tagId);
}
