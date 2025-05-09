package com.bookmark.controller;

import com.bookmark.dto.CategoryDTO;
import com.bookmark.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDTO categoryDTO;
    private List<CategoryDTO> categoryDTOList;

    @BeforeEach
    void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Technology");
        categoryDTO.setDescription("Articles on tech trends");

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName("Science");
        categoryDTO2.setDescription("Scientific articles");

        categoryDTOList = Arrays.asList(categoryDTO, categoryDTO2);
    }

    @Test
    @DisplayName("GET /api/v1/categories - Get All Categories")
    void testGetAllCategories() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Categories retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Technology"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("Science"));
    }

    @Test
    @DisplayName("POST /api/v1/categories - Create Category")
    void testAddCategory() throws Exception {
        when(categoryService.addCategory(any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category created successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Technology"))
                .andExpect(jsonPath("$.data.description").value("Articles on tech trends"));
    }

    @Test
    @DisplayName("GET /api/v1/categories/{id} - Get Category By ID")
    void testGetCategoryById() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(categoryDTO);

        mockMvc.perform(get("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Technology"))
                .andExpect(jsonPath("$.data.description").value("Articles on tech trends"));
    }

    @Test
    @DisplayName("PUT /api/v1/categories/{id} - Update Category")
    void testUpdateCategory() throws Exception {
        CategoryDTO updatedCategory = new CategoryDTO();
        updatedCategory.setId(1L);
        updatedCategory.setName("Tech & Innovation");
        updatedCategory.setDescription("Updated description");

        when(categoryService.updateCategory(eq(1L), any(CategoryDTO.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/api/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Tech & Innovation"))
                .andExpect(jsonPath("$.data.description").value("Updated description"));
    }

    @Test
    @DisplayName("DELETE /api/v1/categories/{id} - Delete Category")
    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category deleted successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
