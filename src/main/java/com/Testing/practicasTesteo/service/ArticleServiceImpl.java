package com.Testing.practicasTesteo.service;

import com.Testing.practicasTesteo.entity.Article;
import com.Testing.practicasTesteo.exceptions.ArticleNotFoundException;
import com.Testing.practicasTesteo.exceptions.NotFoundException;
import com.Testing.practicasTesteo.exceptions.NotSavedException;
import com.Testing.practicasTesteo.respository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;


    @Override
    public List<Article> getAllArticles() throws NotFoundException {
        try{
            return articleRepository.findAll();
        }catch (Exception e){
            throw new ArticleNotFoundException("No data found" + e.getMessage());

        }

    }

    @Override
    public Article getArticleByid(long id) throws ArticleNotFoundException {
        try {
            return articleRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ArticleNotFoundException("Article not found with id: " + id);
        }
    }

    @Override
    public Article updateArticleById(Article a, long id) throws ArticleNotFoundException {
        Optional<Article> articleFound = articleRepository.findById(id);
        if (articleFound.isPresent()) {
            Article articleUpdated = articleFound.get();
            articleUpdated.setCodigo(a.getCodigo());
            articleUpdated.setDescripcion(a.getDescripcion());
            articleUpdated.setPrecio(a.getPrecio());

            return articleRepository.save(articleUpdated);

        } else {
            throw new ArticleNotFoundException("Article ID  " + id + "no encontrado.");
        }
    }

    //todo que mensaje??
    @Override
    public Article saveArticle(Article article) {
        try {
            return articleRepository.save(article);
        } catch (Exception e) {
            throw new NotSavedException("Error saving article" + e.getMessage());
        }
    }

    @Override
    public boolean deleteAllArticles() {

        try {
            articleRepository.deleteAll();
            return true;
        } catch (Exception e) {
            throw new ArticleNotFoundException("Articles not Found.");
        }
    }

    @Override
    public boolean deleteArticleById(long id) {
        Optional<Article> articleFound = articleRepository.findById(id);
        if (articleFound.isPresent()) {
            articleRepository.delete(articleFound.get());
            return true;
        } else {
            throw new ArticleNotFoundException("Article not found with id: " + id);
        }
    }
}
