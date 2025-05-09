package com.bookmark.dto;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    private String description;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}