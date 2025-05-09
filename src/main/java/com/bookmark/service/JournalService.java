package com.bookmark.service;

import com.bookmark.dto.JournalDTO;
import com.bookmark.entity.Journal;
import com.bookmark.exception.ResourceNotFoundException;
import com.bookmark.mapper.JournalMapper;
import com.bookmark.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalService {
    private final JournalRepository journalRepository;
    private final JournalMapper journalMapper;

    public JournalService(JournalRepository journalRepository, JournalMapper journalMapper) {
        this.journalRepository = journalRepository;
        this.journalMapper = journalMapper;
    }

    public List<JournalDTO> getAllJournals() {
        return journalRepository.findAll().stream()
                .map(journalMapper::toDTO)
                .collect(Collectors.toList());
    }

    public JournalDTO getJournalById(Long id) {
        Journal journal = journalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal not found with ID: " + id));
        return journalMapper.toDTO(journal);
    }

    public JournalDTO addJournal(JournalDTO journalDTO) {
        Journal journal = journalMapper.toEntity(journalDTO);
        Journal savedJournal = journalRepository.save(journal);
        return journalMapper.toDTO(savedJournal);
    }

    public JournalDTO updateJournal(Long id, JournalDTO journalDTO) {
        Journal journal = journalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal not found with ID: " + id));
        journal.setYear(journalDTO.getYear());
        journal.setTitle(journalDTO.getTitle());
        journal.setDescription(journalDTO.getDescription());
        Journal updatedJournal = journalRepository.save(journal);
        return journalMapper.toDTO(updatedJournal);
    }

    public void deleteJournal(Long id) {
        Journal journal = journalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal not found with ID: " + id));
        journalRepository.delete(journal);
    }
}