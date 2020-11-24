package com.eceplatform.QAForum.repository;

import com.eceplatform.QAForum.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken,Integer> {

    UserToken findByToken(String token);

}
