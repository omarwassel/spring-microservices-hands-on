package org.example.springmicroserviceshandson.services;

import org.example.springmicroserviceshandson.domain.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> listCategories();
    Category createCategory(Category category);
}
