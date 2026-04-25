package org.example.springmicroserviceshandson.mappers;

import org.example.springmicroserviceshandson.domain.PostStatus;
import org.example.springmicroserviceshandson.domain.dtos.categories.CategoryDto;
import org.example.springmicroserviceshandson.domain.dtos.categories.CreateCategoryRequest;
import org.example.springmicroserviceshandson.domain.entities.Category;
import org.example.springmicroserviceshandson.domain.entities.Post;

import java.util.List;

public class CategoryMapper {

    public static CategoryDto toDto(Category category){
        return CategoryDto
                .builder()
                .id(category.getId())
                .name(category.getName())
                .postcount(calculatePostsCount(category.getPosts()))
                .build();
    }

    private static Long calculatePostsCount(List<Post> posts){
        if(null == posts){ return 0L;}
        return posts.stream().filter(post -> post.getStatus().equals(PostStatus.PUBLISHED)).count();
    }

    public static Category toEntity(CreateCategoryRequest createCategoryRequest){
        return Category
                .builder()
                .name(createCategoryRequest.getName())
                .build();
    }

}
