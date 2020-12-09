package com.eceplatform.QAForum.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ANSWER")
public class Answer {

    @Id
    @Column(name = "ANSWER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ANSWER_USER"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ANSWER_QUESTION"))
    private Question question;

    @Column(name = "LIKES", nullable = false)
    private int likes;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT", nullable = false)
    private LocalDateTime modifiedAt;

    @Column(name="ELASTIC_SEARCH_REF")
    private String elasticSearchRef;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getElasticSearchRef() {
        return elasticSearchRef;
    }

    public void setElasticSearchRef(String elasticSearchRef) {
        this.elasticSearchRef = elasticSearchRef;
    }
}
