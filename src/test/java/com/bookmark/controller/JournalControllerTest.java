package com.bookmark.controller;

import com.bookmark.dto.JournalDTO;
import com.bookmark.service.JournalService;
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

@WebMvcTest(JournalController.class)
public class JournalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JournalService journalService;

    @Autowired
    private ObjectMapper objectMapper;

    private JournalDTO journalDTO;
    private List<JournalDTO> journalDTOList;

    @BeforeEach
    void setUp() {
        journalDTO = new JournalDTO();
        journalDTO.setId(1L);
        journalDTO.setYear(2024);
        journalDTO.setTitle("Journal of Software");
        journalDTO.setDescription("All about software dev");

        JournalDTO journalDTO2 = new JournalDTO();
        journalDTO2.setId(2L);
        journalDTO2.setYear(2023);
        journalDTO2.setTitle("Journal of AI");
        journalDTO2.setDescription("Artificial Intelligence research");

        journalDTOList = Arrays.asList(journalDTO, journalDTO2);
    }

    @Test
    @DisplayName("GET /api/v1/journals - Get All Journals")
    void testGetAllJournals() throws Exception {
        when(journalService.getAllJournals()).thenReturn(journalDTOList);

        mockMvc.perform(get("/api/v1/journals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Journals retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("Journal of Software"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].title").value("Journal of AI"));
    }

    @Test
    @DisplayName("POST /api/v1/journals - Create Journal")
    void testAddJournal() throws Exception {
        when(journalService.addJournal(any(JournalDTO.class))).thenReturn(journalDTO);

        mockMvc.perform(post("/api/v1/journals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(journalDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Journal created successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.year").value(2024))
                .andExpect(jsonPath("$.data.title").value("Journal of Software"))
                .andExpect(jsonPath("$.data.description").value("All about software dev"));
    }

    @Test
    @DisplayName("GET /api/v1/journals/{id} - Get Journal By ID")
    void testGetJournalById() throws Exception {
        when(journalService.getJournalById(1L)).thenReturn(journalDTO);

        mockMvc.perform(get("/api/v1/journals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Journal retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.year").value(2024))
                .andExpect(jsonPath("$.data.title").value("Journal of Software"))
                .andExpect(jsonPath("$.data.description").value("All about software dev"));
    }

    @Test
    @DisplayName("PUT /api/v1/journals/{id} - Update Journal")
    void testUpdateJournal() throws Exception {
        JournalDTO updatedJournal = new JournalDTO();
        updatedJournal.setId(1L);
        updatedJournal.setYear(2024);
        updatedJournal.setTitle("Journal of Modern Software");
        updatedJournal.setDescription("Updated journal info");

        when(journalService.updateJournal(eq(1L), any(JournalDTO.class))).thenReturn(updatedJournal);

        mockMvc.perform(put("/api/v1/journals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedJournal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Journal updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.year").value(2024))
                .andExpect(jsonPath("$.data.title").value("Journal of Modern Software"))
                .andExpect(jsonPath("$.data.description").value("Updated journal info"));
    }

    @Test
    @DisplayName("DELETE /api/v1/journals/{id} - Delete Journal")
    void testDeleteJournal() throws Exception {
        doNothing().when(journalService).deleteJournal(1L);

        mockMvc.perform(delete("/api/v1/journals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Journal deleted successfully"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
