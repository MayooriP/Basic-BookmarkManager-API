package com.bookmark.controller;

import com.bookmark.dto.ApiResponse;
import com.bookmark.dto.ArticleDTO;
import com.bookmark.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleDTO>>> getAllArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(ApiResponse.success("Articles retrieved successfully", articles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleDTO>> getArticleById(@PathVariable Long id) {
        ArticleDTO article = articleService.getArticleById(id);
        return ResponseEntity.ok(ApiResponse.success("Article retrieved successfully", article));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ArticleDTO>> addArticle(@Validated @RequestBody ArticleDTO articleDTO) {
        ArticleDTO savedArticle = articleService.addArticle(articleDTO);
        return new ResponseEntity<>(ApiResponse.success("Article created successfully", savedArticle), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleDTO>> updateArticle(@PathVariable Long id, @Validated @RequestBody ArticleDTO articleDTO) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleDTO);
        return ResponseEntity.ok(ApiResponse.success("Article updated successfully", updatedArticle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok(ApiResponse.success("Article deleted successfully", null));
    }
}