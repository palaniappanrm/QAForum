package com.eceplatform.QAForum.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "QUESTION")
public class Question {

    @Id
    @Column(name = "QUESTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_QUESTION_USER"))
    private User user;

    @Column(name = "LIKES", nullable = false)
    private int likes = 0;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "MODIFIED_AT", nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionImage> images;

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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<QuestionImage> getImages() {
        return images;
    }

    public void setImages(List<QuestionImage> images) {
        this.images = images;
    }

    public String getElasticSearchRef() {
        return elasticSearchRef;
    }

    public void setElasticSearchRef(String elasticSearchRef) {
        this.elasticSearchRef = elasticSearchRef;
    }
}
