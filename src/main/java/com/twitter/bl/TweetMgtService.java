package com.twitter.bl;

import com.twitter.dl.Tweet;
import com.twitter.dl.TweetRepository;
import com.twitter.dl.User;
import com.twitter.dl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TweetMgtService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    public void createTweet(String tweetMessage, String email) {

        Optional<User> opUser = userRepository.findByEmail(email);
        if(opUser.isPresent()) {
            Tweet tweet = new Tweet();
            tweet.setMessage(tweetMessage);
            tweet.setUser(opUser.get());
            tweetRepository.save(tweet);
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<Tweet> findAllTweets() {
        return tweetRepository.findAll();
    }


    public List<Tweet> findAllTweetsByUser(String email) {
        Optional<User> opUser = userRepository.findByEmail(email);
        if(opUser.isPresent()) {
            return opUser.get().getTweets();
        } else {
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public void deleteAllTweetsForUser(String email) {
        Optional<User> opUser = userRepository.findByEmail(email);
        if(opUser.isPresent()) {
            List<Tweet> tweets = opUser.get().getTweets();
            opUser.get().getTweets().clear();
        } else {
            throw new UserNotFoundException();
        }
    }

}



