package com.eceplatform.QAForum;

import com.eceplatform.QAForum.util.PropertiesListener;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEncryptableProperties
public class QaForumApplication {
    public static void main(String[] args) {
//        SpringApplication.run(QaForumApplication.class, args);
        SpringApplication app = new SpringApplication(QaForumApplication.class);
        app.addListeners(new PropertiesListener());
        app.run(args);
    }
}

/**
 *
 * Threadlocal for logged in user details - done
 *
 * Get RDS MySQL instance - done
 *
 * Get AWS access for s3 image upload - done
 *
 * Integrate elastic search to put questions and answers into there for word, sentence wise search sorted by number of likes
 * 1. Create one index in ES, configure stop analyser, create mapping with proper field types, some columns shouldn't be involved in search -> done
 * 2. Index the documents with a custom POJO irrespective of question or answer type (in new ES versions, there are no types, just index and documents) -> done
 * 3. Update it when like increases
 * 4. Fetch documents sorted by likes for a searched word with fuzziness -> done
 *
 * Get swagger - done, actuator - done
 *
 * Store credentials in property file properly (jaspyt - done, AWS secret - done)
 *
 * Write test cases for few files (JUnit, Mockito, Scala)
 *
 * Enabling code coverage and viewing the report
 *
 * Refreshing JWT token expiry whe using system continuously
 *
 * Fix build issue wrt scala
 *
 * put the stuff into a kafka topic (question and answer), read them again and then do it with akka (async) just for learning purpose
 *
 * Integrate redis, (many aws services) just for learning purpose
 *
 * promethues, grafana, etc
 *
 * Wireshark and learn everything
 *
 * Create docker image
 *
 * Configure logback.xml and proper logging
 *
 * Integrate datadog
 *
 * OAuth and stuffs other than normal log in
 *
 */