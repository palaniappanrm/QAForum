package com.eceplatform.QAForum.model;

import javax.persistence.*;

@Entity
@Table(name = "QUESTION_LIKE")
public class QuestionLike {

    @Id
    @Column(name = "QUESTION_LIKE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_QUESTION_LIKE_USER"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_QUESTION_LIKE_QUESTION"))
    private Question question;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
