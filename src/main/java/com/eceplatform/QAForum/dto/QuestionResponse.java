package com.eceplatform.QAForum.dto;

import java.util.List;

public class QuestionResponse {

    private int id;

    private String content;

    private int likes;

    private List<String> s3ImageKeys;

    private List<AnswerResponse> answerResponses;

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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getS3ImageKeys() {
        return s3ImageKeys;
    }

    public void setS3ImageKeys(List<String> s3ImageKeys) {
        this.s3ImageKeys = s3ImageKeys;
    }

    public List<AnswerResponse> getAnswerResponses() {
        return answerResponses;
    }

    public void setAnswerResponses(List<AnswerResponse> answerResponses) {
        this.answerResponses = answerResponses;
    }
}
