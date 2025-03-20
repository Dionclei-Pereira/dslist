package me.dionclei.dslist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestCreateGameList(@NotBlank(message = "Name is required") @Size(min = 3, max = 25, message = "Size must be between 3 and 25") String name) {

}
