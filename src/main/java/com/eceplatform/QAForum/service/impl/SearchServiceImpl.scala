package com.eceplatform.QAForum.service.impl

import java.time.LocalDate
import java.util

import com.eceplatform.QAForum.dto.DTOES
import com.eceplatform.QAForum.service.SearchService
import com.fasterxml.jackson.databind.ObjectMapper
import org.elasticsearch.action.search.{SearchRequest, SearchResponse}
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.{FieldSortBuilder, SortOrder}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;

@Service
class SearchServiceImpl @Autowired()(private val restHighLevelClient: RestHighLevelClient, private val mapper: ObjectMapper) extends SearchService {

  override def search(content: String, from: Integer, size: Integer): java.util.List[DTOES] = {

    val sourceBuilder: SearchSourceBuilder = new SearchSourceBuilder()
    sourceBuilder query QueryBuilders.matchQuery("content", content).fuzziness(Fuzziness.TWO)
    sourceBuilder from from
    sourceBuilder size size
    sourceBuilder sort new FieldSortBuilder("likes").order(SortOrder.DESC)

    val searchRequest: SearchRequest = new SearchRequest("qa_forum_content_" + LocalDate.now.getYear)
    searchRequest source sourceBuilder

    val searchResponse: SearchResponse = restHighLevelClient search(searchRequest, RequestOptions.DEFAULT)

    val searchHits = searchResponse.getHits.getHits

    val dtos: java.util.List[DTOES] = new util.ArrayList[DTOES]()
    searchHits foreach { hit => System.out.println(hit.getSourceAsString); dtos add mapper.readValue(hit.getSourceAsString, classOf[DTOES]) }

    dtos
  }
}
