package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.web;

import com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.tweet.TwitterService;
import com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("v1")
public class ApiController {

    private final TwitterService twitterService;

    @Autowired
    public ApiController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @RequestMapping(path = "user/{screenName}", method = RequestMethod.GET)
    public ResponseEntity<User> discoverProfileByScreenName(@PathVariable("screenName") String screenName) {
        return Optional.of(ResponseEntity.ok(twitterService.discoverUserByScreenName(screenName)))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
