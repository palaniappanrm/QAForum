package com.eceplatform.QAForum.service;

import com.eceplatform.QAForum.dto.QuestionRequest;

public interface QuestionService {
    void addQuestion(QuestionRequest questionRequest);

    String getPresignedUploadRequestUrl();
}
