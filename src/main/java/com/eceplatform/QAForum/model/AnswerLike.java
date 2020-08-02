package com.eceplatform.QAForum.model;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER_LIKE")
public class AnswerLike {

    @Id
    @Column(name = "ANSWER_LIKE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ANSWER_LIKE_USER"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID", referencedColumnName = "ANSWER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ANSWER_LIKE_ANSWER"))
    private Answer answer;

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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
