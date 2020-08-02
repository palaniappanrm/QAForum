package com.eceplatform.QAForum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("answers")
public class AnswerController {

//    TODO : Get s3 signed URL for answer image upload - > s3 key format (userId/answers/random_uuid) [GET URL -> /s3-key]
//    TODO : Post a answer with images [POST Body -> AnswerRequest] [Response -> 201 success]
//    TODO : Edit the answer -> editing the answer text, add/remove images [PUT URL -> /:id Body -> AnswerRequest] [Response -> 200 success]
//    TODO : Answer like/unlike [POST URL -> /:id/:action] action can be like or unlike [Response -> 201 success]

//    NOT NEEDED
//    TODO : Get answer by id with images (fetching image link from s3client for the appropriate key) [GET URL -> /:id] [Response -> AnswerResponse]

}
