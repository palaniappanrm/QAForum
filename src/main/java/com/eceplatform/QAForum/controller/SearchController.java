package com.eceplatform.QAForum.controller;

import com.eceplatform.QAForum.service.SearchService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
@Api(value = "search")
public class SearchController {

    @Autowired
    private SearchService contentService;

    // ES endpoint for search across question and answers
    @GetMapping
    @ApiOperation(value = "search in elastic search", notes = "IN ES")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of search"),
            @ApiResponse(code = 503, message = "ES is down")
    })
    public ResponseEntity search(@ApiParam("search string") @RequestParam(value = "text", defaultValue = "") String text,
                                 @ApiParam("search from") @RequestParam(value = "from", defaultValue = "0") Integer from,
                                 @ApiParam("max returned size") @RequestParam(value = "size", defaultValue = "100") Integer size){

        return ResponseEntity.ok(contentService.search(text, from, size));

    }

}
