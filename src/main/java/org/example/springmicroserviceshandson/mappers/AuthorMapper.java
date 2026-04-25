package org.example.springmicroserviceshandson.mappers;

import org.example.springmicroserviceshandson.domain.dtos.posts.AuthorDto;
import org.example.springmicroserviceshandson.domain.entities.User;

public class AuthorMapper {

    public static AuthorDto toDto(User author) {
        return AuthorDto
                .builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }


}