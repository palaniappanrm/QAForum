package com.eceplatform.QAForum.util;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.elasticsearch.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertiesListener implements ApplicationListener<ApplicationPreparedEvent> {

    private ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesListener.class);
    private static final String AWS_SECRET_NAME = "qaforum/properties";
    private static final String AWS_REGION = "ap-south-1";

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {// Get username and password from AWS Secret Manager using secret name
        String secretJson = getSecret();
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        environment.getPropertySources().addFirst(new PropertiesPropertySource("aws.secret.manager", getProperties(secretJson)));
    }

    private String getSecret() {
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(AWS_REGION).build();
        String secret = null;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(AWS_SECRET_NAME);
        GetSecretValueResult getSecretValueResult = null;
        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
            if (getSecretValueResult != null && getSecretValueResult.getSecretString() != null) {
                secret = getSecretValueResult.getSecretString();
            }
        } catch (DecryptionFailureException | InternalServiceErrorException | InvalidParameterException
                | InvalidRequestException | ResourceNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return secret;
    }

    private Properties getProperties(String json) {
        try {
            JsonNode root = mapper.readTree(json);
            ObjectNode objectNode = (ObjectNode) root;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            Properties props = new Properties();
            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                props.put(entry.getKey(), entry.getValue());
            }
            return props;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}