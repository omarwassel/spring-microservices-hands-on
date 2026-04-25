package org.example.springmicroserviceshandson.services;

import org.example.springmicroserviceshandson.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> listCategories();
    Category createCategory(Category category);
    Category findById(UUID id);
}
