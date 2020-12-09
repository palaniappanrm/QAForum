package com.eceplatform.QAForum.controller;

import com.eceplatform.QAForum.dto.QuestionRequest;
import com.eceplatform.QAForum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

//  Get s3 signed URL for question image upload - > s3 key format (questions/userId/random_uuid) [GET URL -> /s3-key]
    @GetMapping("/s3-pre-signed-url")
    public ResponseEntity getSignedUrl(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getPresignedUploadRequestUrl());
        } catch(ValidationException validationException){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validationException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//   Post a question with images [POST Body -> QuestionRequest] [Response -> 201 success]
    @PostMapping
    public ResponseEntity addQuestion(@Valid @RequestBody QuestionRequest questionRequest){
        try {
            questionService.addQuestion(questionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(ValidationException validationException){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validationException.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    TODO : Editing the question -> editing the question text, add/remove images [PUT URL -> /:id Body -> QuestionRequest] [Response -> 200 success]
//    TODO : Question like/unlike [POST URL -> /:id/:action] action can be like or unlike [Response -> 200 success]
//
//    TODO : Get question by id with images (fetching image link from s3client for the appropriate key)
//     along with all answers sorted by likes with answer images [GET URL -> /:id] [Response -> QuestionResponse]

//    TODO : Get list of questions of particular category sorted by modified desc, pagination, question search without images
//      [GET] with appropriate query params [Response -> QuestionResponse]

}
