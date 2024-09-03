package raf.vp.jmijatovic11421rn.entities;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class Category {

    @NotBlank
    private String categoryTitle;
    @NotBlank
    private String description;

    private List<Integer> articles;

    public Category() {

    }

    public Category(String categoryTitle, String description, List<Integer> articles) {
        this.categoryTitle = categoryTitle;
        this.description = description;
        this.articles = articles;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getArticles() {
        return articles;
    }
}
