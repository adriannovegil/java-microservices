package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringCloudApplication
@EnableZuulProxy
@EnableScheduling
public class TwitterCrawlerApplication {

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(TwitterCrawlerApplication.class).run(args);
    }

}
