package com.bookmark.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class JournalDTO {
    private Long id;

    @NotNull(message = "Year cannot be null")
    private Integer year;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description;
}