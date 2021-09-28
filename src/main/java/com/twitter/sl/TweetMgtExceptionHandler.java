package com.twitter.sl;

import com.twitter.bl.UserNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(0)
public class TweetMgtExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handle(final UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user with this email");
    }
}
