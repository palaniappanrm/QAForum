package com.eceplatform.QAForum.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.eceplatform.QAForum.aop.annotations.LoggedIn;
import com.eceplatform.QAForum.dto.QuestionRequest;
import com.eceplatform.QAForum.dto.UserDTO;
import com.eceplatform.QAForum.model.Question;
import com.eceplatform.QAForum.model.QuestionImage;
import com.eceplatform.QAForum.model.User;
import com.eceplatform.QAForum.repository.QuestionRepository;
import com.eceplatform.QAForum.repository.UserRepository;
import com.eceplatform.QAForum.service.QuestionService;
import com.eceplatform.QAForum.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userThreadLocal")
    private ThreadLocalUtil<UserDTO> userThreadLocal;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    @LoggedIn
    public void addQuestion(QuestionRequest questionRequest) {

        User user = userRepository.findById(userThreadLocal.getValue().getId()).orElse(null);

        if(user != null){
            Question question = new Question();
            question.setContent(questionRequest.getContent());
            question.setUser(user);
            if(!CollectionUtils.isEmpty(questionRequest.getS3ImageKeys())){
                question.setImages(questionRequest.getS3ImageKeys().stream().map(key -> {
                    QuestionImage questionImage = new QuestionImage();
                    questionImage.setQuestion(question);
                    questionImage.setS3Key(key);
                    return questionImage;
                }).collect(Collectors.toList()));
            }
            questionRepository.save(question);
        } else {
            throw new RuntimeException("Invalid user");
        }

    }

    @Override
    @LoggedIn
    public String getPresignedUploadRequestUrl() {

        User user = userRepository.findById(userThreadLocal.getValue().getId()).orElse(null);

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 600000; // 10 minutes
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest("qa-forum", "questions/" + user.getId() + "/" + UUID.randomUUID().toString())
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
}
