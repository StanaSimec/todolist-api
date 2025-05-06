package com.simec.todolistapi.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record TodoRequestDto(@NotBlank(message = "title is required")
                             @Length(min = 5, max = 50, message = "title length should be between 5 to 50 characters")
                             String title,
                             @NotBlank(message = "description is required")
                             @Length(min = 5, max = 100,
                                     message = "description length should be between 5 to 100 characters")
                             String description) {
}
