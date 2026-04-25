package org.example.springmicroserviceshandson.domain.dtos.Post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdatePostRequest extends CreatePostRequest {

    @NotBlank(message = "ID is required for update")
    private UUID id;

}
