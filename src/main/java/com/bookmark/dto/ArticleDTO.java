package com.bookmark.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String subtitle;

    private String link;

    private String content;

    @NotBlank(message = "Author cannot be blank")
    private String author;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    private Long journalId;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
