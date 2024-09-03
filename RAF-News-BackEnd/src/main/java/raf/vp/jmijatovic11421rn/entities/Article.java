package raf.vp.jmijatovic11421rn.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Article {

    @NotNull
    private Integer articleId;
    @NotBlank
    private String articleTitle;
    @NotBlank
    private String articleText;
    @NotNull
    @PastOrPresent
    private Date creationDate;
    @NotNull
    private Integer visitCount;
    @NotNull
    private User articleAuthor;

    private List<Comment> comments;

    private List<Tag> tags;
    @NotNull
    private String category;

    public Article() {

    }

    public Article(Integer articleId, String articleTitle, String articleText, Date creationDate, Integer visitCount, User articleAuthor, List<Comment> comments, List<Tag> tags, String category) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.articleText = articleText;
        this.creationDate = creationDate;
        this.visitCount = visitCount;
        this.articleAuthor = articleAuthor;
        this.comments = comments;
        this.tags = tags;
        this.category = category;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleText() {
        return articleText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public User getArticleAuthor() {
        return articleAuthor;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getCategory() {
        return category;
    }
}
