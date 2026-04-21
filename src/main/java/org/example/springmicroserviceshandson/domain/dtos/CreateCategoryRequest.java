package org.example.springmicroserviceshandson.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

    @NotBlank(message = "Category Name is required")
    @Size(min = 5, max = 50, message = "Category Name should be between {min} and {max} characters")
    @Pattern(regexp =  "^[\\w\\s-]+$", message =  "Category Name can only contain letters, numbers, spaces, underscores, or hyphens")
    private String name;


}
