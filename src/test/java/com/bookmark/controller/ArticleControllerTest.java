package com.bookmark.controller;

import com.bookmark.dto.ArticleDTO;
import com.bookmark.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Autowired
    private ObjectMapper objectMapper;

    private ArticleDTO articleDTO;
    private List<ArticleDTO> articleDTOList;

    @BeforeEach
    void setUp() {
        articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setTitle("Understanding Microservices");
        articleDTO.setSubtitle("A deep dive");
        articleDTO.setContent("Detailed article about Microservices");
        articleDTO.setAuthor("Mayoori");
        articleDTO.setCategoryId(1L);
        articleDTO.setJournalId(1L);
        articleDTO.setCreatedDate(LocalDateTime.now());
        articleDTO.setUpdatedDate(LocalDateTime.now());

        ArticleDTO articleDTO2 = new ArticleDTO();
        articleDTO2.setId(2L);
        articleDTO2.setTitle("Cloud Computing Basics");
        articleDTO2.setSubtitle("Getting started");
        articleDTO2.setContent("Introduction to cloud computing");
        articleDTO2.setAuthor("John Doe");
        articleDTO2.setCategoryId(1L);
        articleDTO2.setJournalId(1L);
        articleDTO2.setCreatedDate(LocalDateTime.now());
        articleDTO2.setUpdatedDate(LocalDateTime.now());

        articleDTOList = Arrays.asList(articleDTO, articleDTO2);
    }

    @Test
    @DisplayName("GET /api/v1/articles - Get All Articles")
    void testGetAllArticles() throws Exception {
        when(articleService.getAllArticles()).thenReturn(articleDTOList);

        mockMvc.perform(get("/api/v1/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Articles retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("Understanding Microservices"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].title").value("Cloud Computing Basics"));
    }

    @Test
    @DisplayName("POST /api/v1/articles - Create Article")
    void testAddArticle() throws Exception {
        when(articleService.addArticle(any(ArticleDTO.class))).thenReturn(articleDTO);

        mockMvc.perform(post("/api/v1/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Article created successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Understanding Microservices"))
                .andExpect(jsonPath("$.data.author").value("Mayoori"))
                .andExpect(jsonPath("$.data.categoryId").value(1))
                .andExpect(jsonPath("$.data.journalId").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/articles/{id} - Get Article By ID")
    void testGetArticleById() throws Exception {
        when(articleService.getArticleById(1L)).thenReturn(articleDTO);

        mockMvc.perform(get("/api/v1/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Article retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Understanding Microservices"))
                .andExpect(jsonPath("$.data.author").value("Mayoori"))
                .andExpect(jsonPath("$.data.categoryId").value(1))
                .andExpect(jsonPath("$.data.journalId").value(1));
    }

    @Test
    @DisplayName("PUT /api/v1/articles/{id} - Update Article")
    void testUpdateArticle() throws Exception {
        ArticleDTO updatedArticle = new ArticleDTO();
        updatedArticle.setId(1L);
        updatedArticle.setTitle("Microservices Deep Dive");
        updatedArticle.setContent("Updated content");
        updatedArticle.setAuthor("Mayoori");
        updatedArticle.setCategoryId(1L);
        updatedArticle.setJournalId(1L);

        when(articleService.updateArticle(eq(1L), any(ArticleDTO.class))).thenReturn(updatedArticle);

        mockMvc.perform(put("/api/v1/articles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedArticle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Article updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Microservices Deep Dive"))
                .andExpect(jsonPath("$.data.author").value("Mayoori"))
                .andExpect(jsonPath("$.data.categoryId").value(1))
                .andExpect(jsonPath("$.data.journalId").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/articles/{id} - Delete Article")
    void testDeleteArticle() throws Exception {
        doNothing().when(articleService).deleteArticle(1L);

        mockMvc.perform(delete("/api/v1/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Article deleted successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
