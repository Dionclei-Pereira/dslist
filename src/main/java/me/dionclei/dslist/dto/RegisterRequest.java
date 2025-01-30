package me.dionclei.dslist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank @Size(min = 3, max = 20, message = "Name's size must be between 3 and 20") String name,@Email @NotBlank String email, @NotBlank @Size(min = 6, max = 16, message = "Password size must be between 6 and 16") String password) {
	
}
