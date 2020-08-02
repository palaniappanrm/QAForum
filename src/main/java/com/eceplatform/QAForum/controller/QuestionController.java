package com.eceplatform.QAForum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("questions")
public class QuestionController {

//    TODO : Get s3 signed URL for question image upload - > s3 key format (userId/questions/random_uuid) [GET URL -> /s3-key]
//    TODO : Post a question with images [POST Body -> QuestionRequest] [Response -> 201 success]
//    TODO : Editing the question -> editing the question text, add/remove images [PUT URL -> /:id Body -> QuestionRequest] [Response -> 200 success]
//    TODO : Question like/unlike [POST URL -> /:id/:action] action can be like or unlike [Response -> 200 success]
//
//    TODO : Get question by id with images (fetching image link from s3client for the appropriate key)
//     along with all answers sorted by likes with answer images [GET URL -> /:id] [Response -> QuestionResponse]

//    TODO : Get list of questions of particular category sorted by modified desc, pagination, question search without images
//      [GET] with appropriate query params [Response -> QuestionResponse]

}
