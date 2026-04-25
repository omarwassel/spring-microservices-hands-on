package org.example.springmicroserviceshandson.domain.dtos.tags;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagRequest {

    @NotEmpty(message = "The names list cannot be empty")
    @Size(min = 1, message = "Please provide at least one tag name")
    private List<
            @NotBlank(message = "Tag name cannot be blank")
            @Size(max = 20, message = "Tag name must be under 20 characters")
                    String> names;
}