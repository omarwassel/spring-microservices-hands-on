package org.example.springmicroserviceshandson.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.dtos.categories.UpdateCategoryRequest;
import org.example.springmicroserviceshandson.domain.entities.Category;
import org.example.springmicroserviceshandson.repositories.CategoryRepository;
import org.example.springmicroserviceshandson.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPosts();
    }

    @Override
    @Transactional
    public Category createCategory(Category CategoryToCreate) {
        boolean isExists = categoryRepository.existsByNameIgnoreCase(CategoryToCreate.getName());
        if (isExists)
            throw new IllegalArgumentException("Category with name " + CategoryToCreate.getName() + " already exists");
        return categoryRepository.save(CategoryToCreate);
    }

    @Override
    @Transactional
    public Category findById(UUID id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    @Transactional
    public Category updateCategory(UUID id, UpdateCategoryRequest request) {
        Category category = findById(id);
        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        Category category = findById(id);
        if (!category.getPosts().isEmpty()){
            throw new IllegalStateException("Category with id " + id + " has posts");
        }
        categoryRepository.deleteById(id);
    }

}
