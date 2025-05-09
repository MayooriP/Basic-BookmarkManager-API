package com.bookmark.controller;

import com.bookmark.dto.ApiResponse;
import com.bookmark.dto.JournalDTO;
import com.bookmark.service.JournalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/journals")
public class JournalController {
    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<JournalDTO>>> getAllJournals() {
        List<JournalDTO> journals = journalService.getAllJournals();
        return ResponseEntity.ok(ApiResponse.success("Journals retrieved successfully", journals));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JournalDTO>> getJournalById(@PathVariable Long id) {
        JournalDTO journal = journalService.getJournalById(id);
        return ResponseEntity.ok(ApiResponse.success("Journal retrieved successfully", journal));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JournalDTO>> addJournal(@Validated @RequestBody JournalDTO journalDTO) {
        JournalDTO savedJournal = journalService.addJournal(journalDTO);
        return new ResponseEntity<>(ApiResponse.success("Journal created successfully", savedJournal), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JournalDTO>> updateJournal(@PathVariable Long id, @Validated @RequestBody JournalDTO journalDTO) {
        JournalDTO updatedJournal = journalService.updateJournal(id, journalDTO);
        return ResponseEntity.ok(ApiResponse.success("Journal updated successfully", updatedJournal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteJournal(@PathVariable Long id) {
        journalService.deleteJournal(id);
        return ResponseEntity.ok(ApiResponse.success("Journal deleted successfully", null));
    }
}