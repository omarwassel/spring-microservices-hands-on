package org.example.springmicroserviceshandson.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.entities.Category;
import org.example.springmicroserviceshandson.repositories.CategoryRepository;
import org.example.springmicroserviceshandson.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(isExists)
            throw new IllegalArgumentException("Category with name " + CategoryToCreate.getName() + " already exists");
        return categoryRepository.save(CategoryToCreate);
    }
}
