package raf.vp.jmijatovic11421rn.services;

import raf.vp.jmijatovic11421rn.entities.Article;
import raf.vp.jmijatovic11421rn.entities.Category;
import raf.vp.jmijatovic11421rn.repositories.news.ArticleRepository;

import javax.inject.Inject;
import java.util.List;

public class ArticleService {

    @Inject
    ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        return this.articleRepository.getAllArticles();
    }

    public List<Article> getArticlesByPage(Integer page) {
        return this.articleRepository.getArticlesByPage(page);
    }

    public List<Article> getArticlesByPageAndCategory(String title, Integer page) {
        return this.articleRepository.getArticlesByPageAndCategory(title, page);
    }

    public List<Article> getArticlesByString(String search, Integer page) {
        return this.articleRepository.getArticlesByString(search, page);
    }

    public Article getArticle(Integer id) {
        return this.articleRepository.getArticle(id);
    }

    public void editArticle(Integer id, Article article) {
        this.articleRepository.editArticle(id, article);
    }

    public void addArticle(Article article) {
        this.articleRepository.addArticle(article);
    }

    public void deleteArticle(Integer id) {
        this.articleRepository.deleteArticle(id);
    }
}
