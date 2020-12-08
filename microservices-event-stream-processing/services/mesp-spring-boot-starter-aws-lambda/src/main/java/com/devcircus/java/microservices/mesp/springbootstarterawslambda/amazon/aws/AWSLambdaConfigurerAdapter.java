package com.devcircus.java.microservices.mesp.springbootstarterawslambda.amazon.aws;

import com.amazonaws.auth.*;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AWSLambdaConfigurerAdapter {

    private String accessKeyId;
    private String accessKeySecret;
    private Credentials sessionCredentials;

    public AWSLambdaConfigurerAdapter(String accessKeyId,
            String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public <T> T getFunctionInstance(Class<T> type) {
        return LambdaInvokerFactory.builder()
                .lambdaClient(AWSLambdaClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(
                                getBasicSessionCredentials()))
                        .build())
                .build(type);
    }

    private BasicSessionCredentials getBasicSessionCredentials() {

        // Create a new session token if the session is expired or not initialized
        if (sessionCredentials == null || sessionCredentials.getExpiration().before(new Date())) {
            sessionCredentials = getSessionCredentials();
        }

        // Create basic session credentials using the generated session token
        return new BasicSessionCredentials(sessionCredentials.getAccessKeyId(),
                sessionCredentials.getSecretAccessKey(),
                sessionCredentials.getSessionToken());
    }

    private Credentials getSessionCredentials() {
        // Create a new session with the user credentials for the service instance
        AWSSecurityTokenServiceClient stsClient
                = new AWSSecurityTokenServiceClient(new BasicAWSCredentials(accessKeyId, accessKeySecret));

        // Start a new session for managing a service instance's bucket
        GetSessionTokenRequest getSessionTokenRequest
                = new GetSessionTokenRequest().withDurationSeconds(43200);

        // Get the session token for the service instance's bucket
        sessionCredentials = stsClient.getSessionToken(getSessionTokenRequest).getCredentials();

        return sessionCredentials;
    }
}
