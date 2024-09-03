package raf.vp.jmijatovic11421rn.repositories.news;

import raf.vp.jmijatovic11421rn.entities.Article;
import raf.vp.jmijatovic11421rn.entities.Category;

import java.util.List;

public interface ArticleRepository {

    List<Article> getAllArticles();

    List<Article> getArticlesByPage(Integer page);

    List<Article> getArticlesByPageAndCategory(String title, Integer page);

    List<Article> getArticlesByString(String search, Integer page);

    Article getArticle(Integer id);

    void editArticle(Integer id, Article article);

    void addArticle(Article article);

    void deleteArticle(Integer id);
}
