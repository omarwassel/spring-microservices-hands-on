package org.example.springmicroserviceshandson.repositories;

import org.example.springmicroserviceshandson.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // using JPQL
    //JOIN FETCH (which is an inner join), any category that has zero posts will be excluded from the results entirely.
    @Query("select Distinct c from Category c LEFT JOIN FETCH c.posts")
    List<Category> findAllWithPosts();

    boolean existsByNameIgnoreCase(String name);
}
