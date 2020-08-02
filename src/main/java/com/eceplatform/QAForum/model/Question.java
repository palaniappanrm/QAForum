package com.eceplatform.QAForum.model;

import javax.persistence.*;

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
    private int likes;

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
}
