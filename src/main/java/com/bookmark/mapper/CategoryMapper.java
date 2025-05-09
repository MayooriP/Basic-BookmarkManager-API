package com.bookmark.mapper;

import com.bookmark.dto.CategoryDTO;
import com.bookmark.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);
}