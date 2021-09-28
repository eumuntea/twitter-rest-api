package com.twitter.sl;
import com.twitter.bl.TweetMgtService;
import com.twitter.dl.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The endpoint for the management of tweets.
 */
@Slf4j
@RestController
@ResponseBody
public class TweetMgtEndpoint {

    public static final String API_NAME = "tweetmgt";
    public static final String REQUEST_MAPPING_PATH = "/" + API_NAME + "/v1/tweet";

    @Autowired
    TweetMgtService tweetMgtService;

    @RequestMapping(value = REQUEST_MAPPING_PATH, method = RequestMethod.POST)
    public ResponseEntity<String> createTweet(@RequestBody final TweetSO tweetSO){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TweetSO>> violations = validator.validate(tweetSO);
        if(violations != null && violations.size() > 0) {
            for (ConstraintViolation<TweetSO> violation : violations) {
                log.error(violation.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The request is wrong");
        }

        tweetMgtService.createTweet(tweetSO.getTweetMessage(), tweetSO.getUserEmail());
        return ResponseEntity.status(HttpStatus.OK).body("The tweet was successfully created");
    }


    @GetMapping(value = REQUEST_MAPPING_PATH + "/all")
    public ResponseEntity<List<TweetSO>> findAllTweets(){
        List<Tweet> tweets = tweetMgtService.findAllTweets();
        List<TweetSO> soList = new ArrayList<>();
        if(tweets.size() > 0) {
            tweets.forEach(tweet -> soList.add(new TweetSO(tweet.getMessage() , tweet.getUser().getEmail())));
        }
        return ResponseEntity.status(HttpStatus.OK).body(soList);
    }

    @GetMapping(value = REQUEST_MAPPING_PATH)
    public ResponseEntity<List<TweetSO>> findAllTweetsForUser(@RequestParam(value = "email", required = true) final String email){
        List<Tweet> tweets = tweetMgtService.findAllTweetsByUser(email);
        List<TweetSO> soList = new ArrayList<>();
        if(tweets.size() > 0) {
            tweets.forEach(tweet -> soList.add(new TweetSO(tweet.getMessage() , tweet.getUser().getEmail())));
        }
        return ResponseEntity.status(HttpStatus.OK).body(soList);
    }

    @DeleteMapping(value = REQUEST_MAPPING_PATH)
    public ResponseEntity<String> deleteAllTweetsForUser(@RequestParam(value = "email", required = true) final String email){
        tweetMgtService.deleteAllTweetsForUser(email);
        return ResponseEntity.status(HttpStatus.OK).body("All tweets for user with email: " + email + " have been deleted");
    }

}
