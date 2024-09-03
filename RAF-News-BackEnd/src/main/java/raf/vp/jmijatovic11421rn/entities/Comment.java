package raf.vp.jmijatovic11421rn.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

public class Comment {

    @NotNull
    private Integer commentId;
    @NotNull(message = "Author field is required")
    private User commentAuthor;
    @NotBlank
    private String commentText;
    @NotNull
    @PastOrPresent
    private Date commentDate;

    public Comment() {

    }

    public Comment(Integer commentId, User commentAuthor, String commentText, Date commentDate) {
        this.commentId = commentId;
        this.commentAuthor = commentAuthor;
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public User getCommentAuthor() {
        return commentAuthor;
    }

    public String getCommentText() {
        return commentText;
    }

    public Date getCommentDate() {
        return commentDate;
    }
}
