package com.eceplatform.QAForum.service;

import com.eceplatform.QAForum.dto.DTOES;

trait SearchService {

    def search(content: String, from: Integer, size: Integer): java.util.List[DTOES]

}
