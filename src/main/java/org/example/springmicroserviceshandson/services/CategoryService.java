package org.example.springmicroserviceshandson.services;

import jakarta.validation.Valid;
import org.example.springmicroserviceshandson.domain.dtos.categories.UpdateCategoryRequest;
import org.example.springmicroserviceshandson.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> listCategories();
    Category createCategory(Category category);
    Category findById(UUID id);

    void deleteCategory(UUID id);

    Category updateCategory(UUID id, UpdateCategoryRequest request);
}
