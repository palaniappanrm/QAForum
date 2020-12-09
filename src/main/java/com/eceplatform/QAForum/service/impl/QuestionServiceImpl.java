package com.eceplatform.QAForum.service.impl;

import akka.actor.ActorRef;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.eceplatform.QAForum.ESContentType;
import com.eceplatform.QAForum.aop.annotations.LoggedIn;
import com.eceplatform.QAForum.dto.DTOES;
import com.eceplatform.QAForum.dto.QuestionRequest;
import com.eceplatform.QAForum.dto.S3KeyResponse;
import com.eceplatform.QAForum.dto.UserDTO;
import com.eceplatform.QAForum.model.Question;
import com.eceplatform.QAForum.model.QuestionImage;
import com.eceplatform.QAForum.model.User;
import com.eceplatform.QAForum.repository.QuestionRepository;
import com.eceplatform.QAForum.repository.UserRepository;
import com.eceplatform.QAForum.service.QuestionService;
import com.eceplatform.QAForum.util.ESActions;
import com.eceplatform.QAForum.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import scala.Tuple3;

import java.time.LocalDate;
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

    @Autowired
    @Qualifier("esActor")
    private ActorRef esActor;

    @Override
    @LoggedIn
    public void addQuestion(QuestionRequest questionRequest) {

        User user = userRepository.findById(userThreadLocal.getValue().getId()).orElse(null);

        if (user != null) {
            Question question = new Question();
            question.setContent(questionRequest.getContent());
            question.setUser(user);
            if (!CollectionUtils.isEmpty(questionRequest.getS3ImageKeys())) {
                question.setImages(questionRequest.getS3ImageKeys().stream().map(key -> {
                    QuestionImage questionImage = new QuestionImage();
                    questionImage.setQuestion(question);
                    questionImage.setS3Key(key);
                    return questionImage;
                }).collect(Collectors.toList()));
            }
            questionRepository.save(question);

            DTOES dtoes = new DTOES(ESContentType.QUESTION, question.getId(), question.getContent(), question.getLikes());

            esActor.tell(new Tuple3<DTOES, String, ESActions>(dtoes, "qa_forum_content_" + LocalDate.now().getYear(), ESActions.CREATE), ActorRef.noSender());
        } else {
            throw new RuntimeException("Invalid user");
        }

    }

    @Override
    @LoggedIn
    public S3KeyResponse getPresignedUploadRequestUrl() {

        User user = userRepository.findById(userThreadLocal.getValue().getId()).orElse(null);

        if (user != null) {
            Date expiration = new Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 600000; // 10 minutes
            expiration.setTime(expTimeMillis);

            String uuid = UUID.randomUUID().toString();
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest("qa-forum", "questions/" + user.getId() + "/" + UUID.randomUUID().toString())
                            .withMethod(HttpMethod.PUT)
                            .withExpiration(expiration);
            return new S3KeyResponse(amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString(), uuid);
        } else {
            throw new RuntimeException("Invalid user");
        }
    }
}
