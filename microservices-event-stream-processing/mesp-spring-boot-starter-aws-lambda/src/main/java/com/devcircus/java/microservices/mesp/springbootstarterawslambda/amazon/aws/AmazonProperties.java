package com.devcircus.java.microservices.mesp.springbootstarterawslambda.amazon.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "amazon")
public class AmazonProperties {

    @NestedConfigurationProperty
    private Aws aws;

    public Aws getAws() {
        return aws;
    }

    public void setAws(Aws aws) {
        this.aws = aws;
    }

    public static class Aws {

        private String accessKeyId;
        private String accessKeySecret;

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        @Override
        public String toString() {
            return "Aws{"
                    + "accessKeyId='" + accessKeyId + '\''
                    + ", accessKeySecret='" + accessKeySecret + '\''
                    + '}';
        }
    }
}
