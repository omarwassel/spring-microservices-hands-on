package org.example.springmicroserviceshandson.domain.dtos.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCategoryRequest {

    @NotNull(message = "ID is required for update")
    private UUID id;

    @NotBlank(message = "name is required for update")
    private String name;

}
