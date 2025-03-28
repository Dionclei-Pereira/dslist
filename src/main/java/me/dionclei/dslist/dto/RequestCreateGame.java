package me.dionclei.dslist.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestCreateGame(
		
		@NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must be at most 100 characters")
        String title,

        @NotNull(message = "Year is required")
        @Min(value = 1950, message = "Year must be greater than or equal to 1950")
        @Max(value = 2100, message = "Year must be less than or equal to 2100")
        Integer year,

        @NotBlank(message = "Genre is required")
        @Size(max = 50, message = "Genre must be at most 50 characters")
        String genre,

        @NotBlank(message = "Platforms is required")
        @Size(max = 100, message = "Platforms must be at most 100 characters")
        String platforms,

        @NotNull(message = "Score is required")
        @DecimalMin(value = "0.0", message = "Score must be greater than or equal to 0.0")
        @DecimalMax(value = "10.0", message = "Score must be less than or equal to 10.0")
        Double score,

        @NotBlank(message = "Image URL is required")
        @Size(max = 255, message = "Image URL must be at most 255 characters")
        String imgUrl,

        @NotBlank(message = "Short description is required")
        @Size(max = 255, message = "Short description must be at most 255 characters")
        String shortDescription,

        @NotBlank(message = "Long description is required")
        String longDescription) {

}
