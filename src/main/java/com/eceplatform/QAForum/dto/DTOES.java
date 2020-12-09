package com.eceplatform.QAForum.dto;

import com.eceplatform.QAForum.ESContentType;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DTOES {

    private ESContentType esContentType;
    private Integer dbId;
    private String content;
    private Integer likes;
    @JsonIgnore
    private String elasticSearchRef;

    public DTOES() {
    }

    public DTOES(ESContentType esContentType, Integer dbId, String content, Integer likes) {
        this.esContentType = esContentType;
        this.dbId = dbId;
        this.content = content;
        this.likes = likes;
    }

    public DTOES(int likes, String elasticSearchRef) {
        this.likes = likes;
        this.elasticSearchRef = elasticSearchRef;
    }

    public ESContentType getEsContentType() {
        return esContentType;
    }

    public void setEsContentType(ESContentType esContentType) {
        this.esContentType = esContentType;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getElasticSearchRef() {
        return elasticSearchRef;
    }

    public void setElasticSearchRef(String elasticSearchRef) {
        this.elasticSearchRef = elasticSearchRef;
    }
}
