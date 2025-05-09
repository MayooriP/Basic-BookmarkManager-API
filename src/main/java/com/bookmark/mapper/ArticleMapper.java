package com.bookmark.mapper;

import com.bookmark.dto.ArticleDTO;
import com.bookmark.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, JournalMapper.class})
public interface ArticleMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "journalId", source = "journal.id")
    ArticleDTO toDTO(Article article);

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "journal.id", source = "journalId")
    Article toEntity(ArticleDTO articleDTO);
}
