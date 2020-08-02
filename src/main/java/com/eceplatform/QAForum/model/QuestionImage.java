package com.eceplatform.QAForum.model;

import javax.persistence.*;

@Entity
@Table(name = "QUESTION_IMAGE")
public class QuestionImage {

    @Id
    @Column(name = "QUESTION_IMAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_QUESTION_IMAGE_QUESTION"))
    private Question question;

    @Column(name = "S3_KEY", nullable = false)
    private String s3Key;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }
}
