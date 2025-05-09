package com.bookmark.mapper;

import com.bookmark.dto.JournalDTO;
import com.bookmark.entity.Journal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JournalMapper {
    Journal toEntity(JournalDTO journalDTO);

    JournalDTO toDTO(Journal journal);
}