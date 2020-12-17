package com.eceplatform.QAForum.actors

import akka.actor.{Actor, ActorLogging}
import com.eceplatform.QAForum.ESContentType
import com.eceplatform.QAForum.dto.DTOES
import com.eceplatform.QAForum.repository.QuestionRepository
import com.eceplatform.QAForum.util.ESActions
import com.fasterxml.jackson.databind.ObjectMapper
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.action.search.{SearchRequest, SearchResponse}
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.client.{RequestOptions, RestHighLevelClient}
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.{FieldSortBuilder, SortOrder}

case class ESActor(restHighLevelClient: RestHighLevelClient, questionRepository: QuestionRepository, mapper: ObjectMapper) extends Actor with ActorLogging {
  override def receive: Receive = {

    case (indexDTO: DTOES, indexName: String, action: ESActions) if action equals ESActions.CREATE =>
      val indexRequest: IndexRequest = new IndexRequest(indexName)
      indexRequest source(mapper.writeValueAsString(indexDTO), XContentType.JSON)

      try {
        val indexResponse: IndexResponse = restHighLevelClient index(indexRequest, RequestOptions.DEFAULT)

        indexDTO.getEsContentType match {
          case esType if esType equals ESContentType.QUESTION =>
            questionRepository setESRefById(indexResponse.getId, indexDTO.getDbId)
          case _ =>
        }
      } catch {
        case e: Exception =>
          log.error("Exception {} in indexing the document {}", e, indexRequest)
      }

    case (indexDTO: DTOES, indexName: String, action: ESActions) if action equals ESActions.UPDATE_LIKES =>
      val updateRequest: UpdateRequest = new UpdateRequest(indexName, indexDTO getElasticSearchRef) doc("likes", indexDTO.getLikes)
      restHighLevelClient update(updateRequest, RequestOptions.DEFAULT)

    case (searchText: String, indexName: String, from: Integer, size: Integer, eSActions: ESActions) if eSActions equals ESActions.SEARCH_CONTENT =>
      val sourceBuilder: SearchSourceBuilder = new SearchSourceBuilder()
      sourceBuilder query QueryBuilders.matchQuery("content", searchText).fuzziness(Fuzziness.TWO)
      sourceBuilder from from
      sourceBuilder size size
      sourceBuilder sort new FieldSortBuilder("likes").order(SortOrder.DESC)

      val searchRequest: SearchRequest = new SearchRequest(indexName)
      searchRequest source sourceBuilder

      log info searchRequest.toString

      val searchResponse: SearchResponse  = restHighLevelClient search(searchRequest, RequestOptions.DEFAULT)

      log info mapper.writeValueAsString(searchResponse.getHits)

    case _ => log.error("received unknown message");
  }
}
