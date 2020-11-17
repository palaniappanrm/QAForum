package com.eceplatform.QAForum.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class QuestionRequest {

    @NotNull(message = "Question text can't be null")
    @NotBlank(message = "Question text can't be empty")
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
