package com.eceplatform.QAForum.repository;

import com.eceplatform.QAForum.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Question q set q.elasticSearchRef = ?1 where q.id = ?2")
    void setESRefById(String elasticSearchRef, Integer questionId);
}
