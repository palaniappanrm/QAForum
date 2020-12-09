package com.eceplatform.QAForum.service;

import com.eceplatform.QAForum.dto.QuestionRequest;
import com.eceplatform.QAForum.dto.S3KeyResponse;

public interface QuestionService {
    void addQuestion(QuestionRequest questionRequest);

    S3KeyResponse getPresignedUploadRequestUrl();
}
