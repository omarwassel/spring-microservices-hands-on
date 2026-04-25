package org.example.springmicroserviceshandson.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.domain.dtos.categories.CategoryDto;
import org.example.springmicroserviceshandson.domain.dtos.categories.CreateCategoryRequest;
import org.example.springmicroserviceshandson.domain.entities.Category;
import org.example.springmicroserviceshandson.mappers.CategoryMapper;
import org.example.springmicroserviceshandson.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        List<CategoryDto> categoriesDto = categoryService.listCategories()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
        return ResponseEntity.ok(categoriesDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CreateCategoryRequest categoryRequest) {
        Category categoryToCreate = CategoryMapper.toEntity(categoryRequest);
        Category savedCategory = categoryService.createCategory(categoryToCreate);
        return new ResponseEntity<>(
                CategoryMapper.toDto(savedCategory),
                HttpStatus.CREATED
        );
    }

}
