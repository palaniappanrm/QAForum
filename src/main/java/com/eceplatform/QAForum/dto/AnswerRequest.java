package com.eceplatform.QAForum.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class AnswerRequest {

    @NotBlank
    private String content;

    private List<String> s3ImageKeys;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getS3ImageKeys() {
        return s3ImageKeys;
    }

    public void setS3ImageKeys(List<String> s3ImageKeys) {
        this.s3ImageKeys = s3ImageKeys;
    }
}
