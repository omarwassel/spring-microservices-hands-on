package org.example.springmicroserviceshandson.domain.dtos.posts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdatePostRequest extends CreatePostRequest {

    @NotNull(message = "ID is required for update")
    private UUID id;

}
