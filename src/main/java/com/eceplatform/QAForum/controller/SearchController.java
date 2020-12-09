package com.eceplatform.QAForum.controller;

import com.eceplatform.QAForum.dto.DTOES;
import com.eceplatform.QAForum.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("content")
public class SearchController {

    @Autowired
    private SearchService contentService;

    // ES endpoint for search across question and answers
    @GetMapping
    public List<DTOES> search(@RequestParam(value = "text", defaultValue = "") String text, @RequestParam(value = "from", defaultValue = "0") Integer from, @RequestParam(value = "size", defaultValue = "100") Integer size){

        return contentService.search(text, from, size);

    }

}
