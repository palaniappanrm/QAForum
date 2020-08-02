package com.eceplatform.QAForum.model;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER_IMAGE")
public class AnswerImage {

    @Id
    @Column(name = "ANSWER_IMAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID", referencedColumnName = "ANSWER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ANSWER_IMAGE_ANSWER"))
    private Answer answer;

    @Column(name = "S3_KEY", nullable = false)
    private String s3Key;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }
}
