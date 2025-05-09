package com.bookmark.service;

import com.bookmark.dto.ArticleDTO;
import com.bookmark.entity.Article;
import com.bookmark.entity.Category;
import com.bookmark.entity.Journal;
import com.bookmark.exception.ResourceNotFoundException;
import com.bookmark.mapper.ArticleMapper;
import com.bookmark.repository.ArticleRepository;
import com.bookmark.repository.CategoryRepository;
import com.bookmark.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final JournalRepository journalRepository;
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository, JournalRepository journalRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.journalRepository = journalRepository;
        this.articleMapper = articleMapper;
    }

    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with ID: " + id));
        return articleMapper.toDTO(article);
    }


    public ArticleDTO addArticle(ArticleDTO articleDTO) {

        Category category = categoryRepository.findById(articleDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + articleDTO.getCategoryId()));

        Journal journal = null;
        if (articleDTO.getJournalId() != null) {
            journal = journalRepository.findById(articleDTO.getJournalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Journal not found with ID: " + articleDTO.getJournalId()));
        }

        Article article = articleMapper.toEntity(articleDTO);
        article.setCategory(category);
        article.setJournal(journal);
        article.setCreatedDate(LocalDateTime.now());
        article.setUpdatedDate(LocalDateTime.now());
        // Save article and return mapped DTO
        Article savedArticle = articleRepository.save(article);
        return articleMapper.toDTO(savedArticle);
    }


    public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with ID: " + id));
        Category category = categoryRepository.findById(articleDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + articleDTO.getCategoryId()));

        Journal journal = null;
        if (articleDTO.getJournalId() != null) {
            journal = journalRepository.findById(articleDTO.getJournalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Journal not found with ID: " + articleDTO.getJournalId()));
        }

        article.setTitle(articleDTO.getTitle());
        article.setSubtitle(articleDTO.getSubtitle());
        article.setLink(articleDTO.getLink());
        article.setContent(articleDTO.getContent());
        article.setAuthor(articleDTO.getAuthor());
        article.setCategory(category);
        article.setJournal(journal);
        article.setUpdatedDate(LocalDateTime.now());

        Article updatedArticle = articleRepository.save(article);
        return articleMapper.toDTO(updatedArticle);
    }

    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with ID: " + id));
        articleRepository.delete(article);
    }
}
